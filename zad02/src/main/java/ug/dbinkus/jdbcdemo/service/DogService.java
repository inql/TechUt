package ug.dbinkus.jdbcdemo.service;

import ug.dbinkus.jdbcdemo.SortingMode;
import ug.dbinkus.jdbcdemo.domain.Dog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DogService {

    void addDog(Dog dog) throws SQLException;
    void deleteDog(Dog dog) throws SQLException;
    Connection getConnection();
    void clearDogs();
    List<Dog> getAllDogs() throws SQLException;
    void addAllDogs(List<Dog> dogList);
    Dog findDogById(long id);
    Dog findDogByName(String name);
    Dog removeDog(Dog dog);
    Dog updateDog(Dog dog);
    List<Dog> getAllVaccinatedDogs(String sortingColumn, SortingMode sortingMode);




}
