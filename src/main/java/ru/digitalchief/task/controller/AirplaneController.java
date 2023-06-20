package ru.digitalchief.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.digitalchief.task.entity.Airplane;
import ru.digitalchief.task.exeption.airplane.AirplaneValidationException;
import ru.digitalchief.task.request.AirplaneRequest;
import ru.digitalchief.task.response.airplane.AirplaneFullDataResponse;
import ru.digitalchief.task.response.airplane.ListAirplaneFullDataResponse;
import ru.digitalchief.task.service.AirplaneService;
import ru.digitalchief.task.util.error.ErrorUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/airplanes")
public class AirplaneController {
    private final AirplaneService airplaneService;

    @Autowired
    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @PostMapping("/create")
    public ResponseEntity<Airplane> create(@RequestBody @Valid AirplaneRequest airplaneRequest,
                                           BindingResult bindingResult) {
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(airplaneService.create(airplaneRequest));
    }

    @GetMapping("/find/{id}")
    public AirplaneFullDataResponse findById(@PathVariable("id") long id) {
        return airplaneService.findById(id);
    }

    @GetMapping("/find/all")
    public ListAirplaneFullDataResponse findAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return airplaneService.findAll(pageRequest);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Airplane> update(@PathVariable("id") long id,
                                         @Valid @RequestBody AirplaneRequest airplaneRequest,
                                         BindingResult bindingResult) {
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(airplaneService.update(id, airplaneRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(airplaneService.delete(id));
    }

    private void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = ErrorUtil.returnErrorMessage(bindingResult);
            throw new AirplaneValidationException(errorMessage);
        }
    }
}
