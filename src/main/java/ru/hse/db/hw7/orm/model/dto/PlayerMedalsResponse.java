package ru.hse.db.hw7.orm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerMedalsResponse {
    private String playerName;
    private String olympicId;
}
