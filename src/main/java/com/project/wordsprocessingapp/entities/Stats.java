package com.project.wordsprocessingapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "stats")
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String word;

    private Integer entry;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "request_id")
    private Request request;

    public Stats(String word, Integer entry) {
        this.word = word;
        this.entry = entry;
    }

    public Stats(String word, Integer entry, Request request) {
        this.word = word;
        this.entry = entry;
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stats stats = (Stats) o;

        return id != null ? id.equals(stats.id) : stats.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "word='" + word + '\'' +
                ", entry=" + entry +
                '}';
    }
}
