package com.grove.tfb_backend.user.resetConfirmationToken;

import com.grove.tfb_backend.user.Users;
import com.grove.tfb_backend.user.confirmationToken.ConfirmationToken;
import com.grove.tfb_backend.user.resetConfirmationToken.ResetConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetConfirmationTokenDao extends JpaRepository<ResetConfirmationToken,Long> {

    ResetConfirmationToken findConfirmationTokenByToken(String token);

    boolean existsByUser(Users user);

    ResetConfirmationToken findResetConfirmationTokenByUser(Users user);
}