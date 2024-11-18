package ru.hse.db.hw7.orm.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.db.hw7.orm.model.dto.*;
import ru.hse.db.hw7.orm.repository.*;

import java.util.List;

@RestController
@RequestMapping("/api/olympics")
@RequiredArgsConstructor
public class OlympicController {

    private final PlayerRepository playerRepository;
    private final ResultRepository resultRepository;
    private final EventRepository eventRepository;
    private final OlympicRepository olympicRepository;
    private final CountryRepository countryRepository;

    @GetMapping("/2004/players")
    @Operation(
            summary = "Для Олимпийских игр 2004 года сгенерируйте список " +
                    "(год рождения, количество игроков, количество золотых медалей)."
    )
    public List<PlayerStatsResponse> getPlayersStatsFor2004() {
        return playerRepository.findPlayersStatsFor2004();
    }

    @GetMapping("/individual/draws")
    @Operation(
            summary = "Перечислите все индивидуальные соревнования, " +
                    "в которых была ничья и два или более игрока выиграли " +
                    "золотую медаль."
    )
    public List<EventResponse> getIndividualDraws() {
        return eventRepository.findIndividualCompetitionsWithDraw();
    }

    @GetMapping("/medals")
    @Operation(
            summary = "Найдите всех игроков, которые выиграли хотя бы " +
                    "одну медаль (Gold, Silver или Bronze) на одной Олимпиаде."
    )
    public List<PlayerMedalsResponse> getPlayersWithMedals() {
        return playerRepository.findPlayersWithMedals();
    }

    @GetMapping("/countries/vowel-names")
    @Operation(
            summary = "В какой стране был наибольший процент игроков, чьи имена начинались с гласной?"
    )
    public CountryPercentageResponse getCountryWithHighestVowelPercentage() {
        return playerRepository.findCountryWithHighestVowelPercentage();
    }

    @GetMapping("/2000/country-ratio")
    @Operation(
            summary = "Для Олимпийских игр 2000 года найдите 5 стран с " +
                    "минимальным соотношением количества командных медалей " +
                    "к численности населения."
    )
    public List<CountryMedalRatioResponse> getCountryMedalRatioFor2000() {
        return countryRepository.findCountryMedalRatioFor2000();
    }
}
