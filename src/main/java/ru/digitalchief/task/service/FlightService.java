package ru.digitalchief.task.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalchief.task.entity.Airplane;
import ru.digitalchief.task.entity.Flight;
import ru.digitalchief.task.entity.Trip;
import ru.digitalchief.task.exeption.flight.FlightAlreadyExistsException;
import ru.digitalchief.task.exeption.flight.FlightNotFoundException;
import ru.digitalchief.task.mapper.Mapper;
import ru.digitalchief.task.repository.FlightRepository;
import ru.digitalchief.task.request.FindFlightRequest;
import ru.digitalchief.task.request.FlightRequest;
import ru.digitalchief.task.response.flight.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@Slf4j
@Service
@Transactional(readOnly = true)
public class FlightService {
    private final FlightRepository flightRepository;
    private final TripService tripService;
    private final AirplaneService airplaneService;
    private final Mapper mapper;

    @Autowired
    public FlightService(FlightRepository flightRepository,
                         TripService tripService,
                         AirplaneService airplaneService,
                         Mapper mapper) {
        this.flightRepository = flightRepository;
        this.tripService = tripService;
        this.airplaneService = airplaneService;
        this.mapper = mapper;
    }

    @Transactional
    public Flight create(@NotNull FlightRequest flightRequest) {
        log.info("FlightService-create");

        Trip trip = tripService.findTripById(flightRequest.getTripId());
        Airplane airplane = airplaneService.findAirplaneById(flightRequest.getAirplaneId());
        Flight flightToSave = mapper.mapFlightRequestToFlight(flightRequest, trip, airplane);

        if (isFlightExist(flightToSave)) {
            throw new FlightAlreadyExistsException("The same flight already exists");
        }

        flightRepository.save(flightToSave);
        log.info("New flight was added, id: " + flightToSave.getFlightId());

        return flightToSave;
    }

    public FlightFullDataResponse findById(@NotNull Long id) {
        return mapper.mapFlightToFlightFullDataResponse(findFlightById(id));
    }

    private Flight findFlightById(@NotNull Long id) {
        log.info("FlightService-findById: " + id);
        return flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight with id = " + id + " not found"));
    }

    public ListFlightsFullDataResponse findAll(@NotNull PageRequest pageRequest) {
        log.info("FlightService-findAll");
        ListFlightsFullDataResponse listFlightsFullDataResponse = new ListFlightsFullDataResponse();
        listFlightsFullDataResponse.setTripResponseList(flightRepository.findAll(pageRequest)
                .stream()
                .map(mapper::mapFlightToFlightFullDataResponse)
                .collect(Collectors.toList()));
        listFlightsFullDataResponse.setTotal(listFlightsFullDataResponse.getTripResponseList().size());
        return listFlightsFullDataResponse;
    }

    public ListFlightsResponse find(@NotNull FindFlightRequest findFlightRequest,
                                    @NotNull Pageable pageable) {
        log.info("FlightService-find");
        ListFlightsResponse listFlightsResponse = new ListFlightsResponse();
        findDirectFlight(listFlightsResponse, findFlightRequest, pageable);

        if (isNotEmpty(findFlightRequest.getReturnDate())) {
            findReturnFlight(listFlightsResponse, findFlightRequest);
        }
        return listFlightsResponse;
    }

