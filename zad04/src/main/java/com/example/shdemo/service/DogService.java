package com.example.shdemo.service;

import com.example.shdemo.domain.Dog;
import com.example.shdemo.domain.Owner;

import java.util.List;

public interface DogService {

    void addDog(Dog dog);
    List getAllDogs();
    Dog getDogByName(String name);
    void deleteDog(Dog dog);

    Long addOwner(Owner owner);
    List getAllOwners();
    Owner getOwnerById(Long id);
    void deleteOwner();




}
