package ru.digitalchief.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.digitalchief.task.entity.Trip;

import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findByDepartureCityAndArrivalCity(String departureCity,
                                                     String arrivalCity);
}
