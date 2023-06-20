package ru.digitalchief.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalchief.task.entity.Airplane;
import ru.digitalchief.task.exeption.airplane.AirplaneAlreadyExistException;
import ru.digitalchief.task.exeption.airplane.AirplaneNotFoundException;
import ru.digitalchief.task.mapper.Mapper;
import ru.digitalchief.task.repository.AirplaneRepository;
import ru.digitalchief.task.request.AirplaneRequest;
import ru.digitalchief.task.response.airplane.AirplaneFullDataResponse;
import ru.digitalchief.task.response.airplane.ListAirplaneFullDataResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AirplaneService {
    private final AirplaneRepository airplaneRepository;
    private final Mapper mapper;

    @Autowired
    public AirplaneService(AirplaneRepository airplaneRepository,
                           Mapper mapper) {
        this.airplaneRepository = airplaneRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Airplane create(@NotNull @Valid AirplaneRequest airplaneRequest) {
        log.info("AirplaneService-create");

        if (isAirplaneExist(airplaneRequest.getModel())) {
            throw new AirplaneAlreadyExistException("The same airplane already exists");
        }

        Airplane airplaneToSave = mapper.mapAirplaneRequestToAirplane(airplaneRequest);
        airplaneRepository.save(airplaneToSave);
        log.info("New airplane was added, id: " + airplaneToSave.getAirplaneId());

        return airplaneToSave;
    }

    public AirplaneFullDataResponse findById(@NotNull Long id) {
        log.info("AirplaneService-findById: " + id);
        return mapper.mapAirplaneToAirplaneFullDataResponse(findAirplaneById(id));
    }

    protected Airplane findAirplaneById(@NotNull Long id) {
        log.info("AirplaneService-findAirplaneById: " + id);
        return airplaneRepository.findById(id).
                orElseThrow(() -> new AirplaneNotFoundException("Airplane with id: " + id + " not found"));
    }

    public ListAirplaneFullDataResponse findAll(@NotNull PageRequest pageRequest) {
        log.info("AirplaneService-findAll");
        ListAirplaneFullDataResponse listAirplaneFullDataResponse = new ListAirplaneFullDataResponse();
        listAirplaneFullDataResponse.setAirplanes(airplaneRepository.findAll(pageRequest)
                .stream()
                .map(mapper::mapAirplaneToAirplaneFullDataResponse)
                .collect(Collectors.toList()));
        listAirplaneFullDataResponse.setTotal(listAirplaneFullDataResponse.getAirplanes().size());
        return listAirplaneFullDataResponse;
    }

    @Transactional
    public Airplane update(@NotNull Long id,
                           @NotNull AirplaneRequest airplaneRequest) {
        log.info("AirplaneService-update");
        findAirplaneById(id);

        Airplane airplaneToUpdate = mapper.mapAirplaneRequestToAirplane(airplaneRequest);

        airplaneToUpdate.setAirplaneId(id);
        airplaneRepository.save(airplaneToUpdate);
        log.info("Airplane with id: " + id + " was updated");
        return airplaneToUpdate;
    }

    @Transactional
    public AirplaneFullDataResponse delete(@NotNull Long id) {
        log.info("AirplaneService-delete, id: " + id);
        Airplane airplaneToDelete = findAirplaneById(id);
        airplaneRepository.delete(airplaneToDelete);
        log.info("Airplane with id: " + id + ", was deleted");
        AirplaneFullDataResponse response = mapper.mapAirplaneToAirplaneFullDataResponse(airplaneToDelete);
        response.setMessage("Airplane was deleted");
        return response;
    }

    private boolean isAirplaneExist(@NotNull String airplaneModelToCheck) {
        return airplaneRepository.findAirplaneByModel(airplaneModelToCheck).isPresent();
    }
}
