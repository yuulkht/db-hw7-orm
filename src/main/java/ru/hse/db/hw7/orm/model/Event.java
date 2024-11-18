package ru.hse.db.hw7.orm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @Column(name = "event_id")
    private String eventId;


    private String name;
    private String eventtype;

    @ManyToOne
    @JoinColumn(name = "olympic_id", referencedColumnName = "olympic_id")
    private Olympic olympicId;

    @Column(name = "is_team_event")
    private int isTeamEvent;

    @Column(name = "num_players_in_team")
    private int numPlayersInTeam;

    @Column(name = "result_noted_in")
    private String resultNotedIn;
}
