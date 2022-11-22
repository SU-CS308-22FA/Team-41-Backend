package com.grove.tfb_backend.referee;


import com.grove.tfb_backend.referee.refereeDto.AllReferees;
import com.grove.tfb_backend.referee.refereeDto.OneReferee;
import com.grove.tfb_backend.referee.refereeDto.VoteRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;


@Service
public class RefereeService {

    private final RefereeDao refereeDao;

    public RefereeService(RefereeDao refereeDao) {
        this.refereeDao = refereeDao;
    }



    @Transactional
    public void voteReferee(VoteRequest vote) {
        Referee referee = refereeDao.findRefereeById(vote.getId());

        if (referee == null) throw new IllegalStateException("REFEREE NOT FOUND!");

        Long previousTotalVote = referee.getTotalVote();
        referee.setRating((referee.getRating()* previousTotalVote + vote.getRate()) / (previousTotalVote+1));
        referee.setTotalVote(previousTotalVote+1);
    }

    public List<AllReferees> getAllReferees() {
        List<Referee> referees =  refereeDao.findAll();

        List<AllReferees> toBeReturned = new ArrayList<>();
        for(Referee r: referees){
            AllReferees newReferee = new AllReferees(r.getId(),r.getName(),r.getRating(),r.getTotalVote());
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
}
