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

        if(dogServiceImpl.findDogById(1)!=null){
            initWithSampleData(dogServiceImpl);
        }

        testImplementation(dogServiceImpl);


    }

    public static void initWithSampleData(DogService dogService) {
        List<Dog> dogList = new ArrayList<>();

        dogList.add(new Dog("Burek", "2017-01-01", true, 24.1, 'm'));
        dogList.add(new Dog("Reksio", "2018-11-11", false, 11.2, 'm'));
        dogList.add(new Dog("Perla","2018-06-06",true,5.2,'f'));
        dogList.add(new Dog("Puszek","2009-06-01",true,7.0,'m'));
        dogList.add(new Dog("Lena","2009-01-12",true,3.2,'f'));
        dogList.add(new Dog("Leszek","2014-01-01",false,1.1,'f'));

        dogService.addAllDogs(dogList);
    }

    public static void testImplementation(DogService dogService) throws SQLException {
        //update dog test
        Dog toUpdate = dogService.findDogById(3);
        toUpdate.setWeight(999.99);
        toUpdate = dogService.updateDog(toUpdate);
        System.out.println(toUpdate);

        //find functions test
        System.out.println("All vaccinated dogs:");
        System.out.println(dogService.getAllVaccinatedDogs("weight",SortingMode.DESCENDING));
        System.out.println("\nAll non vaccinated dogs:");
        System.out.println(dogService.getAllNonVaccinatedDogs("name",SortingMode.ASCENDING));

        System.out.println(dogService.getAllDogs());

        dogService.removeDog(dogService.findDogById(2));
    }


}
