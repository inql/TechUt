package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.shdemo.domain.Dog;
import com.example.shdemo.domain.Owner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    private final Character DOG1_SEX = 'c';

    private final String OWNER_FNAME = "Jan";
    private final String OWNER_LNAME = "Abacki";
    private final Date OWNER_BDATE = new GregorianCalendar(100,10,10).getTime();

    @Test
    public void addOwnerTest(){
        List<Owner> owners = ownerService.getAllOwners();

        for(Owner owner : owners){
            if(owner.getFirstName().equals(OWNER_FNAME))
                ownerService.deleteOwner(owner);
        }

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
        List<Owner> owners = ownerService.getAllOwners();

        for(Owner owner : owners){
            if(owner.getFirstName().equals(OWNER_FNAME))
                ownerService.deleteOwner(owner);
        }

        Owner ownerToAdd = new Owner();
        ownerToAdd.setFirstName(OWNER_FNAME);
        ownerToAdd.setLastName(OWNER_LNAME);
        ownerToAdd.setBirthDate(OWNER_BDATE);

        ownerService.addOwner(ownerToAdd);

        ownerToAdd.setLastName("Babacki");
        Owner addedOwner = ownerService.getOwnerByName(OWNER_FNAME);

        assertEquals(OWNER_FNAME,addedOwner.getFirstName());
        assertEquals("Babacki",addedOwner.getLastName());
        assertEquals(OWNER_BDATE,addedOwner.getBirthDate());
    }

    @Test
    public void addDogToOwnerTest(){
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


        List<Owner> owners = ownerService.getAllOwners();

        for(Owner owner : owners){
            if(owner.getFirstName().equals(OWNER_FNAME))
                ownerService.deleteOwner(owner);
        }

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
    public void deleteOwnerTest(){
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


        List<Owner> owners = ownerService.getAllOwners();

        for(Owner owner : owners){
            if(owner.getFirstName().equals(OWNER_FNAME))
                ownerService.deleteOwner(owner);
        }

        Owner ownerToAdd = new Owner();
        ownerToAdd.setFirstName(OWNER_FNAME);
        ownerToAdd.setLastName(OWNER_LNAME);
        ownerToAdd.setBirthDate(OWNER_BDATE);
        ownerToAdd.getDogList().add(dogToAdd);

        dogService.addDog(dogToAdd);
        ownerService.addOwner(ownerToAdd);

        ownerService.deleteOwner(ownerToAdd);

        assertEquals(ownerService.getAllOwners().size(),0);
        assertEquals(dogService.getAllDogs().size(),0);
    }



}
