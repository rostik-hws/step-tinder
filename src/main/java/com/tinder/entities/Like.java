package com.tinder.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    private int id;
    private int logged_user_id;
    private int liked_user_id;
    private boolean reaction;

    public Like(int logged_user_id, int liked_user_id, boolean reaction) {
        this.logged_user_id = logged_user_id;
        this.liked_user_id = liked_user_id;
        this.reaction = reaction;
    }
}
