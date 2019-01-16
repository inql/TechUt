package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.Breed;
import com.dbinkus.dogs.domain.Dog;
import com.dbinkus.dogs.domain.Toy;

import java.util.Date;
import java.util.List;

public interface DogService {

    void addDog(Dog dog);
    List getAllDogs();
    List getAllDogsWithBreed(Breed breed);
    List getAllDogsWithToy(Toy toy);
    Dog getDogByName(String name);
    void removeDogsBornBefore(Date date);
    void removeDogsBornAfter(Date date);
    void updateDog(Dog dog);
    void deleteDog(Dog dog);
    void giveToy(Dog dog, Toy toy);
    void removeToy(Dog dog, Toy toy);





}
