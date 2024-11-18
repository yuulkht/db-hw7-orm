package ru.hse.db.hw7.orm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Olympic {
    @Id
    @Column(name = "olympic_id")
    private String olympicId;


    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country countryId;

    private String city;

    private int year;
    private LocalDate startdate;
    private LocalDate enddate;
}
