package com.grove.tfb_backend.referee;


import com.grove.tfb_backend.matches.Matches;
import com.grove.tfb_backend.matches.MatchesDao;
import com.grove.tfb_backend.referee.refereeDto.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Ref;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RefereeService {

    private final RefereeDao refereeDao;

    private final MatchesDao matchesDao;

    public RefereeService(RefereeDao refereeDao, MatchesDao matchesDao) {
        this.refereeDao = refereeDao;
        this.matchesDao = matchesDao;
    }



    @Transactional
    public void voteReferee(VoteRequest vote) {
        Referee referee = refereeDao.findRefereeByName(vote.getName());

        if (referee == null) throw new IllegalStateException("REFEREE NOT FOUND!");

        Long previousTotalVote = referee.getTotalVote();
        referee.setRating((referee.getRating()* previousTotalVote + vote.getRate()) / (previousTotalVote+1));
        referee.setTotalVote(previousTotalVote+1);
    }

    public List<AllReferees> getAllReferees() {
        List<Referee> referees =  refereeDao.findAll();

        List<AllReferees> toBeReturned = new ArrayList<>();
        for(Referee r: referees){
            AllReferees newReferee = new AllReferees(r.getId(),r.getName(),r.getRating(),r.getTotalVote(),r.getTotalRefereeVote(),r.getRefereeRating());
            toBeReturned.add(newReferee);
        }
        return toBeReturned;
    }

    public OneReferee getReferee(Long id) {
        Referee referee = refereeDao.findRefereeById(id);

        if (referee == null) throw new IllegalStateException("REFEREE NOT FOUND!");

        return new OneReferee(referee.getId(), referee.getName(),
                referee.getTotalVote(), referee.getRating(), referee.getMatches());
    }

    @Transactional
    public void handleRefereeVote(RefereeVoteRequest voteRequest) {
        Matches match = matchesDao.findMatchById(voteRequest.getMatchId());

        if (match == null) throw new IllegalStateException("MATCH NOT FOUND!");

        Referee referee = match.getRefereeId();

        if (referee == null) throw new IllegalStateException("REFEREE NOT FOUND!");

        Long previousTotalVote = referee.getTotalRefereeVote();
        referee.setRefereeRating((referee.getRefereeRating()* previousTotalVote + voteRequest.getTotalPoints()) / (previousTotalVote+1));
        referee.setTotalRefereeVote(previousTotalVote+1);
    }

    public List<Referee> handleSuggestion(Integer matchImportance, Long matchId) {
        Matches match = matchesDao.findMatchById(matchId);

        List<Referee> referees = refereeDao.findAll();
        List<RefPoint> refereePoints = new ArrayList<>();
        for (Referee ref: referees){
            RefPoint refc = new RefPoint(ref,ref.getRefereeRating()/40 * 75 + ref.getRating()*5);

            List<Matches> refMatches = ref.getMatches();
            if (refMatches.get(refMatches.size()-1).getHomeTeamName().equals(match.getHomeTeamName()) || refMatches.get(refMatches.size()-1).getHomeTeamName().equals(match.getAwayTeamName())){
                refc.setPoints(refc.getPoints()-20);
            }
            if (LocalDateTime.now().getDayOfYear() - refMatches.get(refMatches.size()-1).getDateAndTime().getDayOfYear() < 7) {
                refc.setPoints(refc.getPoints()-10);
            }
            refereePoints.add(refc);
        }
        refereePoints.sort(Comparator.comparing(RefPoint::getPoints).reversed());
        List<Referee> suggestions = new ArrayList<>();
        if (matchImportance.equals(0)){
            suggestions.add(refereePoints.get(0).getRef());
            suggestions.add(refereePoints.get(1).getRef());
            suggestions.add(refereePoints.get(2).getRef());
        }
        else {
            suggestions.add(refereePoints.get(matchImportance-1).getRef());
            suggestions.add(refereePoints.get(matchImportance).getRef());
            suggestions.add(refereePoints.get(matchImportance+1).getRef());
        }
        return suggestions;
    }

    @Transactional
    public void handleAssign(RefereeAssign refereeAssign) {
        Matches match = matchesDao.findMatchById(refereeAssign.getMatchId());

        if (match == null) throw new IllegalStateException("MATCH NOT FOUND!");
        if (match.getRefereeId() != null) throw new IllegalStateException("MATCH ALREADY HAS A REFEREE!");


        Referee ref = refereeDao.getReferenceById(refereeAssign.getRefereeId());
        List<Matches> refMatches = ref.getMatches();
        if (refMatches.get(refMatches.size()-1).getDateAndTime().isAfter(LocalDateTime.now())) throw new IllegalStateException("REFEREE ALREADY ASSIGNED TO A MATCH NEXT WEEK!");
        match.setRefereeId(ref);
        match.setReferee(ref.getName());

    }
}
