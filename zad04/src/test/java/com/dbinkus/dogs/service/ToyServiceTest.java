package com.dbinkus.dogs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import com.dbinkus.dogs.domain.Dog;
import com.dbinkus.dogs.domain.Sex;
import com.dbinkus.dogs.domain.Toy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ToyServiceTest {

    @Autowired
    ToyService toyService;
    @Autowired
    DogService dogService;

    private final String TOY_NAME1 = "Bone";
    private final String TOY_DESC1 = "Description1";

    private final String TOY_NAME2 = "Ball";
    private final String TOY_DESC2 = "Description2";

    private final String DOG1_NAME = "Burek";
    private final Boolean DOG1_IS_VACCINATED = true;
    private final Double DOG1_WEIGHT = 23.5;
    private final Sex DOG1_SEX = Sex.MALE;
    private Date DOG1_DATE;

    private final String DOG2_NAME = "Reksio";
    private final Boolean DOG2_IS_VACCINATED = false;
    private final Double DOG2_WEIGHT = 4.9;
    private final Sex DOG2_SEX = Sex.FEMALE;
    private Date DOG2_DATE;

    private final String BREED1_NAME = "Kundel";
    private final String BREED2_NAME = "Labrador";

    {
        try {
            DOG1_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2018");
            DOG2_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2011");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Before
    public void setUp(){
        List<Toy> allToys = toyService.getAllToys();

        for (Toy toy: allToys){
            if(toy.getName().equals(TOY_NAME1)){
                toyService.deleteToy(toy);
            }
        }
        List<Dog> dogList = dogService.getAllDogs();

        for (Dog dog: dogList){
            if(dog.getName().equals(DOG1_NAME)){
                dogService.deleteDog(dog);
            }
        }

    }

    @Test
    public void addToyTest(){
        Toy toyToAdd = new Toy();
        toyToAdd.setName(TOY_NAME1);
        toyToAdd.setDescription(TOY_DESC1);

        toyService.addToy(toyToAdd);

        List<Toy> allToys = toyService.getAllToys();
        assertEquals(1,allToys.size());


        Toy addedToy = allToys.get(0);

        assertNotNull(addedToy);

        assertEquals(TOY_NAME1,addedToy.getName());
        assertEquals(TOY_DESC1,addedToy.getDescription());
    }

    @Test
    public void updateToyTest(){
        Toy toyToAdd = new Toy();
        toyToAdd.setName(TOY_NAME1);
        toyToAdd.setDescription(TOY_DESC1);

        toyService.addToy(toyToAdd);

        Toy addedToy = toyService.getToyByName(TOY_NAME1);
        addedToy.setName("Ball");
        toyService.updateToy(addedToy);

        List<Toy> allToys = toyService.getAllToys();
        assertEquals(1,allToys.size());

        Toy updatedToy = allToys.get(0);

        assertNotNull(updatedToy);

        assertEquals("Ball",updatedToy.getName());
        assertEquals(TOY_DESC1,updatedToy.getDescription());
    }

    @Test
    public void countToysOfTheSameTypeTest(){
        Toy firstToy = new Toy();
        firstToy.setName(TOY_NAME1);
        firstToy.setDescription(TOY_DESC1);

        toyService.addToy(firstToy);

        Toy secondToy = new Toy();
        secondToy.setName(TOY_NAME2);
        secondToy.setDescription(TOY_DESC2);

        toyService.addToy(secondToy);

        firstToy = toyService.getToyByName(TOY_NAME1);
        secondToy = toyService.getToyByName(TOY_NAME2);

        Dog firstDog = new Dog();
        firstDog.setName(DOG1_NAME);
        firstDog.setVaccinated(DOG1_IS_VACCINATED);
        firstDog.setWeight(DOG1_WEIGHT);
        firstDog.setSex(DOG1_SEX);
        firstDog.setDateOfBirth(DOG1_DATE);

        dogService.addDog(firstDog);

        firstDog = dogService.getDogByName(DOG1_NAME);

        Dog secondDog = new Dog();
        secondDog.setName(DOG2_NAME);
        secondDog.setVaccinated(DOG2_IS_VACCINATED);
        secondDog.setWeight(DOG2_WEIGHT);
        secondDog.setSex(DOG2_SEX);
        secondDog.setDateOfBirth(DOG2_DATE);

        dogService.addDog(secondDog);

        secondDog = dogService.getDogByName(DOG2_NAME);

        dogService.giveToy(firstDog,firstToy);
        dogService.giveToy(firstDog,secondToy);
        dogService.giveToy(secondDog,secondToy);

        Long firstToyCount = toyService.countToysOfTheSameType(firstToy);
        Long secondToyCount = toyService.countToysOfTheSameType(secondToy);

        assertEquals(1L, (long)firstToyCount);
        assertEquals(2L,(long)secondToyCount);
    }

    @Test
    public void deleteToyTest(){
        Toy toyToAdd = new Toy();
        toyToAdd.setName(TOY_NAME1);
        toyToAdd.setDescription(TOY_DESC1);

        toyService.addToy(toyToAdd);
        Toy toyToDelete = toyService.getToyByName(TOY_NAME1);
        toyService.deleteToy(toyToDelete);

        List<Toy> allToys = toyService.getAllToys();
        assertEquals(0,allToys.size());
    }

    @Test
    public void deleteToyAssignedToDogTest(){
        Toy toyToAdd = new Toy();
        toyToAdd.setName(TOY_NAME1);
        toyToAdd.setDescription(TOY_DESC1);

        toyService.addToy(toyToAdd);
        Toy addedToy = toyService.getToyByName(TOY_NAME1);

        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG1_NAME);
        dogToAdd.setVaccinated(DOG1_IS_VACCINATED);
        dogToAdd.setWeight(DOG1_WEIGHT);
        dogToAdd.setSex(DOG1_SEX);
        dogToAdd.setDateOfBirth(DOG1_DATE);
        dogService.addDog(dogToAdd);

        Dog addedDog = dogService.getDogByName(DOG1_NAME);
        dogService.giveToy(addedDog,addedToy);

        toyService.deleteToy(addedToy);

        List<Toy> toys = toyService.getAllToys();
        assertEquals(0,toys.size());
        Dog dogAfterDeletionOfToy = dogService.getDogByName(DOG1_NAME);
        assertNotNull(dogAfterDeletionOfToy);
        assertEquals(0,dogAfterDeletionOfToy.getToyList().size());


    }




}
