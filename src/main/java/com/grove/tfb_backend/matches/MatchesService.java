package com.grove.tfb_backend.matches;

import com.grove.tfb_backend.matches.MatchDto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchesService {

    private final MatchesDao matchesDao;

    public MatchesService(MatchesDao matchesDao) {
        this.matchesDao = matchesDao;
    }

    public MatchInfo getMatchInfo(Long id) {
        Matches match = matchesDao.findMatchById(id);

        if (match == null) throw new IllegalStateException("MATCH NOT FOUND!");

        return new MatchInfo(match.getHomeTeam(), match.getAwayTeam(),
                             match.getReferee(), match.getCity(),
                             match.getStadiumName(), match.getDateAndTime(),
                             match.getStatus(), match.isFinished(),
                             match.getGoalHome(), match.getGoalAway());
    }

    @Transactional
    public void addMatch(MatchInfo matchInfoDto) {

        Matches newMatch = new Matches(matchInfoDto);
        Matches matchDb = matchesDao.save(newMatch);

    }
}
