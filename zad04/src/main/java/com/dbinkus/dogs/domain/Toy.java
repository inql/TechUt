package com.dbinkus.dogs.domain;


import javax.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "toy.getAll",query = "Select o from Toy o"),
        @NamedQuery(name = "toy.getByName",query = "Select o from Toy o where :name=o.name")
})
public class Toy {

    private Long id;
    private String name;
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toy toy = (Toy) o;
        return id.equals(toy.id) &&
                name.equals(toy.name) &&
                description.equals(toy.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
