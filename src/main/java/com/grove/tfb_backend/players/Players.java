package com.grove.tfb_backend.players;

import com.grove.tfb_backend.players.PlayerDto.PlayerInfo;
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

    public Players(PlayerInfo playerInfoDto) {
        name = playerInfoDto.getName();
        teamName = playerInfoDto.getTeamName();
        age = playerInfoDto.getAge();
        position = playerInfoDto.getPosition();
        number = playerInfoDto.getNumber();
        pictureURL = playerInfoDto.getPictureURL();
    }
}
