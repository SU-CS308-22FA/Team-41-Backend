package com.grove.tfb_backend.user;


import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.user.userDto.UserSignupDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String mail;

    private String gender;

    private String password;

    private boolean isReferee;

    private boolean isActive;

    @Column(nullable = true)
    private boolean isAdmin;

    @ManyToOne
    private Teams fanTeam;

    private LocalDate birthdate;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Teams> favoriteTeams;


    public Users(UserSignupDto signupDto,Teams fanT){
        name = signupDto.getName();
        mail = signupDto.getMail();
        gender = signupDto.getGender();
        password = signupDto.getPassword();
        birthdate = signupDto.getBirthdate();
        isAdmin = false;
        isActive = false;
        favoriteTeams = new ArrayList<>();
        favoriteTeams.add(fanT);
        fanTeam = fanT;

    }

}
