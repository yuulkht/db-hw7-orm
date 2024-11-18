package ru.hse.db.hw7.orm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hse.db.hw7.orm.model.Event;
import ru.hse.db.hw7.orm.model.dto.EventResponse;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    @Query("""
        SELECT new ru.hse.db.hw7.orm.model.dto.EventResponse(e.name, COUNT(r))
        FROM Event e
        JOIN Result r ON e.eventId = r.eventId.eventId
        WHERE e.isTeamEvent = 0 AND r.medal = 'Gold'
        GROUP BY e.name
        HAVING COUNT(r) > 1
        """)
    List<EventResponse> findIndividualCompetitionsWithDraw();
}
