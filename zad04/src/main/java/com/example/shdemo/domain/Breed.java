package com.example.shdemo.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Breed {

    private Long id;
    private String name;
    private String description;
    private List<Dog> dogList;


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


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Dog> getDogList() {
        return dogList;
    }

    public void setDogList(List<Dog> dogList) {
        this.dogList = dogList;
    }
}
