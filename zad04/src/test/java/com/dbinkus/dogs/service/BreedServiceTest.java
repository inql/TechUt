package com.dbinkus.dogs.service;


import com.dbinkus.dogs.domain.Breed;
import com.dbinkus.dogs.domain.Dog;
import com.dbinkus.dogs.domain.Sex;
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
    private final Sex DOG1_SEX = Sex.MALE;
    private Date DOG1_DATE;

    {
        try {
            DOG1_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2018");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
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

    @Test
    public void updateBreedTest() {
        Breed breedToAdd = new Breed();
        breedToAdd.setName(BREED1_NAME);

        breedService.addBreed(breedToAdd);
        Breed addedBreed = breedService.getBreedByName(BREED1_NAME);
        addedBreed.setName("New name");
        breedService.updateBreed(breedToAdd);

        Breed updatedBreed = breedService.getBreedByName("New name");
        assertNotNull(updatedBreed);
        assertEquals("New name", updatedBreed.getName());
        assertEquals(1, breedService.getAllBreeds().size());
    }

    @Test
    public void deleteBreedTest(){
        Breed breedToAdd = new Breed();
        breedToAdd.setName(BREED1_NAME);

        breedService.addBreed(breedToAdd);
        Breed addedBreed = breedService.getBreedByName(BREED1_NAME);
        breedService.deleteBreed(addedBreed);

        List<Breed> breeds = breedService.getAllBreeds();

        assertEquals(0,breeds.size());
    }

    @Test
    public void deleteBreedWithDogAssignedTest(){
        Breed breedToAdd = new Breed();
        breedToAdd.setName(BREED1_NAME);

        breedService.addBreed(breedToAdd);
        Breed addedBreed = breedService.getBreedByName(BREED1_NAME);

        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG1_NAME);
        dogToAdd.setVaccinated(DOG1_IS_VACCINATED);
        dogToAdd.setWeight(DOG1_WEIGHT);
        dogToAdd.setSex(DOG1_SEX);
        dogToAdd.setDateOfBirth(DOG1_DATE);
        dogToAdd.setBreed(addedBreed);

        dogService.addDog(dogToAdd);

        Dog addedDog = dogService.getDogByName(DOG1_NAME);

        assertEquals(breedToAdd,addedDog.getBreed());
        breedService.deleteBreed(addedBreed);

        List<Breed> breeds = breedService.getAllBreeds();
        List<Dog> dogs = dogService.getAllDogs();

        assertEquals(0,dogs.size());
        assertEquals(0,breeds.size());

    }


}
