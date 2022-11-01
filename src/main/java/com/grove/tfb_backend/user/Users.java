package com.grove.tfb_backend.user;


import com.grove.tfb_backend.user.userDto.UserSignupDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private boolean isActive;

    public Users(UserSignupDto signupDto){
        name = signupDto.getName();
        mail = signupDto.getMail();
        gender = signupDto.getGender();
        password = signupDto.getPassword();
        isActive = false;

    }

}
