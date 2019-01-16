package com.example.shdemo.service;

import com.example.shdemo.domain.Dog;
import com.example.shdemo.domain.Owner;
import com.example.shdemo.domain.Sex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class OwnerServiceTest {

    @Autowired
    DogService dogService;
    @Autowired
    OwnerService ownerService;

    private final String DOG1_NAME = "Burek";
    private final Boolean DOG1_IS_VACCINATED = true;
    private final Double DOG1_WEIGHT = 23.5;
    private final Sex DOG1_SEX = Sex.MALE;

    private final String DOG2_NAME = "Reksio";
    private final Boolean DOG2_IS_VACCINATED = false;
    private final Double DOG2_WEIGHT = 4.9;
    private final Sex DOG2_SEX = Sex.FEMALE;

    private final String OWNER_FNAME = "Jan";
    private final String OWNER_LNAME = "Abacki";
    private final Date OWNER_BDATE = new GregorianCalendar(100,10,10).getTime();

    @Before
    public void setUp(){
        List<Owner> owners = ownerService.getAllOwners();

        for(Owner owner : owners){
            if(owner.getFirstName().equals(OWNER_FNAME))
                ownerService.deleteOwner(owner);
        }

        List<Dog> dogList = dogService.getAllDogs();

        for (Dog dog: dogList){
            if(dog.getName().equals(DOG1_NAME)){
                dogService.deleteDog(dog);
            }
        }

    }

    @Test
    public void addOwnerTest(){
        Owner ownerToAdd = new Owner();
        ownerToAdd.setFirstName(OWNER_FNAME);
        ownerToAdd.setLastName(OWNER_LNAME);
        ownerToAdd.setBirthDate(OWNER_BDATE);

        ownerService.addOwner(ownerToAdd);

        Owner addedOwner = ownerService.getOwnerByName(OWNER_FNAME);

        assertEquals(OWNER_FNAME,addedOwner.getFirstName());
        assertEquals(OWNER_LNAME,addedOwner.getLastName());
        assertEquals(OWNER_BDATE,addedOwner.getBirthDate());
    }

    @Test
    public void updateOwnerTest(){
        Owner ownerToAdd = new Owner();
        ownerToAdd.setFirstName(OWNER_FNAME);
        ownerToAdd.setLastName(OWNER_LNAME);
        ownerToAdd.setBirthDate(OWNER_BDATE);

        ownerService.addOwner(ownerToAdd);

        ownerToAdd.setLastName("Babacki");
        ownerService.updateOwner(ownerToAdd);
        Owner addedOwner = ownerService.getOwnerByName(OWNER_FNAME);

        assertEquals(OWNER_FNAME,addedOwner.getFirstName());
        assertEquals("Babacki",addedOwner.getLastName());
        assertEquals(OWNER_BDATE,addedOwner.getBirthDate());
    }

    @Test
    public void addDogToOwnerTest(){
        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG1_NAME);
        dogToAdd.setVaccinated(DOG1_IS_VACCINATED);
        dogToAdd.setWeight(DOG1_WEIGHT);
        dogToAdd.setSex(DOG1_SEX);

        Owner ownerToAdd = new Owner();
        ownerToAdd.setFirstName(OWNER_FNAME);
        ownerToAdd.setLastName(OWNER_LNAME);
        ownerToAdd.setBirthDate(OWNER_BDATE);
        ownerToAdd.getDogList().add(dogToAdd);

        dogService.addDog(dogToAdd);
        ownerService.addOwner(ownerToAdd);
        Owner addedOwner = ownerService.getOwnerByName(OWNER_FNAME);
        Dog ownersDog = addedOwner.getDogList().get(0);

        assertEquals(OWNER_FNAME,addedOwner.getFirstName());
        assertEquals(OWNER_LNAME,addedOwner.getLastName());
        assertEquals(OWNER_BDATE,addedOwner.getBirthDate());
        assertEquals(1,addedOwner.getDogList().size());
        assertEquals(DOG1_NAME,ownersDog.getName());
        assertEquals(DOG1_WEIGHT,ownersDog.getWeight());
        assertEquals(DOG1_SEX,ownersDog.getSex());
        assertEquals(DOG1_IS_VACCINATED,ownersDog.getVaccinated());

    }

    @Test
    public void removeDogFromOwnerTest(){
        Dog dogToRemove = new Dog();
        dogToRemove.setName(DOG1_NAME);
        dogToRemove.setVaccinated(DOG1_IS_VACCINATED);
        dogToRemove.setWeight(DOG1_WEIGHT);
        dogToRemove.setSex(DOG1_SEX);

        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG2_NAME);
        dogToAdd.setVaccinated(DOG2_IS_VACCINATED);
        dogToAdd.setWeight(DOG2_WEIGHT);
        dogToAdd.setSex(DOG2_SEX);

        Owner ownerToAdd = new Owner();
        ownerToAdd.setFirstName(OWNER_FNAME);
        ownerToAdd.setLastName(OWNER_LNAME);
        ownerToAdd.setBirthDate(OWNER_BDATE);

        dogService.addDog(dogToRemove);
        dogService.addDog(dogToAdd);
        Long ownerId = ownerService.addOwner(ownerToAdd);

        Dog addedDog = dogService.getDogByName(DOG2_NAME);
        Dog toRemoveDog = dogService.getDogByName(DOG1_NAME);

        ownerService.addDogToOwner(ownerId,addedDog.getId());
        ownerService.addDogToOwner(ownerId,toRemoveDog.getId());
        ownerService.deleteDogFromOwner(ownerService.getOwnerByName(OWNER_FNAME).getId(),dogService.getDogByName(DOG1_NAME).getId());


        Owner addedOwner = ownerService.getOwnerByName(OWNER_FNAME);
        Dog ownersDog = addedOwner.getDogList().get(0);



        assertEquals(OWNER_FNAME,addedOwner.getFirstName());
        assertEquals(OWNER_LNAME,addedOwner.getLastName());
        assertEquals(OWNER_BDATE,addedOwner.getBirthDate());
        assertEquals(1,addedOwner.getDogList().size());
        assertEquals(DOG2_NAME,ownersDog.getName());
        assertEquals(DOG2_WEIGHT,ownersDog.getWeight());
        assertEquals(DOG2_SEX,ownersDog.getSex());
        assertEquals(DOG2_IS_VACCINATED,ownersDog.getVaccinated());
        assertEquals(true,ownersDog.getHasOwner());
        assertEquals(false,dogToRemove.getHasOwner());

    }

    @Test
    public void deleteOwnerTest(){
        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG1_NAME);
        dogToAdd.setVaccinated(DOG1_IS_VACCINATED);
        dogToAdd.setWeight(DOG1_WEIGHT);
        dogToAdd.setSex(DOG1_SEX);

        Owner ownerToAdd = new Owner();
        ownerToAdd.setFirstName(OWNER_FNAME);
        ownerToAdd.setLastName(OWNER_LNAME);
        ownerToAdd.setBirthDate(OWNER_BDATE);
        ownerToAdd.getDogList().add(dogToAdd);

        dogService.addDog(dogToAdd);
        ownerService.addOwner(ownerToAdd);

        ownerService.deleteOwner(ownerToAdd);

        Dog dogWithoutOwner = dogService.getDogByName(DOG1_NAME);

        assertNotNull(dogWithoutOwner);
        assertEquals(0,ownerService.getAllOwners().size());
        assertEquals(1,dogService.getAllDogs().size());
        assertEquals(false,dogWithoutOwner.getHasOwner());
    }



}
