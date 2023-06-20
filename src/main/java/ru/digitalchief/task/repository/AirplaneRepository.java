package ru.digitalchief.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.digitalchief.task.entity.Airplane;

import java.util.Optional;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    Optional<Airplane> findAirplaneByModel(String model);
}
