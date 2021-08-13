package com.example.wordsprocessingapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    private Integer entry;

    @ManyToOne
    private Request request;

    public Stats(String word, Integer entry, Request request) {
        this.word = word;
        this.entry = entry;
        this.request = request;
    }
}
