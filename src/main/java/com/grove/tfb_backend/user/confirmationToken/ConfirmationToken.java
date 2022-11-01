package com.grove.tfb_backend.user.confirmationToken;


import com.grove.tfb_backend.user.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiresAt;

    private boolean isConfirmed;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;


    public ConfirmationToken(Users user){
        this.isConfirmed = false;
        this.expiresAt = LocalDateTime.now().plusMinutes(15);
        this.token = UUID.randomUUID().toString();
        this.user = user;
    }
}
