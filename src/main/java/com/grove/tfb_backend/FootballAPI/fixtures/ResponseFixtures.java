package com.grove.tfb_backend.FootballAPI.fixtures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseFixtures {
    private FixtureFixtures fixture;
    private LeagueFixtures league;
    private TeamsFixtures teams;
    private GoalsFixtures goals;
    private ScoreFixtures score;
}