    private void findDirectFlight(@NotNull ListFlightsResponse listFlightsResponse,
                                  @NotNull FindFlightRequest findFlightRequest,
                                  @NotNull Pageable pageable) {
        log.info("FlightService-findDirectFlight");
        if (findFlightRequest.isFastest()) {
            log.info("FlightService-findDirectFlight: fastest");
            List<Flight> sortedFlights = flightRepository.findAll(new FlightSpecification(findFlightRequest),
                    Sort.by("duration"));
            if (!sortedFlights.isEmpty()) {
                listFlightsResponse.setFlights(
                        new ArrayList<>(Collections.singleton(mapper.mapFlightToFlightResponse(sortedFlights.get(0)))));
                return;
            }

        } else {
            log.info("FlightService-findDirectFlight: not fastest");
            Page<Flight> flights;

            flights = flightRepository.findAll(new FlightSpecification(findFlightRequest), pageable);

            if (flights.hasContent()) {
                listFlightsResponse.setFlights(flights.getContent().stream()
                        .map(mapper::mapFlightToFlightResponse)
                        .collect(Collectors.toList()));
                listFlightsResponse.setTotal(flights.getContent().size());
                return;
            }
        }
        log.info("FlightService-findDirectFlight: not found");
        listFlightsResponse.setMessage("Flights: " + findFlightRequest.getDepartureCity() +
                " - " + findFlightRequest.getArrivalCity() + " not found: ");

    }

    private void findReturnFlight(@NotNull ListFlightsResponse listFlightsResponse,
                                  @NotNull FindFlightRequest findFlightRequest) {
        log.info("FlightService-findReturnFlight");
        FindFlightRequest returnFlight = FindFlightRequest.builder()
                .departureCity(findFlightRequest.getArrivalCity())
                .arrivalCity(findFlightRequest.getDepartureCity())
                .departureDate(findFlightRequest.getReturnDate())
                .build();

        List<FlightResponse> returnFlights = flightRepository.findAll(new FlightSpecification(returnFlight)).stream()
                .map(mapper::mapFlightToFlightResponse).toList();

        if (returnFlights.isEmpty()) {
            log.info("FlightService-findReturnFlight: not found");
            String message = "No return flights on date: " + findFlightRequest.getReturnDate();
            if (listFlightsResponse.getMessage() == null) {
                listFlightsResponse.setMessage(message);
            } else {
                listFlightsResponse.setMessage(listFlightsResponse.getMessage() + message);
            }
        } else {
            listFlightsResponse.setReturnFlights(returnFlights);
            listFlightsResponse.setReturnTotal(returnFlights.size());
        }

    }

    @Transactional
    public Flight update(@NotNull Long id,
                         @NotNull FlightRequest flightRequest) {
        log.info("FlightService-update");
        findFlightById(id);
        Trip trip = tripService.findTripById(flightRequest.getTripId());
        Airplane airplane = airplaneService.findAirplaneById(flightRequest.getAirplaneId());
        Flight updatedFlight = mapper.mapFlightRequestToFlight(flightRequest, trip, airplane);
        updatedFlight.setFlightId(id);

        if (isFlightExist(updatedFlight)) {
            throw new FlightAlreadyExistsException("The same flight already exists");
        }

        flightRepository.save(updatedFlight);
        log.info("Flight with id: " + id + " was updated");
        return updatedFlight;
    }

    @Transactional
    public FlightFullDataResponse delete(@NotNull Long id) {
        log.info("FlightService-delete, id: " + id);
        Flight flightToDelete = findFlightById(id);
        flightRepository.deleteById(id);
        log.info("Flight id: " + id + " was deleted");
        FlightFullDataResponse response = mapper.mapFlightToFlightFullDataResponse(flightToDelete);
        response.setMessage("Flight was deleted");
        return response;
    }

    public FlightInfo findLimitedData(@NotNull Long id) {
        log.info("FlightService-info, id: " + id);
        return mapper.mapFlightToFlightInfo(findFlightById(id));
    }

    private boolean isFlightExist(@NotNull Flight flightToCheck) {
        return flightRepository.findFlightByTrip_TripIdAndAirplane_AirplaneIdAndFlightNumberAndDepartureDateTimeAndArrivalDateTime(
                flightToCheck.getTrip().getTripId(),
                flightToCheck.getAirplane().getAirplaneId(),
                flightToCheck.getFlightNumber(),
                flightToCheck.getDepartureDateTime(),
                flightToCheck.getArrivalDateTime()).isPresent();
    }
}
