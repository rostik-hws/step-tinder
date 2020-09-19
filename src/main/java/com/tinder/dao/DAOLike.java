package com.tinder.dao;

import lombok.SneakyThrows;
import com.tinder.entities.Like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOLike implements DAO<Like> {
    private final String SQL_getAll = "SELECT * FROM likes";
    private final String SQL_getBy = "SELECT * FROM likes WHERE id = ?";
    private final String SQL_insert = "INSERT INTO likes (logged_user_id, liked_user_id, action) VALUES (?, ? , ?)";
    private final String SQL_update = "UPDATE likes SET logged_user_id = ?, liked_user_id = ?, action = ? WHERE id = ?";
    private final Connection CONN;

    public DAOLike(Connection connection) {
        this.CONN = connection;
    }

    @Override
    @SneakyThrows
    public List<Like> getAll() {
        PreparedStatement stmt = CONN.prepareStatement(SQL_getAll);
        ResultSet resultSet = stmt.executeQuery();
        List<Like> data = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int logged_user_id = resultSet.getInt("logged_user_id");
            int liked_user_id = resultSet.getInt("liked_user_id");
            boolean action = resultSet.getBoolean("action");
            data.add(new Like(id, logged_user_id, liked_user_id, action));
        }
        return data;
    }

    @Override
    @SneakyThrows
    public List<Like> getBy(String SQL) {
        PreparedStatement stmt = CONN.prepareStatement(SQL);
        ResultSet resultSet = stmt.executeQuery();
        List<Like> data = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int logged_user_id = resultSet.getInt("logged_user_id");
            int liked_user_id = resultSet.getInt("liked_user_id");
            boolean action = resultSet.getBoolean("action");
            data.add(new Like(id, logged_user_id, liked_user_id, action));
        }
        return data;
    }

    @SneakyThrows
    @Override
    public Optional<Like> get(int id) {
        PreparedStatement stmt = CONN.prepareStatement(SQL_getBy);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();
        return !resultSet.next() ? Optional.empty() : Optional.of(
                new Like(
                        resultSet.getInt("id"),
                        resultSet.getInt("logged_user_id"),
                        resultSet.getInt("liked_user_id"),
                        resultSet.getBoolean("action")
                )
        );
    }

    @SneakyThrows
    @Override
    public void insert(Like like) {
        PreparedStatement stmt = CONN.prepareStatement(SQL_insert);
        stmt.setInt(1, like.getLogged_user_id());
        stmt.setInt(2, like.getLiked_user_id());
        stmt.setBoolean(3, like.isReaction());
        stmt.executeUpdate();
    }

    @SneakyThrows
    @Override
    public void update(Like like) {
        PreparedStatement stmt = CONN.prepareStatement(SQL_update);
        stmt.setInt(1, like.getLogged_user_id());
        stmt.setInt(2, like.getLiked_user_id());
        stmt.setBoolean(3, like.isReaction());
        stmt.setBoolean(3, like.isReaction());
        stmt.setInt(4, like.getId());
        stmt.executeUpdate();
    }
}
