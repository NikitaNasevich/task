package ru.digitalchief.task.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.digitalchief.task.entity.Flight;
import ru.digitalchief.task.exeption.flight.FlightValidationException;
import ru.digitalchief.task.request.FindFlightRequest;
import ru.digitalchief.task.request.FlightRequest;
import ru.digitalchief.task.response.flight.FlightFullDataResponse;
import ru.digitalchief.task.response.flight.FlightInfo;
import ru.digitalchief.task.response.flight.ListFlightsFullDataResponse;
import ru.digitalchief.task.response.flight.ListFlightsResponse;
import ru.digitalchief.task.service.FlightService;
import ru.digitalchief.task.util.error.ErrorUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/create")
    public ResponseEntity<Flight> create(@RequestBody @Valid FlightRequest flightRequest,
                                         BindingResult bindingResult) {
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(flightService.create(flightRequest));
    }

    @GetMapping("/find/{id}")
    public FlightFullDataResponse findById(@PathVariable("id") long id) {
        return flightService.findById(id);
    }

    @GetMapping("/find/all")
    public ListFlightsFullDataResponse findAll(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return flightService.findAll(pageRequest);
    }

    @PostMapping("/find-limited-data")
    public ListFlightsResponse findBySpec(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size,
                                          @RequestBody @Valid FindFlightRequest flightRequest,
                                          BindingResult bindingResult) {
        validate(bindingResult);
        Pageable pageable = PageRequest.of(page, size);
        return flightService.find(flightRequest, pageable);
    }

    @GetMapping("/find-limited-data/{id}")
    public FlightInfo showInfo(@PathVariable("id") long id) {
        return flightService.findLimitedData(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> update(@PathVariable("id") long id,
                                         @Valid @RequestBody FlightRequest flightRequest,
                                         BindingResult bindingResult) {
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(flightService.update(id, flightRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(flightService.delete(id));
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = ErrorUtil.returnErrorMessage(bindingResult);
            throw new FlightValidationException(errorMessage);
        }
    }
}
