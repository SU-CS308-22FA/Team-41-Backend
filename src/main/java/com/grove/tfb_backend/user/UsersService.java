package com.grove.tfb_backend.user;

import com.grove.tfb_backend.mailSender.MailService;
import com.grove.tfb_backend.matches.Matches;
import com.grove.tfb_backend.matches.MatchesService;
import com.grove.tfb_backend.teams.TeamDto.TeamInfo;
import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.teams.TeamsDao;
import com.grove.tfb_backend.user.confirmationToken.ConfirmationToken;
import com.grove.tfb_backend.user.confirmationToken.ConfirmationTokenDao;
import com.grove.tfb_backend.user.confirmationToken.confirmationTokenDto.ConfirmationTokenDto;
import com.grove.tfb_backend.user.userDto.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private final UsersDao usersDao;
    private final MailService mailService;
    private final TeamsDao teamsDao;

    private final MatchesService matchesService;

    private final ConfirmationTokenDao confirmationTokenDao;

    public UsersService(UsersDao usersDao, MailService mailService, TeamsDao teamsDao, MatchesService matchesService, ConfirmationTokenDao confirmationTokenDao) {
        this.usersDao = usersDao;
        this.mailService = mailService;
        this.teamsDao = teamsDao;
        this.matchesService = matchesService;
        this.confirmationTokenDao = confirmationTokenDao;
    }

    @Transactional
    public void signup(UserSignupDto signupDto) {

        if (usersDao.existsByMail(signupDto.getMail())) throw new IllegalStateException("MAIL IN USE!");
        Teams fanTeam = teamsDao.findTeamsByName(signupDto.getFanTeam());

        Users newUser = new Users(signupDto,fanTeam);
        Users userDb = usersDao.save(newUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(userDb);
        confirmationTokenDao.save(confirmationToken);

        ConfirmationTokenDto confirmationMail = new ConfirmationTokenDto(signupDto.getMail(), "https://tfb308.herokuapp.com/api/v1/user/activation?token="+confirmationToken.getToken());



        mailService.sendSignupConfirmation(confirmationMail);


    }

    public LoginResponse login(UserLoginRequest loginRequest) {

        Users user = usersDao.findUserByMail(loginRequest.getMail());

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");
        if (!user.getPassword().equals(loginRequest.getPassword())) throw new IllegalStateException("WRONG PASSWORD!");
        if(!user.isActive()) throw new IllegalStateException("ACCOUNT IS NOT ACTIVE!");

        return new LoginResponse(user.getId(), user.isAdmin());
        // JWT will be returned later.
    }

    public UserInfo getUserInfo(Long id) {
        Users user = usersDao.findUserById(id);

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");

        return new UserInfo(user.getName(), user.getMail(), user.getGender(),user.getBirthdate(),user.getFanTeam().getName());
    }

    @Transactional
    public void deleteUser(Long id) {
        Users user = usersDao.findUserById(id);

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");

        ConfirmationToken token = confirmationTokenDao.findConfirmationTokenByUser(user);
        confirmationTokenDao.delete(token);
        usersDao.delete(user);


    }

    @Transactional
    public void updateUser(Long id,UserInfo userInfo) {
        Users user = usersDao.findUserById(id);

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");

        user.setGender(userInfo.getGender());
        user.setMail(userInfo.getMail());
        user.setName(userInfo.getName());
    }

    @Transactional
    public void changePassword(PasswordChangeRequest request) {
        Users user = usersDao.findUserById(request.getId());

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");
        if (!request.getOldPassword().equals(user.getPassword())) throw new IllegalStateException("INVALID PASSWORD!");

        user.setPassword(request.getPassword());
    }

    public List<Teams> getFavTeams(Long id) {
        Users user = usersDao.findUserById(id);

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");
        /*List<TeamInfo> toBeReturned = new ArrayList<>();
        for(Teams t: favTeams){
            toBeReturned.add(new TeamInfo(t.getName(),t.getCity(),t.getStadiumName(),t.getLogoURL()));
        }*/
        return user.getFavoriteTeams();
    }

    @Transactional
    public void addFavTeam(FavTeamAdd body) {
        Users user = usersDao.findUserById(body.getUserId());
        Teams team = teamsDao.findTeamById(body.getTeamId());

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");
        if (team == null) throw new IllegalStateException("TEAM NOT FOUND!");

        List<Teams> currentList = user.getFavoriteTeams();

        boolean isExist = false;
        for(Teams t: currentList){
            if (t.equals(team)) isExist = true;
        }

        if(isExist) throw new IllegalStateException(team.getName()+" ALREADY IN YOUR FAVORITE LIST!");

        currentList.add(team);

        user.setFavoriteTeams(currentList);

    }

    @Transactional
    public void deleteFavTeam(FavTeamAdd body){
        Users user = usersDao.findUserById(body.getUserId());
        Teams team = teamsDao.findTeamById(body.getTeamId());

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");
        if (team == null) throw new IllegalStateException("TEAM NOT FOUND!");

        List<Teams> currentList = user.getFavoriteTeams();

        int index = -1;
        int iterator = 0;
        for(Teams t: currentList){
            if (t.equals(team)){
                index = iterator;
                break;
            }
            iterator++;
        }

        if(index == -1) throw new IllegalStateException(team.getName() +" DOES NOT EXIST IN YOUR FAVORITE LIST!");

        currentList.remove(index);

        user.setFavoriteTeams(currentList);
    }


    @Transactional
    @Scheduled(cron = "0 0 8 * * *",zone = "Europe/Istanbul")  // at 8am every day.
    public void sendNotificationBeforeMatches(){
        List<Matches> todaysMatches = matchesService.getAllTodaysMatches();

        for (Matches m:todaysMatches) {

            List<Users> homeTeamUsers = m.getHome_team().getUsers();
            List<Users> awayTeamUsers = m.getAway_team().getUsers();
            homeTeamUsers.addAll(awayTeamUsers);
            Set<String> mails = homeTeamUsers.stream()
                    .map(Users::getMail)
                    .collect(Collectors.toSet());

            String[] mailsArray = mails.stream().toArray(String[]::new);
            String text = "One of your favorite teams has a match today. Here is the details:\n\n"+
                    m.getHomeTeamName()+"  -  "+ m.getAwayTeamName() + "\n\n"+
                    "Referee: " + m.getReferee() + "\n\n" +
                    "Time   : " + m.getDateAndTime();

            mailService.sendDailyMatchNotifications(mailsArray,text);

        }



    }

    public Integer getCount() {
        List<Users> users = usersDao.findAll();
        return users.size();
    }

    public List<UserInfo> getUsersIfAdmin(Long adminId) {
        Users admin = usersDao.findUserById(adminId);
        if(admin.isAdmin())
            return usersDao.findAllInfo();
        return null;
    }

    @Transactional
    public void banUser(Long adminId, Long userId) {
        Users admin = usersDao.findUserById(adminId);
        if(admin == null)  throw new IllegalStateException("ADMIN USER NOT FOUND!");
        if(!admin.isAdmin()) throw new IllegalStateException("NO PERMISSION!");

        Users user = usersDao.findUserById(userId);
        if(user == null) throw new IllegalStateException("USER NOT FOUND!");
        if(user.isAdmin()) throw new IllegalStateException("YOU CANNOT BAN AN ADMIN!");

        user.setActive(false);
    }

}
