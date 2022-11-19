package com.grove.tfb_backend.matches;

//import com.grove.tfb_backend.footballAPI;
import com.grove.tfb_backend.matches.MatchDto.*;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/*
import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
*/

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("api/v1/match")
public class MatchesController {

    @Autowired
    private final MatchesService matchesService;

    public MatchesController(MatchesService matchesService) {
        this.matchesService = matchesService;
    }

    @GetMapping("{id}")
    public GeneralHttpResponse<MatchInfo> matchInfo(@PathVariable Long id){
        GeneralHttpResponse<MatchInfo> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getMatchInfo(id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("all")
    public GeneralHttpResponse<List<Matches>> allMatchesInfo(){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllMatches());
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("home/{team_id}")
    public GeneralHttpResponse<List<Matches>> allHomeMatchesInfo(@PathVariable Long team_id){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllHomeTeamMatches(team_id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("away/{team_id}")
    public GeneralHttpResponse<List<Matches>> allAwayMatchesInfo(@PathVariable Long team_id){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllAwayTeamMatches(team_id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @GetMapping("all/{team_id}")
    public GeneralHttpResponse<List<Matches>> allTeamMatchesInfo(@PathVariable Long team_id){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllTeamMatches(team_id));
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    /*
    //@PostConstruct
    public void getDataByAPI(){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/fixtures?league=203&season=2022"))
                    .header("X-RapidAPI-Key", footballAPI.apiKey)
                    .header("X-RapidAPI-Host", footballAPI.apiHost)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> _response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONObject(_response.body()).getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tmp = jsonArray.getJSONObject(i);

                JSONObject fixture = tmp.getJSONObject("fixture");
                String dateTmp = fixture.getString("date").replace('T', ' ');
                String date = dateTmp.substring(0, dateTmp.indexOf('+'));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  //2022-08-07T18:45:00
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
                JSONObject venue = fixture.getJSONObject("venue");
                String city = venue.getString("city");
                String stadiumName = venue.getString("name");
                JSONObject STATUS = fixture.getJSONObject("status");
                String status = STATUS.getString("long");
                boolean isFinished = status.equals("Match Finished");
                String refereeTmp = fixture.getString("referee");
                String referee = (status.equals("Time to be defined")) ? "none yet" : refereeTmp.substring(0, refereeTmp.indexOf(','));


                JSONObject teams = tmp.getJSONObject("teams");
                JSONObject home = teams.getJSONObject("home");
                String homeTeam = home.getString("name");
                JSONObject away = teams.getJSONObject("away");
                String awayTeam = away.getString("name");


                int goalHome = -1;
                int goalAway = -1;
                String result = "none";

                if(isFinished) {
                    JSONObject goals = tmp.getJSONObject("goals");
                    goalHome = goals.getInt("home");
                    goalAway = goals.getInt("away");
                    if(goalHome == goalAway)
                        result = "draw";
                    else if (goalHome > goalAway)
                        result = "home winner";
                    else
                        result = "away winner";
                }

                MatchInfo M = new MatchInfo(homeTeam, awayTeam, referee, city, stadiumName, dateTime, status, isFinished, goalHome, goalAway,
                        result, matchesService.getTeam(homeTeam), matchesService.getTeam(awayTeam));
                matchesService.addMatch(M);
            }
        }
        catch (Exception e){}
    }*/
}
