package com.grove.tfb_backend.players;

import com.grove.tfb_backend.players.PlayerDto.*;
//import com.grove.tfb_backend.footballAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
/*
import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
*/
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

    /*
    //@PostConstruct
    public void getDataByAPI(){
        String[] teamCodes = {"549", "564", "607", "611", "645", "996", "998", "1001", "1002", "1004",
                "1005", "1010", "3563", "3573", "3574", "3575", "3577", "3578", "3589"};

        for(String teamCode: teamCodes) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/players/squads?team=" + teamCode))
                        .header("X-RapidAPI-Key", footballAPI.apiKey)
                        .header("X-RapidAPI-Host", footballAPI.apiHost)
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();;

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

                    PlayerInfo P = new PlayerInfo(name, teamName, age, position, number, pictureURL, playersService.getTeam(teamName));
                    playersService.addPlayer(P);
                }
            }
            catch (Exception e) {}
        }
    }*/
}
