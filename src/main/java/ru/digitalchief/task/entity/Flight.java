package ru.digitalchief.task.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "flights")
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private long flightId;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id")
    private Trip trip;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "airplane_id")
    private Airplane airplane;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "departure")
    private LocalDateTime departureDateTime;

    @Column(name = "arrival")
    private LocalDateTime arrivalDateTime;

    @Column(name = "meal")
    private boolean meal;

    @Column(name = "hand_luggage")
    private boolean handLuggage;

    @Column(name = "luggage")
    private boolean luggage;

    @Column(name = "duration")
    private long duration;

    @Column(name = "canceled")
    private boolean canceled;
}
