package com.grove.tfb_backend.user.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoPublic {

    private Long userId;

    private String name;

    private String mail;

    private String gender;

    private LocalDate birthdate;

    private String fanTeam;
}

