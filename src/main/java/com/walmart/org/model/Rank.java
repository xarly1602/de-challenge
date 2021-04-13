package com.walmart.org.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Rank implements Serializable, Comparable<Rank>{
    private Long id;
    private Integer metascore;
    private String name;
    private Console console;
    private String userscore;
    private String date;

    @Override
    public int compareTo(Rank rank) {
        return this.getMetascore().compareTo(rank.getMetascore());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rank rank = (Rank) o;
        return Objects.equals(metascore, rank.metascore) && Objects.equals(name, rank.name) && console.equals(rank.console) && Objects.equals(userscore, rank.userscore) && Objects.equals(date, rank.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metascore, name, console, userscore, date);
    }
}
