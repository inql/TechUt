package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import com.example.shdemo.domain.Description;
import org.junit.Assert;
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
public class DescriptionServiceTest {

    @Autowired
    DescriptionService descriptionService;
    @Autowired
    DogService dogService;

    private final String DESCRIPTION1 = "To jest opis";

    private final String DOG1_NAME = "Burek";
    private final Boolean DOG1_IS_VACCINATED = true;
    private final Double DOG1_WEIGHT = 23.5;
    private final Character DOG1_SEX = 'c';

    @Test
    public void addDescriptionTest(){
        List<Description> descriptions = descriptionService.getAllDescriptions();

        for(Description description : descriptions){
            if(description.getDescription().equals(DESCRIPTION1))
                descriptionService.deleteDescription(description);
        }

        Description descriptionToAdd = new Description();
        descriptionToAdd.setDescription(DESCRIPTION1);
        descriptionService.addDescription(descriptionToAdd);
        Description addedDescription = descriptionService.getDescriptionByContents(DESCRIPTION1);

        Assert.assertEquals(DESCRIPTION1,addedDescription.getDescription());
    }

    @Test
    public void updateDescriptionTest(){
        List<Description> descriptions = descriptionService.getAllDescriptions();

        for(Description description : descriptions){
            if(description.getDescription().equals(DESCRIPTION1))
                descriptionService.deleteDescription(description);
        }
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

    }

}
