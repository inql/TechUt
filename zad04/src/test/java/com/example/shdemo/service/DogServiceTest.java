package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import com.example.shdemo.domain.Dog;

import com.example.shdemo.domain.Owner;
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

    private final String DOG1_NAME = "Burek";
    private final Boolean DOG1_IS_VACCINATED = true;
    private final Double DOG1_WEIGHT = 23.5;
    private final Character DOG1_SEX = 'c';

    private final String DOG2_NAME = "Reksio";
    private final Boolean DOG2_IS_VACCINATED = false;
    private final Double DOG2_WEIGHT = 4.9;
    private final Character DOG2_SEX = 'c';

    @Test
    public void addDogTest(){

        List<Dog> dogList = dogService.getAllDogs();

        for (Dog dog: dogList){
            if(dog.getName().equals(DOG1_NAME)){
                dogService.deleteDog(dog);
            }
        }

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




}
