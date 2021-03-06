package com.dbinkus.dogs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.dbinkus.dogs.domain.Dog;
import com.dbinkus.dogs.domain.Sex;
import com.dbinkus.dogs.domain.Description;
import org.junit.Assert;
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
public class DescriptionServiceTest {

    @Autowired
    DescriptionService descriptionService;
    @Autowired
    DogService dogService;

    private final String DESCRIPTION1 = "To jest opis";

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
        List<Description> descriptions = descriptionService.getAllDescriptions();

        for(Description description : descriptions){
            if(description.getDescription().equals(DESCRIPTION1))
                descriptionService.deleteDescription(description);
        }
    }

    @Test
    public void addDescriptionTest(){
        Description descriptionToAdd = new Description();
        descriptionToAdd.setDescription(DESCRIPTION1);
        descriptionService.addDescription(descriptionToAdd);
        Description addedDescription = descriptionService.getDescriptionByContents(DESCRIPTION1);

        Assert.assertEquals(DESCRIPTION1,addedDescription.getDescription());
    }

    @Test
    public void updateDescriptionTest(){
        Description descriptionToAdd = new Description();
        descriptionToAdd.setDescription(DESCRIPTION1);
        descriptionService.addDescription(descriptionToAdd);

        descriptionToAdd.setDescription("New description");
        descriptionService.updateDescription(descriptionToAdd);
        Description addedDescription = descriptionService.getDescriptionByContents("New description");

        assertEquals("New description",addedDescription.getDescription());
    }

    @Test
    public void addDescriptionToDogTest(){
        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG1_NAME);
        dogToAdd.setVaccinated(DOG1_IS_VACCINATED);
        dogToAdd.setWeight(DOG1_WEIGHT);
        dogToAdd.setSex(DOG1_SEX);
        dogToAdd.setDateOfBirth(DOG1_DATE);

        Description descriptionToAdd = new Description();
        descriptionToAdd.setDescription(DESCRIPTION1);
        descriptionToAdd.setDog(dogToAdd);
        dogToAdd.setDescription(descriptionToAdd);

        dogService.addDog(dogToAdd);
        descriptionService.addDescription(descriptionToAdd);

        Dog addedDog = dogService.getDogByName(DOG1_NAME);
        Description dogsDescription = addedDog.getDescription();

        assertEquals(DOG1_NAME,addedDog.getName());
        assertEquals(DOG1_WEIGHT,addedDog.getWeight());
        assertEquals(DOG1_SEX,addedDog.getSex());
        assertEquals(DOG1_IS_VACCINATED,addedDog.getVaccinated());
        assertEquals(DOG1_DATE,addedDog.getDateOfBirth());
        assertEquals(DESCRIPTION1,dogsDescription.getDescription());
        assertEquals(addedDog,dogsDescription.getDog());
    }

    @Test
    public void deleteDescriptionTest(){
        Description descriptionToRemove = new Description();
        descriptionToRemove.setDescription(DESCRIPTION1);

        Dog dogToAdd = new Dog();
        dogToAdd.setName(DOG1_NAME);
        dogToAdd.setVaccinated(DOG1_IS_VACCINATED);
        dogToAdd.setWeight(DOG1_WEIGHT);
        dogToAdd.setSex(DOG1_SEX);
        dogToAdd.setDescription(descriptionToRemove);
        dogToAdd.setDateOfBirth(DOG1_DATE);

        descriptionToRemove.setDog(dogToAdd);

        dogService.addDog(dogToAdd);
        descriptionService.addDescription(descriptionToRemove);

        descriptionService.deleteDescription(descriptionService.getDescriptionByContents(DESCRIPTION1));

        Dog addedDog = dogService.getDogByName(DOG1_NAME);

        assertNull(addedDog.getDescription());

    }

}
