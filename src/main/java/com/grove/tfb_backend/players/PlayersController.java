package com.grove.tfb_backend.players;

import com.grove.tfb_backend.players.PlayerDto.*;
//import com.grove.tfb_backend.footballAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    public GeneralHttpResponse<PlayerInfo> playerInfo(@PathVariable Long id){
        GeneralHttpResponse<PlayerInfo> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(playersService.getPlayerInfo(id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }
    /*
    //@PostConstruct
    public void getDataByAPI(){
        String[] teamCodes = {"564", "607", "611", "645", "996", "998", "1001", "1002", "1004", "1005",
                              "1010", "3563", "3573", "3574", "3575", "3577", "3578", "3589"};

        for(String s: teamCodes) {
            try {
                HttpRequest request = footballAPI.getPlayersAPI(s);

                HttpResponse<String> _response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                JSONArray jsonArray = new JSONObject(_response.body()).getJSONArray("response");
                JSONObject data = jsonArray.getJSONObject(0);

                JSONObject team = data.getJSONObject("team");
                String teamName = team.getString("name");

                JSONArray players = data.getJSONArray("players");
                for (int i = 0; i < players.length(); i++) {
                    JSONObject player = players.getJSONObject(i);
                    String name = player.getString("name");
                    int age = player.getInt("age");
                    int number = -1;
                    String position = "unknown";
                    String pictureURL = "";
                    try {
                        number = player.getInt("number");
                    }
                    catch (Exception e) {}
                    try {
                        position = player.getString("position");
                    }
                    catch (Exception e) {}
                    try {
                        pictureURL = player.getString("photo");
                    }
                    catch (Exception e) {}

                    PlayerInfo P = new PlayerInfo(name, teamName, age, position, number, pictureURL);
                    playersService.addPlayer(P);
                }
            }
            catch (Exception e) {}
        }
    }*/
}
