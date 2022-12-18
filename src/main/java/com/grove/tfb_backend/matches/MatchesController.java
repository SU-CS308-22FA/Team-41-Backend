package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.FootballAPI.fixtures.ResponseFixtures;
import com.grove.tfb_backend.FootballAPI.fixtures.ReturnedFixtures;
import com.grove.tfb_backend.FootballAPI.footballAPI;
import com.grove.tfb_backend.matches.MatchDto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @PostMapping("/add_match")
    public GeneralHttpResponse<String> addMatchByAdmin(@RequestBody AddMatch newMatch) {
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            matchesService.addMatchByAdmin(newMatch);
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }

    @GetMapping("{id}")
    public GeneralHttpResponse<SingleMatchResponse> matchInfo(@PathVariable Long id){
        GeneralHttpResponse<SingleMatchResponse> response = new GeneralHttpResponse<>("200",null);
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

    @GetMapping("today")
    public GeneralHttpResponse<List<Matches>> allTeamMatchesInfo(){
        GeneralHttpResponse<List<Matches>> response = new GeneralHttpResponse<>("200",null);
        try{
            response.setReturnObject(matchesService.getAllTodaysMatches());
        }
        catch (Exception e){
            response.setStatus("400: "+e.getMessage());
        }
        return response;
    }

    @Scheduled(cron = "0 0 */1 ? * *")
    public void automation() {
        List<Matches> matchList = matchesService.getAllTodaysMatches();
        if(matchList.size() > 0) {
            System.out.println("Found " + matchList.size() + " matches!");
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = footballAPI.getFixtureUrl(LocalDate.now(),"203", "2022");
                HttpHeaders header = new HttpHeaders();
                header.set("X-RapidAPI-Key", footballAPI.apiKey);
                header.set("X-RapidAPI-Host", footballAPI.apiHost);
                HttpEntity<ReturnedFixtures> request = new HttpEntity<>(header);
                ResponseEntity<ReturnedFixtures> response = restTemplate.exchange(url, HttpMethod.GET, request, ReturnedFixtures.class);

                for(ResponseFixtures r: response.getBody().getResponse()) {
                    Instant instant = r.getFixture().getDate().toInstant();
                    ZoneId zone = ZoneId.of(r.getFixture().getTimezone());
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);

                    int homeGoal = (r.getGoals().getHome() == null)? -1: r.getGoals().getHome();
                    int awayGoal = (r.getGoals().getAway() == null)? -1: r.getGoals().getAway();
                    String result;
                    boolean finished = homeGoal != -1;
                    if(finished) {
                        if (homeGoal == awayGoal)
                            result = "draw";
                        else if (homeGoal > awayGoal)
                            result = "home winner";
                        else
                            result = "away winner";
                    }
                    else
                        result = "none";

                    MatchInfo m = new MatchInfo(r.getTeams().getHome().getName(), r.getTeams().getAway().getName(), r.getFixture().getReferee(),
                            r.getFixture().getVenue().getCity(), r.getFixture().getVenue().getName(), localDateTime,
                            r.getFixture().getStatus().get_long(), finished, homeGoal, awayGoal, result,
                            matchesService.getTeam(r.getTeams().getHome().getName()), matchesService.getTeam(r.getTeams().getAway().getName()),
                            matchesService.getReferee(r.getFixture().getReferee()));

                    Long id = matchesService.getId(m);  //find real match id
                    matchesService.updateMatch(id, m);  //update
                }
            }
            catch (Exception e) {
                System.out.println("Error! couldn't update at " + LocalDateTime.now().toString().replace('T', ' '));
                System.out.println(e.getMessage());
            }
        }
    }


    //@PostConstruct
    public void getDataByAPI(){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = footballAPI.getFixtureUrl("203", "2022");
            HttpHeaders header = new HttpHeaders();
            header.set("X-RapidAPI-Key", footballAPI.apiKey);
            header.set("X-RapidAPI-Host", footballAPI.apiHost);
            HttpEntity<ReturnedFixtures> request = new HttpEntity<>(header);
            ResponseEntity<ReturnedFixtures> response = restTemplate.exchange(url, HttpMethod.GET, request, ReturnedFixtures.class);

            for(ResponseFixtures r: response.getBody().getResponse()) {
                Instant instant = r.getFixture().getDate().toInstant();
                ZoneId zone = ZoneId.of(r.getFixture().getTimezone());
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
                int homeGoal = (r.getGoals().getHome() == null)? -1: r.getGoals().getHome();
                int awayGoal = (r.getGoals().getAway() == null)? -1: r.getGoals().getAway();
                String result;
                boolean finished = homeGoal != -1;
                if(finished) {
                    if (homeGoal == awayGoal)
                        result = "draw";
                    else if (homeGoal > awayGoal)
                        result = "home winner";
                    else
                        result = "away winner";
                }
                else
                    result = "none";
                MatchInfo m = new MatchInfo(r.getTeams().getHome().getName(), r.getTeams().getAway().getName(), r.getFixture().getReferee(),
                        r.getFixture().getVenue().getCity(), r.getFixture().getVenue().getName(), localDateTime,
                        r.getFixture().getStatus().get_long(), finished, homeGoal, awayGoal, result,
                        matchesService.getTeam(r.getTeams().getHome().getName()), matchesService.getTeam(r.getTeams().getAway().getName()),
                        matchesService.getReferee(r.getFixture().getReferee()));

                matchesService.addMatch(m);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
