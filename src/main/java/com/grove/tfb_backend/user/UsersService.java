package com.grove.tfb_backend.user;

import com.grove.tfb_backend.mailSender.MailService;
import com.grove.tfb_backend.user.confirmationToken.ConfirmationToken;
import com.grove.tfb_backend.user.confirmationToken.ConfirmationTokenDao;
import com.grove.tfb_backend.user.confirmationToken.confirmationTokenDto.ConfirmationTokenDto;
import com.grove.tfb_backend.user.userDto.PasswordChangeRequest;
import com.grove.tfb_backend.user.userDto.UserInfo;
import com.grove.tfb_backend.user.userDto.UserLoginRequest;
import com.grove.tfb_backend.user.userDto.UserSignupDto;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsersService {

    private final UsersDao usersDao;
    private final MailService mailService;

    private final ConfirmationTokenDao confirmationTokenDao;

    public UsersService(UsersDao usersDao, MailService mailService, ConfirmationTokenDao confirmationTokenDao) {
        this.usersDao = usersDao;
        this.mailService = mailService;
        this.confirmationTokenDao = confirmationTokenDao;
    }

    @Transactional
    public void signup(UserSignupDto signupDto) {

        if (usersDao.existsByMail(signupDto.getMail())) throw new IllegalStateException("MAIL IN USE!");

        Users newUser = new Users(signupDto);
        Users userDb = usersDao.save(newUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(userDb);
        confirmationTokenDao.save(confirmationToken);

        ConfirmationTokenDto confirmationMail = new ConfirmationTokenDto(signupDto.getMail(), "https://tfb308.herokuapp.com/api/v1/user/activation?token="+confirmationToken.getToken());



        mailService.sendSignupConfirmation(confirmationMail);


    }

    public Long login(UserLoginRequest loginRequest) {

        Users user = usersDao.findUserByMail(loginRequest.getMail());

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");
        if (!user.getPassword().equals(loginRequest.getPassword())) throw new IllegalStateException("WRONG PASSWORD!");
        if(!user.isActive()) throw new IllegalStateException("ACCOUNT IS NOT ACTIVE!");

        return user.getId();
        // JWT will be returned later.
    }

    public UserInfo getUserInfo(Long id) {
        Users user = usersDao.findUserById(id);

        if (user == null) throw new IllegalStateException("USER NOT FOUND!");

        return new UserInfo(user.getName(), user.getMail(), user.getGender());
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
}
