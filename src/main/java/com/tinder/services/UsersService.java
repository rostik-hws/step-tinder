package com.tinder.services;

import com.tinder.dao.DAOLike;
import com.tinder.dao.DAOUser;
import com.tinder.entities.Like;
import com.tinder.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsersService {
    private final DAOUser DAO_USER;
    private final DAOLike DAO_LIKE;

    public UsersService(DAOUser DAO_USER, DAOLike DAO_LIKE) {
        this.DAO_USER = DAO_USER;
        this.DAO_LIKE = DAO_LIKE;
    }

    public List<User> getLikedUsers(int logged_user_id) {
        String SQL = String.format("SELECT * FROM likes WHERE logged_user_id = %d AND action = 'TRUE'", logged_user_id);

        return DAO_LIKE.getBy(SQL).stream()
                .map(Like::getLiked_user_id)
                .map(DAO_USER::get)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
