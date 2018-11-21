package ug.dbinkus.jdbcdemo;

import ug.dbinkus.jdbcdemo.domain.Dog;
import ug.dbinkus.jdbcdemo.service.DogService;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        DogService dogService = new DogService();
        Dog burekDog = new Dog("Burek","2018-01-01",true,25.4,'m');
        dogService.addDog(burekDog);



    }


}
