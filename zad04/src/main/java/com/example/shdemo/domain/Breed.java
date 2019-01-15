package com.example.shdemo.domain;

import javax.persistence.*;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "breed.getAll",query = "Select d from Breed d"),
                @NamedQuery(name = "breed.getByName", query = "Select d from Breed d where d.name = :name")
        }
)
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;

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
}
