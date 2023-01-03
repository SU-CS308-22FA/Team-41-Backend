package com.grove.tfb_backend.user.resetConfirmationToken.resetConfirmationTokenDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetConfirmationTokenDto {

    private String mail;
    private String body;

}

