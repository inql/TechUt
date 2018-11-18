package ug.dbinkus.jdbcdemo;

import ug.dbinkus.jdbcdemo.domain.Dog;
import ug.dbinkus.jdbcdemo.service.DogService;
import ug.dbinkus.jdbcdemo.service.DogServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        DogServiceImpl dogServiceImpl = new DogServiceImpl();

        initWithSampleData(dogServiceImpl);

        dogServiceImpl.removeDog(dogServiceImpl.findDogById(2));
        Dog toUpdate = dogServiceImpl.findDogById(3);
        toUpdate.setName("Zmienilem imie :)");
        toUpdate = dogServiceImpl.updateDog(toUpdate);
        System.out.println(toUpdate);
        System.out.println(dogServiceImpl.getAllVaccinatedDogs("weight",SortingMode.DESCENDING));
        dogServiceImpl.addDog(new Dog("Test","2011-11-11",true,1.1,'f'));
        System.out.println(dogServiceImpl.getAllDogs());


    }

    public static void initWithSampleData(DogService dogService) {
        List<Dog> dogList = new ArrayList<>();

        dogList.add(new Dog("Burek", "2017-01-01", true, 24.1, 'm'));
        dogList.add(new Dog("Reksio", "2018-11-11", false, 11.2, 'm'));
        dogList.add(new Dog("Perla","2018-06-06",true,5.2,'f'));
        dogList.add(new Dog("Puszek","2009-06-01",true,7.0,'m'));
        dogList.add(new Dog("Lena","2009-01-12",true,3.2,'f'));

        dogService.addAllDogs(dogList);
    }


}
