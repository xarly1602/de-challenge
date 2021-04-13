package com.walmart.org.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Console implements Serializable {
    private Long id;
    private String name;
    private Company company;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return Objects.equals(name, console.name) && Objects.equals(company, console.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, company);
    }
}
