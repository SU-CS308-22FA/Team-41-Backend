package com.grove.tfb_backend.mailSender;




import com.grove.tfb_backend.matches.Matches;
import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.user.Users;
import com.grove.tfb_backend.user.confirmationToken.confirmationTokenDto.ConfirmationTokenDto;
import com.grove.tfb_backend.user.resetConfirmationToken.ResetConfirmationToken;
import com.grove.tfb_backend.user.resetConfirmationToken.resetConfirmationTokenDto.ResetConfirmationTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MailService {

    private final JavaMailSender mailSender;



    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSignupConfirmation(ConfirmationTokenDto confirmationMail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("emrhn2001@gmail.com");
        mailMessage.setTo(confirmationMail.getMail());
        mailMessage.setSubject("TFB Registration Confirmation");
        mailMessage.setText("Activate your account by clicking this link:  " + confirmationMail.getLink());
        mailSender.send(mailMessage);
    }

    public void sendResetPasswordConfirmation(ResetConfirmationTokenDto resetConfirmationMail){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("emrhn2001@gmail.com");
        mailMessage.setTo(resetConfirmationMail.getMail());
        mailMessage.setSubject("TFB Reset Password");
        mailMessage.setText(resetConfirmationMail.getBody());
        mailSender.send(mailMessage);
    }


    public void sendDailyMatchNotifications(String[] mailList,String text) {


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("emrhn2001@gmail.com");
        mailMessage.setSubject("TFP Match Notification");
        mailMessage.setText(text);
        mailMessage.setTo(mailList);
        mailSender.send(mailMessage);


    }
}
