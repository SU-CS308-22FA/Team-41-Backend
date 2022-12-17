package com.grove.tfb_backend.FootballAPI;

import java.time.LocalDate;

public class footballAPI {
    /*
     * leagueId      league
     * 1             world cup
     * 2             champions league
     * 21            confederation cup
     * 203           superlig
     * 551           super cup
     */

    public static String apiHost = "api-football-v1.p.rapidapi.com";

    public static String apiKey = "";

    public static String[] superligTeamCodes = {"549", "564", "607", "611", "645", "996", "998", "1001", "1002", "1004",
            "1005", "1010", "3563", "3573", "3574", "3575", "3577", "3578", "3589"};

    public static String getTeamUrl(String league, String season) {
        return "https://" + apiHost + "/v3/teams?league=" + league + "&season=" + season;
    }

    public static String getPlayerUrl(String teamCode) {
        return "https://" + apiHost + "/v3/squads?team=" + teamCode;
    }

    public static String getFixtureUrl(LocalDate date, String league, String season) {
        return "https://" + apiHost + "/v3/fixtures?date=" + date.toString() + "&league=" + league + "&season=" + season;
    }

    public static String getFixtureUrl(String league, String season) {
        return "https://" + apiHost + "/v3/fixtures?league=" + league + "&season=" + season;
    }
}
