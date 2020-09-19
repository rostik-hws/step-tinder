package com.tinder.services;

import com.tinder.dao.DAOLike;
import com.tinder.dao.DAOUser;
import com.tinder.entities.Like;
import com.tinder.entities.User;

import java.util.List;
import java.util.Optional;

public class LikePageService {
    private final DAOUser DAO_USER;
    private final DAOLike DAO_LIKE;

    public LikePageService(DAOUser DAO_USER, DAOLike DAO_LIKE) {
        this.DAO_USER = DAO_USER;
        this.DAO_LIKE = DAO_LIKE;
    }


    public List<User> getUsersExceptThis(int id) {
        String SQL = String.format("SELECT * FROM users WHERE id != %d", id);
        return DAO_USER.getBy(SQL);
    }

    public void addReaction(int logged_user_id, int liked_user_id, boolean reaction) {
        String SQL = String.format("SELECT * FROM likes WHERE logged_user_id = %d AND liked_user_id = %d", logged_user_id, liked_user_id);
        Optional<Like> isReactedBefore = DAO_LIKE.getBy(SQL).stream().findFirst();

        if (isReactedBefore.isPresent()) {
            Like like = isReactedBefore.get();
            like.setReaction(reaction);
            DAO_LIKE.update(like);
        } else DAO_LIKE.insert(new Like(logged_user_id, liked_user_id, reaction));
    }
}
