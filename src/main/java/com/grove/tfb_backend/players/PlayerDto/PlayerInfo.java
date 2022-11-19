package com.grove.tfb_backend.players.PlayerDto;

import com.grove.tfb_backend.teams.Teams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerInfo {

    private String name;

    private String teamName;

    private int age;

    private String position;

    private int number;

    private String pictureURL;

    private Teams team;

}
