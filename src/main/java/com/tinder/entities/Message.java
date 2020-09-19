package com.tinder.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private int id;
    private int from_id;
    private int to_id;
    private String content;
    private LocalDate date;

    public Message(int from_id, int to_id, String content, LocalDate date) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.content = content;
        this.date = date;
    }
}
