package ug.dbinkus.jdbcdemo.service;

import ug.dbinkus.jdbcdemo.SortingMode;
import ug.dbinkus.jdbcdemo.domain.Dog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DogService {

    void addDog(Dog dog);
    Connection getConnection();
    void clearDogs();
    List<Dog> getAllDogs();
    void addAllDogs(List<Dog> dogList);
    Dog findDogById(long id);
    Dog findDogByName(String name);
    Dog removeDog(Dog dog);
    Dog updateDog(Dog dog);
    List<Dog> getAllVaccinatedDogs(String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllNonVaccinatedDogs(String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllMaleDogs(String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllFemaleDogs(String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllDogsHeavierThan(double minWeight, String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllDogsLighterThan(double maxWeight, String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllDogsWeightInRange(double minWeight, double maxWeight, String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllDogsBornBefore(String date, String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllDogsBornAfter(String date, String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllDogsBornInRange(String from,String to, String sortingColumn, SortingMode sortingMode);
    List<Dog> getAllDogsWithNameLike(String namePattern, String sortingColumn, SortingMode sortingMode);







}
