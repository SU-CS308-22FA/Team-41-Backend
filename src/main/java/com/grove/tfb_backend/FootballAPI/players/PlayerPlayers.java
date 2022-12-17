package com.grove.tfb_backend.FootballAPI.players;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerPlayers {
    private int id;
    private String name;
    private Integer age;
    private Integer number;
    private String position;
    private String photo;
}
