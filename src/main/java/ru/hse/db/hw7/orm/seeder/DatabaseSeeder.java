package ru.hse.db.hw7.orm.seeder;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import ru.hse.db.hw7.orm.model.*;
import ru.hse.db.hw7.orm.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DatabaseSeeder {

    private CountryRepository countryRepository;
    private OlympicRepository olympicRepository;
    private PlayerRepository playerRepository;
    private EventRepository eventRepository;
    private ResultRepository resultRepository;

    private final EntityManager entityManager;
    private final Faker faker = new Faker();

    public DatabaseSeeder(EntityManager entityManager,
                          CountryRepository countryRepository,
                          OlympicRepository olympicRepository,
                          PlayerRepository playerRepository,
                          EventRepository eventRepository,
                          ResultRepository resultRepository) {

        this.entityManager = entityManager;
        this.countryRepository = countryRepository;
        this.olympicRepository = olympicRepository;
        this.playerRepository = playerRepository;
        this.eventRepository = eventRepository;
        this.resultRepository = resultRepository;
    }

    @Transactional
    public void seedDatabase(int countriesCount, int playersCount, int olympicsCount, int eventsCount, int resultsCount) {
        try {
            List<Country> countries = seedCountries(countriesCount);
            List<Player> players = seedPlayers(playersCount, countries);
            List<Olympic> olympics = seedOlympics(olympicsCount, countries);
            List<Event> events = seedEvents(eventsCount, olympics);
            seedResults(resultsCount, players, events);

            System.out.println("Database successfully seeded!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Country> seedCountries(int count) {
        List<Country> countries = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Country country = new Country();
            country.setName(faker.country().name());
            country.setCountryId(UUID.randomUUID().toString());
            country.setAreaSqkm(faker.number().numberBetween(1000, 2000000));
            country.setPopulation(faker.number().numberBetween(1000000, 300000000));
            countries.add(country);
        }
        // Сохраняем все страны за один раз
        return countryRepository.saveAll(countries);
    }

    private List<Player> seedPlayers(int count, List<Country> countries) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Player player = new Player();
            player.setPlayerId(UUID.randomUUID().toString());
            player.setName(faker.name().fullName());
            player.setBirthdate(LocalDate.of(
                    faker.number().numberBetween(1970, 2010),
                    faker.number().numberBetween(1, 12),
                    faker.number().numberBetween(1, 28)
            ));
            player.setCountryId(countries.get(faker.random().nextInt(countries.size())));
            players.add(player);
        }
        // Сохраняем всех игроков за один раз
        return playerRepository.saveAll(players);
    }

    private List<Olympic> seedOlympics(int count, List<Country> countries) {
        List<Olympic> olympics = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Olympic olympic = new Olympic();
            olympic.setOlympicId(UUID.randomUUID().toString());
            olympic.setCity(faker.address().city());
            olympic.setCountryId(countries.get(faker.random().nextInt(countries.size())));
            olympic.setYear(faker.number().numberBetween(1900, 2024));
            olympic.setStartdate(LocalDate.of(
                    olympic.getYear(),
                    faker.number().numberBetween(1, 6),
                    faker.number().numberBetween(1, 28)
            ));
            olympic.setEnddate(olympic.getStartdate().plusDays(faker.number().numberBetween(10, 30)));
            olympics.add(olympic);
        }
        // Сохраняем все олимпиады за один раз
        return olympicRepository.saveAll(olympics);
    }

    private List<Event> seedEvents(int count, List<Olympic> olympics) {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Event event = new Event();
            event.setEventId(UUID.randomUUID().toString());
            event.setName(faker.esports().event());
            event.setEventtype(faker.options().option("Individual", "Team"));
            event.setIsTeamEvent(event.getEventtype().equals("Team") ? 1 : 0);
            event.setNumPlayersInTeam(event.getIsTeamEvent() == 1 ? faker.number().numberBetween(2, 11) : 1);
            event.setResultNotedIn(faker.options().option("Time", "Points", "Distance"));
            event.setOlympicId(olympics.get(faker.random().nextInt(olympics.size())));
            events.add(event);
        }
        // Сохраняем все события за один раз
        return eventRepository.saveAll(events);
    }

    private void seedResults(int count, List<Player> players, List<Event> events) {
        List<Result> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Result result = new Result();
            result.setId(UUID.randomUUID());
            result.setPlayerId(players.get(faker.random().nextInt(players.size())));
            result.setEventId(events.get(faker.random().nextInt(events.size())));
            result.setMedal(faker.options().option("Gold", "Silver", "Bronze", null));
            result.setResult(faker.number().randomDouble(2, 10, 100));
            results.add(result);
        }
        // Сохраняем все результаты за один раз
        resultRepository.saveAll(results);
    }

}
