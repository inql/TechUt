package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import com.example.shdemo.domain.Dog;
import com.example.shdemo.domain.Toy;
import org.junit.Before;
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
public class ToyServiceTest {

    @Autowired
    ToyService toyService;

    private final String TOY_NAME1 = "Bone";
    private final String TOY_DESC1 = "Description1";

    @Before
    public void setUp(){
        List<Toy> allToys = toyService.getAllToys();

        for (Toy toy: allToys){
            if(toy.getName().equals(TOY_NAME1)){
                toyService.deleteToy(toy);
            }
        }
    }

    @Test
    public void addToyTest(){
        Toy toyToAdd = new Toy();
        toyToAdd.setName(TOY_NAME1);
        toyToAdd.setDescription(TOY_DESC1);

        toyService.addToy(toyToAdd);

        List<Toy> allToys = toyService.getAllToys();
        assertEquals(1,allToys.size());


        Toy addedToy = allToys.get(0);

        assertNotNull(addedToy);

        assertEquals(TOY_NAME1,addedToy.getName());
        assertEquals(TOY_DESC1,addedToy.getDescription());
    }

    @Test
    public void updateToyTest(){
        Toy toyToAdd = new Toy();
        toyToAdd.setName(TOY_NAME1);
        toyToAdd.setDescription(TOY_DESC1);

        toyService.addToy(toyToAdd);

        Toy addedToy = toyService.getToyByName(TOY_NAME1);
        addedToy.setName("Ball");
        toyService.updateToy(addedToy);

        List<Toy> allToys = toyService.getAllToys();
        assertEquals(1,allToys.size());

        Toy updatedToy = allToys.get(0);

        assertNotNull(updatedToy);

        assertEquals("Ball",updatedToy.getName());
        assertEquals(TOY_DESC1,updatedToy.getDescription());
    }



}
