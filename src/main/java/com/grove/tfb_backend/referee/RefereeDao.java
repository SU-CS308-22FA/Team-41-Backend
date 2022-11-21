package com.grove.tfb_backend.referee;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefereeDao extends JpaRepository<Referee,Long> {

    Referee findRefereeById(Long id);
    Referee findRefereeByName(String name);
}
