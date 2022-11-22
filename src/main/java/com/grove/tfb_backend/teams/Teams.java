package com.grove.tfb_backend.teams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.grove.tfb_backend.matches.Matches;
import com.grove.tfb_backend.players.Players;
import com.grove.tfb_backend.teams.TeamDto.TeamInfo;
import com.grove.tfb_backend.user.Users;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String city;

    private String stadiumName;

    private String logoURL;

    @OneToMany(mappedBy ="team" ,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Players> players;

    @OneToMany(mappedBy ="home_team" ,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Matches> homeMatches;

    @OneToMany(mappedBy ="away_team" ,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Matches> awayMatches;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "favoriteTeams")
    @JsonIgnore
    private List<Users> users;


    public Teams(TeamInfo teamInfoDto) {
        name = teamInfoDto.getName();
        city = teamInfoDto.getCity();
        stadiumName = teamInfoDto.getStadiumName();
        logoURL = teamInfoDto.getLogoURL();
    }


    public boolean equals(Teams team) {
        return id.equals(team.getId());
    }
}