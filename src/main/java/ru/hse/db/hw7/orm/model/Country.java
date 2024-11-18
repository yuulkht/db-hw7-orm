package ru.hse.db.hw7.orm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Country {

    @Id
    @Column(name = "country_id")
    private String countryId;

    private String name;

    @Column(name = "area_sqkm")
    private int areaSqkm;

    private int population;
}
