package ru.hse.db.hw7.orm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryPercentageResponse {
    private String countryName;
    private Long vowelCount;
    private Double percentage;
}
