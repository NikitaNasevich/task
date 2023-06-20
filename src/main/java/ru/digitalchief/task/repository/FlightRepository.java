package ru.digitalchief.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.digitalchief.task.entity.Flight;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {
    Optional<Flight> findFlightByTrip_TripIdAndAirplane_AirplaneIdAndFlightNumberAndDepartureDateTimeAndArrivalDateTime(
            long tripId,
            long airplaneId,
            String flightNumber,
            LocalDateTime departureDateTime,
            LocalDateTime arrivalDateTime);
}
