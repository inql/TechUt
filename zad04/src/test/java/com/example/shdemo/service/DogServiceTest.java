package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import com.example.shdemo.domain.Dog;

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
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DogServiceTest {

    @Autowired
    DogService dogService;

    private final String DOG_NAME = "Burek";
    private final Boolean DOG_IS_VACCINATED = true;
    private final Double DOG_WEIGHT = 23.5;
    private final Character DOG_SEX = 'c';

    @Test
    public void addDogTest(){

        List<Dog> dogList = dogService.getAllDogs();

        for (Dog dog: dogList){
            if(dog.getName().equals(DOG_NAME)){
                dogService.deleteDog(dog);
            }
        }

        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG_NAME);
        dogToAdd.setVaccinated(DOG_IS_VACCINATED);
        dogToAdd.setWeight(DOG_WEIGHT);
        dogToAdd.setSex(DOG_SEX);

        dogService.addDog(dogToAdd);

        Dog addedDog = dogService.getDogByName(DOG_NAME);

        assertEquals(DOG_NAME,addedDog.getName());
        assertEquals(DOG_IS_VACCINATED,addedDog.getVaccinated());
        assertEquals(DOG_WEIGHT,addedDog.getWeight());
        assertEquals(DOG_SEX,addedDog.getSex());
    }

    @Test
    public void addOwnerTest(){




    }


}
