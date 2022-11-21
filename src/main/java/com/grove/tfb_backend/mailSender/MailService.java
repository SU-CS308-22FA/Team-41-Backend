package com.grove.tfb_backend.mailSender;




import com.grove.tfb_backend.matches.Matches;
import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.user.Users;
import com.grove.tfb_backend.user.confirmationToken.confirmationTokenDto.ConfirmationTokenDto;
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


    public void sendDailyMatchNotifications(List<Matches> todaysMatches) {

        for (Matches m:todaysMatches){

            List<Users> homeTeamUsers = m.getHome_team().getUsers();
            List<Users> awayTeamUsers = m.getAway_team().getUsers();
            homeTeamUsers.addAll(awayTeamUsers);
            Set<String> mails = homeTeamUsers.stream()
                                    .map(Users::getMail)
                                    .collect(Collectors.toSet());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("emrhn2001@gmail.com");
            mailMessage.setSubject("TFP Match Notification");
            mailMessage.setText("One of your favorite teams has a match today. Here is the details:\n\n"+
                    m.getHomeTeamName()+"  -  "+ m.getAwayTeamName() + "\n\n"+
                    "Referee: " + m.getReferee() + "\n\n" +
                    "Time   : " + m.getDateAndTime());
            String[] mailsArray = mails.stream().toArray(String[]::new);
            mailMessage.setTo(mailsArray);
            mailSender.send(mailMessage);

        }
    }
}
