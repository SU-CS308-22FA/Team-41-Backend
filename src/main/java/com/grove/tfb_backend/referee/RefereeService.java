package com.grove.tfb_backend.referee;


import com.grove.tfb_backend.referee.refereeDto.VoteRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;


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
}
