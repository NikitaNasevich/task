package ru.digitalchief.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.digitalchief.task.entity.Trip;
import ru.digitalchief.task.exeption.trip.TripValidationException;
import ru.digitalchief.task.request.TripRequest;
import ru.digitalchief.task.response.trip.ListTripsFullDataResponse;
import ru.digitalchief.task.response.trip.ListTripsResponse;
import ru.digitalchief.task.response.trip.TripFullDataResponse;
import ru.digitalchief.task.service.TripService;
import ru.digitalchief.task.util.error.ErrorUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/trips")
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/create")
    public ResponseEntity<Trip> create(@RequestBody @Valid TripRequest tripRequest,
                                       BindingResult bindingResult) {
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(tripService.save(tripRequest));
    }

    @GetMapping("/find/{id}")
    public TripFullDataResponse findById(@PathVariable("id") long id) {
        return tripService.findById(id);
    }

    @GetMapping("/find-all")
    public ListTripsFullDataResponse findAllTrips(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return tripService.findAllTrips(pageRequest);
    }

    @GetMapping("/find-all-limited-data")
    public ListTripsResponse findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return tripService.findAll(pageRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Trip> update(@PathVariable("id") long id,
                                       @Valid @RequestBody TripRequest tripRequest,
                                       BindingResult bindingResult) {
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(tripService.update(id, tripRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TripFullDataResponse> delete(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(tripService.delete(id));
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = ErrorUtil.returnErrorMessage(bindingResult);
            throw new TripValidationException(errorMessage);
        }
    }
}
