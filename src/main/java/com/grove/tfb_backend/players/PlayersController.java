package com.grove.tfb_backend.players;

import com.grove.tfb_backend.players.PlayerDto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/player")
public class PlayersController {

    @Autowired
    private final PlayersService playersService;


    public PlayersController(PlayersService playersService) {
        this.playersService = playersService;
    }

    @GetMapping("{id}")
    public GeneralHttpResponse<Players> playerInfo(@PathVariable Long id){
        GeneralHttpResponse<Players> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(playersService.getPlayerInfo(id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("team/{team_id}")
    public GeneralHttpResponse<List<Players>> playersInfoByTeam(@PathVariable Long team_id){
        GeneralHttpResponse<List<Players>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(playersService.getPlayersOfTeam(team_id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("all")
    public GeneralHttpResponse<List<Players>> allPlayerInfos(){
        GeneralHttpResponse<List<Players>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(playersService.getAllPlayers());
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }
}
