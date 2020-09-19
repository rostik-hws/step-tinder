package com.tinder.dao;

import lombok.SneakyThrows;
import com.tinder.entities.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOUser implements DAO<User> {
    private final String SQL_getAll = "SELECT * FROM users";
    private final String SQL_get = "SELECT * FROM users WHERE id = ?";
    private final String SQL_insert = "INSERT INTO users (username, password, photo_url, profession, last_login) VALUES (?,?,?,?,?)";
    private final String SQL_update = "UPDATE users SET username = ?, password = ?, photo_url = ?, profession = ?, last_login = ? WHERE id = ?";
    private final Connection CONN;

    public DAOUser(Connection connection) {
        this.CONN = connection;
    }

    @SneakyThrows
    @Override
    public List<User> getAll() {
        PreparedStatement stmt = CONN.prepareStatement(SQL_getAll);
        ResultSet resultSet = stmt.executeQuery();
        List<User> data = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String photo_url = resultSet.getString("photo_url");
            String profession = resultSet.getString("profession");
            LocalDate last_login = LocalDate.parse(resultSet.getString("last_login"));
            User user = new User(id, username, password, photo_url, profession, last_login);
            data.add(user);
        }
        return data;
    }

    @SneakyThrows
    @Override
    public List<User> getBy(String SQL) {
        PreparedStatement stmt = CONN.prepareStatement(SQL);
        ResultSet resultSet = stmt.executeQuery();
        List<User> data = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String photo_url = resultSet.getString("photo_url");
            String profession = resultSet.getString("profession");
            LocalDate last_login = LocalDate.parse(resultSet.getString("last_login"));
            data.add(new User(id, username, password, photo_url, profession, last_login));
        }
        return data;
    }

    @SneakyThrows
    @Override
    public Optional<User> get(int id) {
        PreparedStatement stmt = CONN.prepareStatement(SQL_get);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();
        return !resultSet.next() ? Optional.empty() : Optional.of(
                new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("photo_url"),
                        resultSet.getString("profession"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("last_login")))
                )
        );
    }

    @SneakyThrows
    @Override
    public void insert(User user) {
        PreparedStatement stmt = CONN.prepareStatement(SQL_insert);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getPhoto_url());
        stmt.setString(4, user.getProfession());
        stmt.setDate(5, Date.valueOf(user.getLast_login()));
        stmt.executeUpdate();
    }

    @SneakyThrows
    @Override
    public void update(User user) {
        PreparedStatement stmt = CONN.prepareStatement(SQL_update);
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getPhoto_url());
        stmt.setString(4, user.getProfession());
        stmt.setDate(5, Date.valueOf(user.getLast_login()));
        stmt.setInt(6, user.getId());
        stmt.executeUpdate();
    }
}
