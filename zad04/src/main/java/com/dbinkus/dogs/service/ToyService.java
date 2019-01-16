package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.Toy;

import java.util.List;

public interface ToyService {

    Long addToy(Toy toy);
    List getAllToys();
    Toy getToyByName(String name);
    void updateToy(Toy toy);
    void deleteToy(Toy toy);

}
