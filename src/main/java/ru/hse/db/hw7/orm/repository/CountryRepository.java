package ru.hse.db.hw7.orm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hse.db.hw7.orm.model.Country;
import ru.hse.db.hw7.orm.model.dto.CountryMedalRatioResponse;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    @Query("""
        SELECT new ru.hse.db.hw7.orm.model.dto.CountryMedalRatioResponse(
            c.name, 
            COUNT(r), 
            CAST(COUNT(r) * 1.0 / c.population AS double)
        )
        FROM Country c
        JOIN Player p ON c.countryId = p.countryId.countryId
        JOIN Result r ON p.playerId = r.playerId.playerId
        JOIN Event e ON r.eventId.eventId = e.eventId
        JOIN Olympic o ON e.olympicId.olympicId = o.olympicId
        WHERE o.year = 2000 AND e.isTeamEvent = 1
        GROUP BY c.name, c.population
        ORDER BY (COUNT(r) * 1.0 / c.population)
        LIMIT 5
        """)
    List<CountryMedalRatioResponse> findCountryMedalRatioFor2000();
}
