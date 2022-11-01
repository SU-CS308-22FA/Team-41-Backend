package com.grove.tfb_backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersDao extends JpaRepository<Users,Long> {

    boolean existsByMail(String mail); // sql query yerine geçiyor

    Users findUserByMail(String mail);

    Users findUserById(Long id);
}
