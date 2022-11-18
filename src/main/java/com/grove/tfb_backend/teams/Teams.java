package com.grove.tfb_backend.teams;

import com.grove.tfb_backend.teams.TeamDto.TeamInfo;
import lombok.*;
import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String city;

    private String stadiumName;

    private String logoURL;

    public Teams(TeamInfo teamInfoDto) {
        name = teamInfoDto.getName();
        city = teamInfoDto.getCity();
        stadiumName = teamInfoDto.getStadiumName();
        logoURL = teamInfoDto.getLogoURL();
    }
}
