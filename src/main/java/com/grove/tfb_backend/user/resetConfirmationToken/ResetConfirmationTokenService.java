package com.grove.tfb_backend.user.resetConfirmationToken;

import com.grove.tfb_backend.user.Users;
import com.grove.tfb_backend.user.confirmationToken.ConfirmationToken;
import com.grove.tfb_backend.user.confirmationToken.ConfirmationTokenDao;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class ResetConfirmationTokenService {

    private final ResetConfirmationTokenDao resetconfirmationTokenDao;

    public ResetConfirmationTokenService(ResetConfirmationTokenDao resetconfirmationTokenDao) {
        this.resetconfirmationTokenDao = resetconfirmationTokenDao;
    }

    @Transactional
    public void mailConfirmation(String confirmationToken) {
        ResetConfirmationToken token = resetconfirmationTokenDao.findConfirmationTokenByToken(confirmationToken);

        if(token == null) throw new IllegalStateException("TOKEN NOT FOUND!");
        if(token.isConfirmed()) throw new IllegalStateException("TOKEN ALREADY CONFIRMED!");
        if(token.getExpiresAt().isBefore(LocalDateTime.now())) throw new IllegalStateException("TOKEN EXPIRED!");

        token.setConfirmed(true);
        Users user = token.getUser();
        user.setPassword(token.getTmpPass());
    }



}
