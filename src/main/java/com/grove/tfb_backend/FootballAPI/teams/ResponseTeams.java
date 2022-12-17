package com.grove.tfb_backend.FootballAPI.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseTeams {
    private TeamTeams team;
    private VenueTeams parameters;
}
