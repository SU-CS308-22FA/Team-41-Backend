package com.grove.tfb_backend.mailSender;




import com.grove.tfb_backend.user.confirmationToken.confirmationTokenDto.ConfirmationTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

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


}
