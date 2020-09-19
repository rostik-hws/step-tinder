package com.tinder.services;

import lombok.extern.log4j.Log4j2;
import com.tinder.dao.DAOMessage;
import com.tinder.dao.DAOUser;
import com.tinder.entities.Message;
import com.tinder.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Log4j2
public class MessageService {
    private final DAOUser DAO_USER;
    private final DAOMessage DAO_MESSAGE;

    public MessageService(DAOUser DAO_USER, DAOMessage DAO_MESSAGE) {
        this.DAO_USER = DAO_USER;
        this.DAO_MESSAGE = DAO_MESSAGE;
    }

    public List<Message> getMessages(int logged_user_id, int liked_user_id) {
        String SQL = String.format("SELECT * FROM messages WHERE from_id = %d AND to_id = %d OR from_id = %d AND to_id = %d ORDER BY date",
                logged_user_id, liked_user_id, liked_user_id, logged_user_id);
        return DAO_MESSAGE.getBy(SQL);
    }

    public User getUserInfo(int id) {
        Optional<User> user = DAO_USER.get(id);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            log.error("Something went wrong while getting user from database!!!");
            return null;
        }
    }

    public void addMessage(int logged_user_id, int liked_user_id, String content, LocalDate date) {
        DAO_MESSAGE.insert(new Message(logged_user_id, liked_user_id, content, date));
    }
}
