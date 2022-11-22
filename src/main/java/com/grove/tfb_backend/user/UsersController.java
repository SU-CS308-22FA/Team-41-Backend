package com.grove.tfb_backend.user;


import com.grove.tfb_backend.teams.TeamDto.TeamInfo;
import com.grove.tfb_backend.teams.Teams;
import com.grove.tfb_backend.user.confirmationToken.ConfirmationTokenService;
import com.grove.tfb_backend.user.userDto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/user")
public class UsersController {

    private final UsersService usersService;
    private final ConfirmationTokenService confirmationTokenService;


    public UsersController(UsersService usersService, ConfirmationTokenService confirmationTokenService) {
        this.usersService = usersService;
        this.confirmationTokenService = confirmationTokenService;
    }


    @PostMapping("/signup")
    public GeneralHttpResponse<String> signup(@RequestBody UserSignupDto signupDto){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try {
            usersService.signup(signupDto);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }

    @GetMapping("/activation")
    public String mailConfirmation(@RequestParam String token){
        try{
            confirmationTokenService.mailConfirmation(token);
        }
        catch (Exception e){
            return "Confirmation failed.";
        }
        return "You have successfully confirmed your account.";
    }


    @PostMapping("/login")
    public GeneralHttpResponse<LoginResponse> login(@RequestBody UserLoginRequest loginRequest){
        GeneralHttpResponse<LoginResponse> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(usersService.login(loginRequest));
        }
        catch (Exception e){
            response.setStatus("400: "+ e.getMessage());
        }
        return response;
    }

    @GetMapping("{id}")
    public GeneralHttpResponse<UserInfo> userInfo(@PathVariable Long id){
        GeneralHttpResponse<UserInfo> response = new GeneralHttpResponse<>("200",null);

        try{
            response.setReturnObject(usersService.getUserInfo(id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @DeleteMapping("{id}")
    public GeneralHttpResponse<String> deleteUser(@PathVariable Long id){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200", "USER DELETED SUCCESSFULLY!");
        try{
            usersService.deleteUser(id);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }

        return response;
    }

    @PutMapping("{id}")
    public GeneralHttpResponse<String> updateUser(@PathVariable Long id,@RequestBody UserInfo userInfo){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200","USER INFORMATION UPDATED SUCCESSFULLY!");

        try{
            usersService.updateUser(id, userInfo);
        }
        catch (Exception e){
            response.setReturnObject(e.getMessage());
            response.setStatus("400");

        }
        return response;
    }

    @PutMapping("changePassword")
    public GeneralHttpResponse<String> updatePassword(@RequestBody PasswordChangeRequest request){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200","PASSWORD CHANGED SUCCESSFULLY!");
        try{
            usersService.changePassword(request);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }


    @PostMapping("favTeams")
    public GeneralHttpResponse<String> addFavTeam(@RequestBody FavTeamAdd body){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            usersService.addFavTeam(body);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }

    @GetMapping("favTeams/{id}")
    public GeneralHttpResponse<List<Teams>> getFavTeams(@PathVariable Long id){
        GeneralHttpResponse<List<Teams>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(usersService.getFavTeams(id));
        }
        catch (Exception e){
            response.setStatus("400: "+ e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/favTeams")
    public GeneralHttpResponse<String> deleteFavTeam(@RequestBody FavTeamAdd body){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            usersService.deleteFavTeam(body);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }


}
