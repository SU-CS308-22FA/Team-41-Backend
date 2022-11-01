package com.grove.tfb_backend.user.confirmationToken;

import com.grove.tfb_backend.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenDao extends JpaRepository<ConfirmationToken,Long> {

    ConfirmationToken findConfirmationTokenByToken(String token);

    ConfirmationToken findConfirmationTokenByUser(Users user);
}
