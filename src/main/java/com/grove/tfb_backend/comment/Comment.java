package com.grove.tfb_backend.comment;

import com.grove.tfb_backend.matches.Matches;
import com.grove.tfb_backend.user.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users user;

    private LocalDateTime dop; //date of post

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private Matches match;

}