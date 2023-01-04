package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.FootballAPI.fixtures.ResponseFixtures;
import com.grove.tfb_backend.FootballAPI.fixtures.ReturnedFixtures;
import com.grove.tfb_backend.FootballAPI.footballAPI;
import com.grove.tfb_backend.matches.MatchDto.*;
import com.grove.tfb_backend.user.userDto.UserInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
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
            response.setReturnObject("Match successfully added!");
        }
        catch (Exception e){
            response.setStatus("400");
            response.setReturnObject(e.getMessage());
        }
        return response;
    }

    @PutMapping("{id}")
    public GeneralHttpResponse<String> updateResultByAdmin(@PathVariable Long id, @RequestBody UpdateMatch updatedMatch){
        GeneralHttpResponse<String> response = new GeneralHttpResponse<>("200",null);
        try{
            matchesService.updateResultByAdmin(id, updatedMatch);
            response.setReturnObject("Match successfully updated!");
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
        System.out.println("Running matchDB automation at " + LocalDateTime.now().toString().replace('T', ' '));
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = footballAPI.getFixtureUrl(LocalDate.now(),"203", "2022");
            HttpHeaders header = new HttpHeaders();
            header.set("X-RapidAPI-Key", footballAPI.apiKey);
            header.set("X-RapidAPI-Host", footballAPI.apiHost);
            HttpEntity<ReturnedFixtures> request = new HttpEntity<>(header);
            ResponseEntity<ReturnedFixtures> response = restTemplate.exchange(url, HttpMethod.GET, request, ReturnedFixtures.class);

            if(response.getBody().getResults() != 0) {
                System.out.println("Starting to update!");
                for(ResponseFixtures r: response.getBody().getResponse()) {
                    Instant instant = r.getFixture().getDate().toInstant();
                    ZoneId zone = ZoneId.of(r.getFixture().getTimezone());
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone).plusHours(3);

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

                    MatchAutoUpdate matchUpdate = new MatchAutoUpdate(
                            r.getFixture().getVenue().getCity(), r.getFixture().getVenue().getName(), homeGoal, awayGoal,
                            localDateTime, result, r.getFixture().getStatus().getLong_(), finished, r.getFixture().getId()
                    );

                    try {
                        matchesService.updateMatchAuto(matchUpdate);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("Successfully updated at " + LocalDateTime.now().toString().replace('T', ' '));
            }
            else {
                System.out.println("No matches to update at " + LocalDateTime.now().toString().replace('T', ' '));
            }
        }
        catch (Exception e) {
            System.out.println("Error! couldn't update at " + LocalDateTime.now().toString().replace('T', ' '));
            System.out.println(e.getMessage());
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
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone).plusHours(3);
                int homeGoal = (r.getGoals().getHome() == null)? -1: r.getGoals().getHome();
                int awayGoal = (r.getGoals().getAway() == null)? -1: r.getGoals().getAway();
                String result;
                boolean finished = homeGoal != -1;
                String ref = getRef(r.getFixture().getReferee());
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

                MatchInfo m = new MatchInfo(r.getTeams().getHome().getName(), r.getTeams().getAway().getName(), ref,
                        r.getFixture().getVenue().getCity(), r.getFixture().getVenue().getName(), localDateTime,
                        r.getFixture().getStatus().getLong_(), finished, homeGoal, awayGoal, result,
                        matchesService.getTeam(r.getTeams().getHome().getName()), matchesService.getTeam(r.getTeams().getAway().getName()),
                        matchesService.getReferee(ref), r.getFixture().getId());

                matchesService.addMatch(m);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //to be used when getting data from api for first time to fill table
    public String getRef(String in) {
        if(in == null)
            return "";
        if(in.equals("A. Bitigen"))
            return "Abdulkadir Bitigen";
        if(in.equals("A. Karaoğlan"))
            return "Atilla Karaoglan";
        if(in.equals("A. Kardeşler"))
            return "Arda Kardesler";
        if(in.equals("A. Palabıyık"))
            return "Ali Palabiyik";
        if(in.equals("A. Şansalan"))
            return "Ali Sansalan";
        if(in.equals("B. Şeker"))
            return "Burak Seker";
        if(in.equals("B. Şimşek"))
            return "Bahattin Simsek";
        if(in.equals("E. Özdamar"))
            return "Erkan Ozdamar";
        if(in.equals("H. Göçek"))
            return "Huseyin Gocek";
        if(in.equals("H. Meler"))
            return "Halil Umut Meler";
        if(in.equals("K. Sağlam"))
            return "Kadir Saglam";
        if(in.equals("M. Filiz"))
            return "Mustafa Kursad Filiz";
        if(in.equals("M. Güzenge"))
            return "Mert Guzenge";
        if(in.equals("M. Kalkavan"))
            return "Mete Kalkavan";
        if(in.equals("S. Arslanboğa"))
            return "Suat Arslanboga";
        if(in.equals("S. Saka"))
            return "Sarperbaris Saka";
        if(in.equals("T. Numanoğlu"))
            return "Tugay Kaan Numanoglu";
        if(in.equals("V. Bayarslan"))
            return "Volkan Bayarslan";
        if(in.equals("Y. Kol"))
            return "Yasin Kol";
        if(in.equals("Y. Uğurlu"))
            return "Yasar Kemal Ugurlu";
        if(in.equals("Z. Küçük"))
            return "Zorbay Kucuk";
        if(in.equals("Ç. Altay"))
            return "Cagdas Altay";
        if(in.equals("Ü. Öztürk"))
            return "Umit Ozturk";
        return "";
    }
}
