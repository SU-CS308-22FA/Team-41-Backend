package com.grove.tfb_backend.players;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grove.tfb_backend.players.PlayerDto.PlayerInfo;
import com.grove.tfb_backend.teams.Teams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Players {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String teamName;

    private int age;

    private String position;
    private int number;

    private String pictureURL;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Teams team;

    public Players(PlayerInfo playerInfoDto) {
        name = playerInfoDto.getName();
        teamName = playerInfoDto.getTeamName();
        age = playerInfoDto.getAge();
        position = playerInfoDto.getPosition();
        number = playerInfoDto.getNumber();
        pictureURL = playerInfoDto.getPictureURL();
        team = playerInfoDto.getTeam();
    }
}
