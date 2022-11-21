package com.grove.tfb_backend.user.userDto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDto {

    private String name;

    private String mail;

    private String password;

    private String gender;

    private String fanTeam;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;

}
