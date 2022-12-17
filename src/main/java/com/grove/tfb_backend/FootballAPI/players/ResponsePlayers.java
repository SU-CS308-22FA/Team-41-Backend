package com.grove.tfb_backend.FootballAPI.players;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponsePlayers {
    private TeamPlayers team;
    private List<PlayerPlayers> players;
}
