package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.*;
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
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DogServiceTest {

    @Autowired
    DogService dogService;
    @Autowired
    ToyService toyService;
    @Autowired
    BreedService breedService;
    @Autowired
    OwnerService ownerService;

    private Date BEFORE_DATE;
    private Date AFTER_DATE;

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

    private final String OWNER_FNAME = "Jan";
    private final String OWNER_LNAME = "Abacki";
    private final Date OWNER_BDATE = new GregorianCalendar(100,10,10).getTime();
    {
        try {
            DOG1_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2018");
            DOG2_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2011");
            BEFORE_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2014");
            AFTER_DATE = new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2014");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Before
    public void setUp(){
        List<Dog> dogList = dogService.getAllDogs();

        for (Dog dog: dogList){
            if(dog.getName().equals(DOG1_NAME) || dog.getName().equals(DOG2_NAME)){
                dogService.deleteDog(dog);
            }
        }
        List<Toy> allToys = toyService.getAllToys();

        for (Toy toy: allToys){
            if(toy.getName().equals(TOY_NAME1)){
                toyService.deleteToy(toy);
            }
        }
        List<Breed> breeds = breedService.getAllBreeds();

        for(Breed breed : breeds){
            if(breed.getName().equals(BREED1_NAME) || breed.getName().equals(BREED2_NAME))
                breedService.deleteBreed(breed);
        }
    }

    @Test
    public void addDogTest(){
        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG1_NAME);
        dogToAdd.setVaccinated(DOG1_IS_VACCINATED);
        dogToAdd.setWeight(DOG1_WEIGHT);
        dogToAdd.setSex(DOG1_SEX);
        dogToAdd.setDateOfBirth(DOG1_DATE);

        dogService.addDog(dogToAdd);

        Dog addedDog = dogService.getDogByName(DOG1_NAME);

        assertEquals(DOG1_NAME,addedDog.getName());
        assertEquals(DOG1_IS_VACCINATED,addedDog.getVaccinated());
        assertEquals(DOG1_WEIGHT,addedDog.getWeight());
        assertEquals(DOG1_SEX,addedDog.getSex());
        assertEquals(DOG1_DATE,addedDog.getDateOfBirth());
    }

    @Test
    public void updateDogTest(){
        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG2_NAME);
        dogToAdd.setVaccinated(DOG2_IS_VACCINATED);
        dogToAdd.setWeight(DOG2_WEIGHT);
        dogToAdd.setSex(DOG2_SEX);
        dogToAdd.setDateOfBirth(DOG2_DATE);

        dogService.addDog(dogToAdd);

        dogToAdd.setVaccinated(true);
        dogService.updateDog(dogToAdd);

        Dog updatedDog = dogService.getDogByName(DOG2_NAME);

        assertEquals(DOG2_NAME,updatedDog.getName());
        assertEquals(true,updatedDog.getVaccinated());
        assertEquals(DOG2_WEIGHT,updatedDog.getWeight());
        assertEquals(DOG2_SEX,updatedDog.getSex());
        assertEquals(DOG2_DATE,updatedDog.getDateOfBirth());

    }

    @Test
    public void deleteDogTest(){
        Dog dogToDelete = new Dog();
        dogToDelete.setName(DOG1_NAME);
        dogToDelete.setVaccinated(DOG1_IS_VACCINATED);
        dogToDelete.setWeight(DOG1_WEIGHT);
        dogToDelete.setSex(DOG1_SEX);
        dogToDelete.setDateOfBirth(DOG1_DATE);

        dogService.addDog(dogToDelete);

        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG2_NAME);
        dogToAdd.setVaccinated(DOG2_IS_VACCINATED);
        dogToAdd.setWeight(DOG2_WEIGHT);
        dogToAdd.setSex(DOG2_SEX);
        dogToAdd.setDateOfBirth(DOG2_DATE);

        dogService.addDog(dogToAdd);

        dogService.deleteDog(dogToDelete);

        List<Dog> dogs = dogService.getAllDogs();
        Dog remainedDog = dogs.get(0);

        assertEquals(dogs.size(),1);
        assertEquals(DOG2_NAME,remainedDog.getName());
        assertEquals(DOG2_IS_VACCINATED,remainedDog.getVaccinated());
        assertEquals(DOG2_WEIGHT,remainedDog.getWeight());
        assertEquals(DOG2_SEX,remainedDog.getSex());
        assertEquals(DOG2_DATE,remainedDog.getDateOfBirth());


    }

    @Test
    public void deleteDogWithOwnerAssociatedTest(){
        Dog dogToDelete = new Dog();
        dogToDelete.setName(DOG1_NAME);
        dogToDelete.setVaccinated(DOG1_IS_VACCINATED);
        dogToDelete.setWeight(DOG1_WEIGHT);
        dogToDelete.setSex(DOG1_SEX);
        dogToDelete.setDateOfBirth(DOG1_DATE);

        dogService.addDog(dogToDelete);

        Owner ownerToAdd = new Owner();
        ownerToAdd.setFirstName(OWNER_FNAME);
        ownerToAdd.setLastName(OWNER_LNAME);
        ownerToAdd.setBirthDate(OWNER_BDATE);

        ownerService.addOwner(ownerToAdd);

        dogToDelete = dogService.getDogByName(DOG1_NAME);
        ownerToAdd = ownerService.getOwnerByName(OWNER_FNAME);

        ownerService.addDogToOwner(ownerToAdd.getId(),dogToDelete.getId());

        dogService.deleteDog(dogToDelete);

        ownerToAdd = ownerService.getOwnerByName(OWNER_FNAME);

        assertEquals(0,ownerToAdd.getDogList().size());

    }

    @Test
    public void getAllDogsWithBreedTest(){
        Breed firstBreed = new Breed();
        firstBreed.setName(BREED1_NAME);

        breedService.addBreed(firstBreed);
        firstBreed = breedService.getBreedByName(BREED1_NAME);
        Breed secondBreed = new Breed();
        secondBreed.setName(BREED2_NAME);

        breedService.addBreed(secondBreed);

        secondBreed = breedService.getBreedByName(BREED2_NAME);

        assertNotNull(firstBreed);
        assertNotNull(secondBreed);

        Dog firstDog = new Dog();
        firstDog.setName(DOG1_NAME);
        firstDog.setVaccinated(DOG1_IS_VACCINATED);
        firstDog.setWeight(DOG1_WEIGHT);
        firstDog.setSex(DOG1_SEX);
        firstDog.setBreed(firstBreed);
        firstDog.setDateOfBirth(DOG1_DATE);

        dogService.addDog(firstDog);

        Dog secondDog = new Dog();
        secondDog.setName(DOG2_NAME);
        secondDog.setVaccinated(DOG2_IS_VACCINATED);
        secondDog.setWeight(DOG2_WEIGHT);
        secondDog.setSex(DOG2_SEX);
        secondDog.setBreed(secondBreed);
        secondDog.setDateOfBirth(DOG2_DATE);

        dogService.addDog(secondDog);

        firstDog = dogService.getDogByName(DOG1_NAME);
        secondDog = dogService.getDogByName(DOG2_NAME);

        assertNotNull(firstDog);
        assertNotNull(secondDog);


        List<Dog> firstBreedDogs = dogService.getAllDogsWithBreed(firstBreed);
        List<Dog> secondBreedDogs = dogService.getAllDogsWithBreed(secondBreed);

        assertEquals(1,firstBreedDogs.size());
        assertEquals(1,secondBreedDogs.size());

        Dog firstBreedDog = firstBreedDogs.get(0);
        Dog secondBreedDog = secondBreedDogs.get(0);

        assertEquals(firstDog,firstBreedDog);
        assertEquals(secondDog,secondBreedDog);
    }

    @Test
    public void getAllDogsWithToyTest(){
        Toy firstToy = new Toy();
        firstToy.setName(TOY_NAME1);
        firstToy.setDescription(TOY_DESC1);

        toyService.addToy(firstToy);
        firstToy = toyService.getToyByName(TOY_NAME1);
        Toy secondToy = new Toy();
        secondToy.setName(TOY_NAME2);
        secondToy.setDescription(TOY_DESC2);

        toyService.addToy(secondToy);
        secondToy = toyService.getToyByName(TOY_NAME2);

        assertNotNull(firstToy);
        assertNotNull(secondToy);

        Dog firstDog = new Dog();
        firstDog.setName(DOG1_NAME);
        firstDog.setVaccinated(DOG1_IS_VACCINATED);
        firstDog.setWeight(DOG1_WEIGHT);
        firstDog.setSex(DOG1_SEX);
        firstDog.setDateOfBirth(DOG1_DATE);
        dogService.addDog(firstDog);

        Dog secondDog = new Dog();
        secondDog.setName(DOG2_NAME);
        secondDog.setVaccinated(DOG2_IS_VACCINATED);
        secondDog.setWeight(DOG2_WEIGHT);
        secondDog.setSex(DOG2_SEX);
        secondDog.setDateOfBirth(DOG2_DATE);

        dogService.addDog(secondDog);

        firstDog = dogService.getDogByName(DOG1_NAME);
        secondDog = dogService.getDogByName(DOG2_NAME);

        dogService.giveToy(firstDog,firstToy);
        dogService.giveToy(secondDog,secondToy);

        assertNotNull(firstDog);
        assertNotNull(secondDog);

        firstDog = dogService.getDogByName(DOG1_NAME);
        secondDog = dogService.getDogByName(DOG2_NAME);


        List<Dog> firstToyDogs = dogService.getAllDogsWithToy(firstToy);
        List<Dog> secondToyDogs = dogService.getAllDogsWithToy(secondToy);

        assertEquals(1,firstToyDogs.size());
        assertEquals(1,secondToyDogs.size());

        Dog firstToyDog = firstToyDogs.get(0);
        Dog secondToyDog = secondToyDogs.get(0);

        assertEquals(firstDog,firstToyDog);
        assertEquals(secondDog,secondToyDog);
    }

    @Test
    public void removeDogsBornBeforeTest(){
        Dog firstDog = new Dog();
        firstDog.setName(DOG1_NAME);
        firstDog.setVaccinated(DOG1_IS_VACCINATED);
        firstDog.setWeight(DOG1_WEIGHT);
        firstDog.setSex(DOG1_SEX);
        firstDog.setDateOfBirth(DOG1_DATE);
        dogService.addDog(firstDog);

        Dog secondDog = new Dog();
        secondDog.setName(DOG2_NAME);
        secondDog.setVaccinated(DOG2_IS_VACCINATED);
        secondDog.setWeight(DOG2_WEIGHT);
        secondDog.setSex(DOG2_SEX);
        secondDog.setDateOfBirth(DOG2_DATE);

        dogService.addDog(secondDog);

        dogService.removeDogsBornBefore(BEFORE_DATE);

        //second dog should be removed

        firstDog = dogService.getDogByName(DOG1_NAME);
        secondDog = dogService.getDogByName(DOG2_NAME);
        assertNull(secondDog);
        assertNotNull(firstDog);

        List<Dog> dogs = dogService.getAllDogs();
        assertEquals(1,dogs.size());
        assertEquals(firstDog,dogs.get(0));

    }

    @Test
    public void removeDogsBornAfterTest(){
        Dog firstDog = new Dog();
        firstDog.setName(DOG1_NAME);
        firstDog.setVaccinated(DOG1_IS_VACCINATED);
        firstDog.setWeight(DOG1_WEIGHT);
        firstDog.setSex(DOG1_SEX);
        firstDog.setDateOfBirth(DOG1_DATE);
        dogService.addDog(firstDog);

        Dog secondDog = new Dog();
        secondDog.setName(DOG2_NAME);
        secondDog.setVaccinated(DOG2_IS_VACCINATED);
        secondDog.setWeight(DOG2_WEIGHT);
        secondDog.setSex(DOG2_SEX);
        secondDog.setDateOfBirth(DOG2_DATE);

        dogService.addDog(secondDog);

        dogService.removeDogsBornAfter(BEFORE_DATE);

        //first dog should be removed

        firstDog = dogService.getDogByName(DOG1_NAME);
        secondDog = dogService.getDogByName(DOG2_NAME);
        assertNull(firstDog);
        assertNotNull(secondDog);

        List<Dog> dogs = dogService.getAllDogs();
        assertEquals(1,dogs.size());
        assertEquals(secondDog,dogs.get(0));

    }

    @Test
    public void giveToyTest(){
        Dog dog = new Dog();
        dog.setName(DOG1_NAME);
        dog.setVaccinated(DOG1_IS_VACCINATED);
        dog.setWeight(DOG1_WEIGHT);
        dog.setSex(DOG1_SEX);
        dog.setDateOfBirth(DOG1_DATE);

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
        firstDog.setDateOfBirth(DOG1_DATE);

        dogService.addDog(firstDog);

        Dog secondDog = new Dog();
        secondDog.setName(DOG2_NAME);
        secondDog.setVaccinated(DOG2_IS_VACCINATED);
        secondDog.setWeight(DOG2_WEIGHT);
        secondDog.setSex(DOG2_SEX);
        secondDog.setDateOfBirth(DOG2_DATE);

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

    @Test
    public void removeToyTest(){
        Dog firstDog = new Dog();
        firstDog.setName(DOG1_NAME);
        firstDog.setVaccinated(DOG1_IS_VACCINATED);
        firstDog.setWeight(DOG1_WEIGHT);
        firstDog.setSex(DOG1_SEX);
        firstDog.setDateOfBirth(DOG1_DATE);

        dogService.addDog(firstDog);

        Dog secondDog = new Dog();
        secondDog.setName(DOG2_NAME);
        secondDog.setVaccinated(DOG2_IS_VACCINATED);
        secondDog.setWeight(DOG2_WEIGHT);
        secondDog.setSex(DOG2_SEX);
        secondDog.setDateOfBirth(DOG2_DATE);

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
        dogService.removeToy(firstDog,toy);

        firstDog = dogService.getDogByName(DOG1_NAME);
        secondDog = dogService.getDogByName(DOG2_NAME);

        assertEquals(0,firstDog.getToyList().size());
        assertEquals(1,secondDog.getToyList().size());

        Toy secondDogsToy = secondDog.getToyList().get(0);

        assertEquals(secondDogsToy.getName(),TOY_NAME1);
        assertEquals(secondDogsToy.getDescription(),TOY_DESC1);

    }




}
