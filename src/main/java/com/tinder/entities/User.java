package com.tinder.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String photo_url;
    private String profession;
    private LocalDate last_login;

    public User(String username, String password, String profession, String photoUrl, LocalDate last_login) {
        this.username = username;
        this.password = password;
        this.profession = profession;
        this.photo_url = photoUrl;
        this.last_login = last_login;
    }
}
