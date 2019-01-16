package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.Owner;

import java.util.List;

public interface OwnerService {

    void addDogToOwner(Long ownerId, Long dogId);
    void deleteDogFromOwner(Long ownerId, Long dogId);
    Long addOwner(Owner owner);
    List getAllOwners();
    Owner getOwnerById(Long id);
    Owner getOwnerByName(String name);
    void updateOwner(Owner owner);
    void deleteOwner(Owner owner);
    List getAllDogsFromOwner(Owner owner);


}
