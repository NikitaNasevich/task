package ru.digitalchief.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "airplanes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airplane_id")
    private long airplaneId;

    @Column(name = "model")
    private String model;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @Column(name = "socket")
    private boolean socket;

    @Column(name = "media")
    private boolean media;

    @Column(name = "wi_fi")
    private boolean wiFi;

    @Column(name = "seat_pitch")
    private int seatPitch;

    @Column(name = "seat_width")
    private int seatWidth;

    @Column(name = "has_first_class")
    private boolean hasFirstClass;

    @Column(name = "chair_scheme")
    private String chairScheme;

    @JsonBackReference
    @OneToMany(mappedBy = "airplane")
    private List<Flight> flights;
 }
