package com.tinder.dao;

import lombok.SneakyThrows;
import com.tinder.entities.Message;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DAOMessage implements DAO<Message> {
    private final String SQL_getAll = "SELECT * FROM messages";
    private final String SQL_getById = "SELECT * FROM messages WHERE id = ?";
    private final String SQL_insert = "INSERT INTO messages (from_id, to_id, content, date) VALUES (?, ?, ?, ?)";
    private final String SQL_update = "UPDATE messages SET from_id = ?, to_id = ?, content = ?, date = ? WHERE id = ?";
    private final Connection CONN;

    public DAOMessage(Connection connection) {
        this.CONN = connection;
    }

    @SneakyThrows
    @Override
    public List<Message> getAll() {
        PreparedStatement stmt = CONN.prepareStatement(SQL_getAll);
        ResultSet resultSet = stmt.executeQuery();
        List<Message> data = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int from_id = resultSet.getInt("from_id");
            int to_id = resultSet.getInt("to_id");
            String content = resultSet.getString("content");
            Date date = resultSet.getDate("date");
            data.add(new Message(id, from_id, to_id, content, LocalDate.parse(String.valueOf(date))));
        }
        return data;
    }

    @Override
    @SneakyThrows
    public List<Message> getBy(String SQL) {
        PreparedStatement stmt = CONN.prepareStatement(SQL);
        ResultSet resultSet = stmt.executeQuery();
        List<Message> data = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            int from_id = resultSet.getInt("from_id");
            int to_id = resultSet.getInt("to_id");
            String content = resultSet.getString("content");
            Date date = resultSet.getDate("date");
            data.add(new Message(id, from_id, to_id, content, LocalDate.parse(String.valueOf(date))));
        }
        return data;
    }

    @SneakyThrows
    @Override
    public Optional<Message> get(int id) {
        PreparedStatement stmt = CONN.prepareStatement(SQL_getById);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();
        return !resultSet.next() ? Optional.empty() : Optional.of(
                new Message(
                        resultSet.getInt("id"),
                        resultSet.getInt("from_id"),
                        resultSet.getInt("to_id"),
                        resultSet.getString("content"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date")))
                )
        );
    }

    @SneakyThrows
    @Override
    public void insert(Message message) {
        PreparedStatement stmt = CONN.prepareStatement(SQL_insert);
        stmt.setInt(1, message.getFrom_id());
        stmt.setInt(2, message.getTo_id());
        stmt.setString(3, message.getContent());
        stmt.setDate(4, Date.valueOf(message.getDate()));
        stmt.executeUpdate();
    }

    @SneakyThrows
    @Override
    public void update(Message message) {
        PreparedStatement stmt = CONN.prepareStatement(SQL_update);
        stmt.setInt(1, message.getFrom_id());
        stmt.setInt(2, message.getTo_id());
        stmt.setString(3, message.getContent());
        stmt.setDate(4, Date.valueOf(message.getDate()));
        stmt.executeUpdate();
    }
}
