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

        testImplementation(dogServiceImpl);


    }

    public static void initWithSampleData(DogService dogService) {

        //add dog and addAllDogs test

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
        Dog toUpdate = dogService.findDogByName("Reksio");
        toUpdate.setWeight(999.99);
        toUpdate = dogService.updateDog(toUpdate);
        System.out.println(toUpdate);

        //find functions test
        System.out.println("All vaccinated dogs:");
        System.out.println(dogService.getAllVaccinatedDogs("weight",SortingMode.DESCENDING));
        System.out.println("\nAll non vaccinated dogs:");
        System.out.println(dogService.getAllNonVaccinatedDogs("name",SortingMode.ASCENDING));
        System.out.println("\nAll male dogs:");
        System.out.println(dogService.getAllMaleDogs("date_of_birth",SortingMode.ASCENDING));
        System.out.println("\nAll female dogs:");
        System.out.println(dogService.getAllFemaleDogs("id",SortingMode.ASCENDING));
        System.out.println("\nAll dogs heavier than 7.0:");
        System.out.println(dogService.getAllDogsHeavierThan(7.0,"is_vaccinated",SortingMode.DESCENDING));
        System.out.println("\nAll dogs lighter than 7.0:");
        System.out.println(dogService.getAllDogsLighterThan(7.0,"weight",SortingMode.ASCENDING));
        System.out.println("\nAll dogs with weight in range (3.2,11.2)");
        System.out.println(dogService.getAllDogsWeightInRange(3.2,11.2,"sex",SortingMode.ASCENDING));
        System.out.println("\nAll dogs born before 2009-06-01:");
        System.out.println(dogService.getAllDogsBornBefore("2009-06-01","id",SortingMode.DESCENDING));
        System.out.println("\nAll dogs born after 2009-06-01:");
        System.out.println(dogService.getAllDogsBornAfter("2009-06-01","name",SortingMode.ASCENDING));
        System.out.println("\nAll dogs born in range(2009-01-12, 2014-01-01)");
        System.out.println(dogService.getAllDogsBornInRange("2009-01-12","2014-01-01","is_vaccinated",SortingMode.ASCENDING));
        System.out.println("\nAll dogs with name starting with \'L\'");
        System.out.println(dogService.getAllDogsWithNameLike("L%","date_of_birth",SortingMode.DESCENDING));

        //find by name test
        System.out.println("\nFound dog with name \"Burek\"");
        System.out.println(dogService.findDogByName("Burek"));
        
        //delete dog test
        System.out.println("\nFound dog with id = 2");
        dogService.removeDog(dogService.findDogById(2));
        System.out.println("\nAll dogs listed:");
        System.out.println(dogService.getAllDogs());

        //clear dogs test
        dogService.clearDogs();
        System.out.println(dogService.getAllDogs());
    }


}
