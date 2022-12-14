package com.grove.tfb_backend.user;

import com.grove.tfb_backend.user.userDto.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsersDao extends JpaRepository<Users,Long> {

    boolean existsByMail(String mail); // sql query yerine geçiyor

    Users findUserByMail(String mail);

    Users findUserById(Long id);

    @Query("SELECT u.name, u.mail, u.gender, u.birthdate, u.fanTeam.name FROM Users u")
    List<UserInfo> findAllInfo();
}
