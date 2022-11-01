package com.grove.tfb_backend.user.userDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordChangeRequest {

    private Long id;
    private String password;
    private String oldPassword;
}
