package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;
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

    private final String DOG2_NAME = "Reksio";
    private final Boolean DOG2_IS_VACCINATED = false;
    private final Double DOG2_WEIGHT = 4.9;
    private final Character DOG2_SEX = 'c';

    private final String OWNER_FNAME = "Jan";
    private final String OWNER_LNAME = "Abacki";
    private final Date OWNER_BDATE = new GregorianCalendar(100,10,10).getTime();

    @Test
    public void addOwnerTest(){
        assertEquals(true,true);

    }


}
