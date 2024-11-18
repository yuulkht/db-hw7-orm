package ru.hse.db.hw7.orm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionResponse {
    private String eventName;
    private String sportName;
    private Integer playerCount;
}
