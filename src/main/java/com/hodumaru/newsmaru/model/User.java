package com.hodumaru.newsmaru.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class User extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false)
    private int gender;

    @Column(nullable = false)
    private Date birthday;

    public User(String username, String email, String password, int gender, Date birthday ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;

    }
}
