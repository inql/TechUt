package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.shdemo.domain.Dog;

import com.example.shdemo.domain.Owner;
import com.example.shdemo.domain.Toy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DogServiceTest {

    @Autowired
    DogService dogService;
    @Autowired
    ToyService toyService;

    private final String TOY_NAME1 = "Bone";
    private final String TOY_DESC1 = "Description1";

    private final String DOG1_NAME = "Burek";
    private final Boolean DOG1_IS_VACCINATED = true;
    private final Double DOG1_WEIGHT = 23.5;
    private final Character DOG1_SEX = 'c';

    private final String DOG2_NAME = "Reksio";
    private final Boolean DOG2_IS_VACCINATED = false;
    private final Double DOG2_WEIGHT = 4.9;
    private final Character DOG2_SEX = 'c';

    @Before
    public void setUp(){
        List<Dog> dogList = dogService.getAllDogs();

        for (Dog dog: dogList){
            if(dog.getName().equals(DOG1_NAME)){
                dogService.deleteDog(dog);
            }
        }
    }

    @Test
    public void addDogTest(){
        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG1_NAME);
        dogToAdd.setVaccinated(DOG1_IS_VACCINATED);
        dogToAdd.setWeight(DOG1_WEIGHT);
        dogToAdd.setSex(DOG1_SEX);

        dogService.addDog(dogToAdd);

        Dog addedDog = dogService.getDogByName(DOG1_NAME);

        assertEquals(DOG1_NAME,addedDog.getName());
        assertEquals(DOG1_IS_VACCINATED,addedDog.getVaccinated());
        assertEquals(DOG1_WEIGHT,addedDog.getWeight());
        assertEquals(DOG1_SEX,addedDog.getSex());
    }

    @Test
    public void updateDogTest(){
        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG2_NAME);
        dogToAdd.setVaccinated(DOG2_IS_VACCINATED);
        dogToAdd.setWeight(DOG2_WEIGHT);
        dogToAdd.setSex(DOG2_SEX);

        dogService.addDog(dogToAdd);

        dogToAdd.setVaccinated(true);
        dogService.updateDog(dogToAdd);

        Dog updatedDog = dogService.getDogByName(DOG2_NAME);

        assertEquals(DOG2_NAME,updatedDog.getName());
        assertEquals(true,updatedDog.getVaccinated());
        assertEquals(DOG2_WEIGHT,updatedDog.getWeight());
        assertEquals(DOG2_SEX,updatedDog.getSex());

    }

    @Test
    public void deleteDogTest(){
        Dog dogToDelete = new Dog();
        dogToDelete.setName(DOG1_NAME);
        dogToDelete.setVaccinated(DOG1_IS_VACCINATED);
        dogToDelete.setWeight(DOG1_WEIGHT);
        dogToDelete.setSex(DOG1_SEX);

        dogService.addDog(dogToDelete);

        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG2_NAME);
        dogToAdd.setVaccinated(DOG2_IS_VACCINATED);
        dogToAdd.setWeight(DOG2_WEIGHT);
        dogToAdd.setSex(DOG2_SEX);

        dogService.addDog(dogToAdd);

        dogService.deleteDog(dogToDelete);

        List<Dog> dogs = dogService.getAllDogs();
        Dog remainedDog = dogs.get(0);

        assertEquals(dogs.size(),1);
        assertEquals(DOG2_NAME,remainedDog.getName());
        assertEquals(DOG2_IS_VACCINATED,remainedDog.getVaccinated());
        assertEquals(DOG2_WEIGHT,remainedDog.getWeight());
        assertEquals(DOG2_SEX,remainedDog.getSex());

    }

    @Test
    public void giveToyTest(){
        Dog dog = new Dog();
        dog.setName(DOG1_NAME);
        dog.setVaccinated(DOG1_IS_VACCINATED);
        dog.setWeight(DOG1_WEIGHT);
        dog.setSex(DOG1_SEX);

        Toy toy = new Toy();
        toy.setName(TOY_NAME1);
        toy.setDescription(TOY_DESC1);

        dogService.addDog(dog);
        toyService.addToy(toy);

        Dog addedDog = dogService.getDogByName(DOG1_NAME);
        Toy addedToy = toyService.getToyByName(TOY_NAME1);

        assertNotNull(addedDog);
        assertNotNull(addedToy);

        dogService.giveToy(addedDog,addedToy);

        addedDog = dogService.getDogByName(DOG1_NAME);

        assertEquals(1,addedDog.getToyList().size());

        addedToy = addedDog.getToyList().get(0);

        assertEquals(TOY_NAME1,addedToy.getName());
        assertEquals(TOY_DESC1,addedToy.getDescription());
    }

    @Test
    public void multipleGiveToyTest(){
        Dog firstDog = new Dog();
        firstDog.setName(DOG1_NAME);
        firstDog.setVaccinated(DOG1_IS_VACCINATED);
        firstDog.setWeight(DOG1_WEIGHT);
        firstDog.setSex(DOG1_SEX);

        dogService.addDog(firstDog);

        Dog secondDog = new Dog();
        secondDog.setName(DOG2_NAME);
        secondDog.setVaccinated(DOG2_IS_VACCINATED);
        secondDog.setWeight(DOG2_WEIGHT);
        secondDog.setSex(DOG2_SEX);

        dogService.addDog(secondDog);

        Toy toy = new Toy();
        toy.setName(TOY_NAME1);
        toy.setDescription(TOY_DESC1);

        toyService.addToy(toy);

        firstDog = dogService.getDogByName(DOG1_NAME);
        secondDog = dogService.getDogByName(DOG2_NAME);
        toy = toyService.getToyByName(TOY_NAME1);

        //assigning same toy to both dogs

        dogService.giveToy(firstDog,toy);
        dogService.giveToy(secondDog,toy);

        firstDog = dogService.getDogByName(DOG1_NAME);
        secondDog = dogService.getDogByName(DOG2_NAME);

        assertEquals(1,firstDog.getToyList().size());
        assertEquals(1,secondDog.getToyList().size());

        Toy firstDogsToy = firstDog.getToyList().get(0);
        Toy secondDogsToy = secondDog.getToyList().get(0);

        assertEquals(firstDogsToy,secondDogsToy);
    }
    




}
