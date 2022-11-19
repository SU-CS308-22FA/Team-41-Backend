package com.grove.tfb_backend.teams;

import com.grove.tfb_backend.footballAPI;
import com.grove.tfb_backend.teams.TeamDto.*;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/team")
public class TeamsController {

    @Autowired
    private final TeamsService teamsService;


    public TeamsController(TeamsService teamsService) {
        this.teamsService = teamsService;
    }

    @GetMapping("{id}")
    public GeneralHttpResponse<TeamInfo> teamInfo(@PathVariable Long id){
        GeneralHttpResponse<TeamInfo> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(teamsService.getTeamInfo(id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("all")
    public GeneralHttpResponse<List<Teams>> allTeamInfos(){
        GeneralHttpResponse<List<Teams>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(teamsService.getAllTeams());
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    //@PostConstruct
    public void getDataByAPI(){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/teams?league=203&season=2022"))
                    .header("X-RapidAPI-Key", footballAPI.apiKey)
                    .header("X-RapidAPI-Host", footballAPI.apiHost)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> _response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONObject(_response.body()).getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tmp = jsonArray.getJSONObject(i);
                JSONObject teamPart = tmp.getJSONObject("team");
                String teamName = teamPart.getString("name");
                String logo = teamPart.getString("logo");
                JSONObject venuePart = tmp.getJSONObject("venue");
                String stadiumName = venuePart.getString("name");
                String city = venuePart.getString("city");
                TeamInfo T = new TeamInfo(teamName, city, stadiumName, logo);
                teamsService.addTeam(T);
            }
        }
        catch (Exception e){}
    }
}
