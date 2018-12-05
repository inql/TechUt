package com.example.shdemo.service;

import com.example.shdemo.domain.Dog;

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
public class DogServiceTest {

    @Autowired
    DogService dogService;

    private final String NAME = "Burek";
    private final Boolean IS_VACCINATED = true;
    private final Double WEIGHT = 23.5;
    private final Character SEX = 'c';

    @Test
    public void addDogTest(){

        List<Dog> dogList = dogService.getAllDogs();

        for (Dog dog: dogList){
            if(dog.getName().equals(NAME)){
                //todo: deletion
            }
        }

        Dog dogToAdd = new Dog();
        dogToAdd.setName(NAME);
        dogToAdd.setVaccinated(IS_VACCINATED);
        dogToAdd.setWeight(WEIGHT);
        dogToAdd.setSex(SEX);

        dogService.addDog(dogToAdd);





    }


}
