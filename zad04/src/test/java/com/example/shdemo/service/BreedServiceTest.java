package com.example.shdemo.service;


import com.example.shdemo.domain.Breed;
import com.example.shdemo.domain.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class BreedServiceTest {

    @Autowired
    BreedService breedService;
    @Autowired
    DogService dogService;

    private final String BREED1_NAME = "Kundel";

    private final String DOG1_NAME = "Burek";
    private final Boolean DOG1_IS_VACCINATED = true;
    private final Double DOG1_WEIGHT = 23.5;
    private final Character DOG1_SEX = 'c';

    @Before
    public void setUp(){
        List<Breed> breeds = breedService.getAllBreeds();

        for(Breed breed : breeds){
            if(breed.getName().equals(BREED1_NAME))
                breedService.deleteBreed(breed);
        }
    }

    @Test
    public void addBreedTest(){
        Breed breedToAdd = new Breed();
        breedToAdd.setName(BREED1_NAME);

        breedService.addBreed(breedToAdd);

        Breed addedBreed = breedService.getBreedByName(BREED1_NAME);
        assertNotNull(addedBreed);
        assertEquals(BREED1_NAME,addedBreed.getName());
        assertEquals(1,breedService.getAllBreeds().size());


    }


}
