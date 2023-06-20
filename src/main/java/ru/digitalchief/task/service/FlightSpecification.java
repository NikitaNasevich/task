package ru.digitalchief.task.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.digitalchief.task.entity.Flight;
import ru.digitalchief.task.request.FindFlightRequest;
import ru.digitalchief.task.util.time.TimeFormatter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

@AllArgsConstructor
public class FlightSpecification implements Specification<Flight> {

    private FindFlightRequest request;

    @Override
    public Predicate toPredicate(Root<Flight> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (isNotEmpty(request.getDepartureCity())) {
            predicates.add(criteriaBuilder.equal(root.get("trip").get("departureCity"), request.getDepartureCity()));
        }
        if (isNotEmpty(request.getArrivalCity())) {
            predicates.add(criteriaBuilder.equal(root.get("trip").get("arrivalCity"), request.getArrivalCity()));
        }
        if (isNotEmpty(request.getDepartureDate())) {
            LocalDateTime departureDate = TimeFormatter.formatStringToDateTime(request.getDepartureDate());
            predicates.add(criteriaBuilder.between(root.get("departureDateTime"), departureDate, departureDate.plusDays(1)));
        }
        if (request.getMinSeatCount() != 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("airplane").get("numberOfSeats"), request.getMinSeatCount()));
        }
        if (request.getMaxSeatCount() != 0) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("airplane").get("numberOfSeats"), request.getMaxSeatCount()));
        }
        if (request.isFirstClass()) {
            predicates.add(criteriaBuilder.equal(root.get("airplane").get("hasFirstClass"), request.isFirstClass()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
