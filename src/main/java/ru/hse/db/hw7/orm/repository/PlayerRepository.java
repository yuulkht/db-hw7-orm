package ru.hse.db.hw7.orm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hse.db.hw7.orm.model.Player;
import ru.hse.db.hw7.orm.model.dto.CountryPercentageResponse;
import ru.hse.db.hw7.orm.model.dto.PlayerMedalsResponse;
import ru.hse.db.hw7.orm.model.dto.PlayerStatsResponse;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    @Query("""
            SELECT new ru.hse.db.hw7.orm.model.dto.PlayerStatsResponse(
                YEAR(p.birthdate), 
                COUNT(DISTINCT p.playerId), 
                COUNT(r)
            )
            FROM Player p
            JOIN Result r ON p.playerId = r.playerId.playerId
            JOIN Event e ON r.eventId.eventId = e.eventId
            JOIN Olympic o ON e.olympicId.olympicId = o.olympicId
            WHERE o.year = 2004 AND r.medal = 'Gold'
            GROUP BY YEAR(p.birthdate)
            """)
    List<PlayerStatsResponse> findPlayersStatsFor2004();

    @Query("""
            SELECT new ru.hse.db.hw7.orm.model.dto.PlayerMedalsResponse(
                p.name, o.olympicId
            )
            FROM Player p
            JOIN Result r ON p.playerId = r.playerId.playerId
            JOIN Event e ON r.eventId.eventId = e.eventId
            JOIN Olympic o ON e.olympicId.olympicId = o.olympicId
            WHERE r.medal IN ('Gold', 'Silver', 'Bronze')
            GROUP BY p.name, o.olympicId
            """)
    List<PlayerMedalsResponse> findPlayersWithMedals();

    @Query("""
            SELECT new ru.hse.db.hw7.orm.model.dto.CountryPercentageResponse(
                c.name, 
                COUNT(p), 
                CAST ((COUNT(p) * 1.0 / c.population) * 100 AS double)
            )
            FROM Player p
            JOIN Country c ON p.countryId.countryId = c.countryId
            WHERE UPPER(SUBSTRING(p.name, 1, 1)) IN ('A', 'E', 'I', 'O', 'U')
            GROUP BY c.name, c.population
            ORDER BY (COUNT(p) * 1.0 / c.population) DESC
            LIMIT 1
            """)
    CountryPercentageResponse findCountryWithHighestVowelPercentage();

}
