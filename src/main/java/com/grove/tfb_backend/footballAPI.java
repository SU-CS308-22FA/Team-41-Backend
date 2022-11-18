package com.grove.tfb_backend;

import java.net.URI;
import java.net.http.HttpRequest;

public class footballAPI {

    public static HttpRequest getPlayersAPI(String teamCode) {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/players/squads?team=" + teamCode))
                .header("X-RapidAPI-Key", "4867d8cc77mshd059542f24aca95p1e086ejsn1e1504d6b37a")
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }

    public static HttpRequest getTeamsAPI() {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/teams?league=203&season=2022"))
                .header("X-RapidAPI-Key", "4867d8cc77mshd059542f24aca95p1e086ejsn1e1504d6b37a")
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }

    public static HttpRequest getMatchesAPI() {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/fixtures?league=203&season=2022"))
                .header("X-RapidAPI-Key", "4867d8cc77mshd059542f24aca95p1e086ejsn1e1504d6b37a")
                .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }
}
