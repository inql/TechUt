package ug.dbinkus.jdbcdemo.service;

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



}
