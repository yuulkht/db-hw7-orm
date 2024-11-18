package ru.hse.db.hw7.orm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Player {
    @Id
    @Column(name = "player_id")
    private String playerId;


    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country countryId;

    private LocalDate birthdate;
}
