package br.com.book.catalog.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "autores")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer birthYear;
    private Integer deathYear;

    public AuthorEntity() {}

    public AuthorEntity(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getBirthYear() { return birthYear; }
    public Integer getDeathYear() { return deathYear; }

    public void setName(String name) { this.name = name; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorEntity)) return false;
        AuthorEntity that = (AuthorEntity) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}