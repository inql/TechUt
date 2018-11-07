package ug.dbinkus.jdbcdemo;

import ug.dbinkus.jdbcdemo.domain.Dog;
import ug.dbinkus.jdbcdemo.service.DogServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        DogServiceImpl dogServiceImpl = new DogServiceImpl();

        List<Dog> dogList = new ArrayList<>();

        //Sample data
        for(int i =0; i<10; i++){
            dogList.add(new Dog("Fifi"+(i+1),"2018-01-01",i%2==0,23+i,'m'));
        }

        dogServiceImpl.addAllDogs(dogList);



    }


}
