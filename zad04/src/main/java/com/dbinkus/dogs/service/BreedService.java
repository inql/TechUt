package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.Breed;

import java.util.List;

public interface BreedService {

    void addBreed(Breed breed);
    List getAllBreeds();
    Breed getBreedByName(String name);
    void updateBreed(Breed breed);
    void deleteBreed(Breed breed);

}
