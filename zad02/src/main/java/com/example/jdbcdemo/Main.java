package com.example.jdbcdemo;

import com.example.jdbcdemo.domain.Dog;
import com.example.jdbcdemo.service.DogService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        DogService dogService = new DogService();
        Dog burekDog = new Dog("Burek","2018-01-01",true,25.4,'m');
        dogService.addDog("Burek2","2018-01-01",true,25.4,'m');



    }


}
