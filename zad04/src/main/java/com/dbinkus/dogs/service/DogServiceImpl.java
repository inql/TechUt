package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.Breed;
import com.dbinkus.dogs.domain.Dog;
import com.dbinkus.dogs.domain.Owner;
import com.dbinkus.dogs.domain.Toy;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class DogServiceImpl implements DogService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addDog(Dog dog) {
        dog.setId(null);
        sessionFactory.getCurrentSession().persist(dog);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dog> getAllDogs() {
        return sessionFactory.getCurrentSession().getNamedQuery("dog.getAll").list();
    }

    @Override
    public List getAllDogsWithBreed(Breed breed) {
        breed = (Breed) sessionFactory.getCurrentSession().get(Breed.class,breed.getId());
        if(breed!=null){
            return sessionFactory.getCurrentSession().getNamedQuery("dog.getByBreed").setLong("id",breed.getId()).list();
        }
        return null;
    }

    @Override
    public List getAllDogsWithToy(Toy toy) {
        List<Dog> dogs = getAllDogs();
        List<Dog> result = new ArrayList<>();
        for(Dog dog: dogs){
            if(dog.getToyList().contains(toy)){
                result.add(dog);
            }
        }
        return result;
    }

    @Override
    public Dog getDogByName(String name) {
        return (Dog) sessionFactory.getCurrentSession().getNamedQuery("dog.getByName").setString("name",name).uniqueResult();
    }

    @Override
    public void removeDogsBornBefore(Date date) {
        List<Dog> dogs = getAllDogs();
        for(Dog dog : dogs){
            if(dog.getDateOfBirth().before(date)){
                deleteDog(dog);
            }
        }
    }

    @Override
    public void removeDogsBornAfter(Date date){
        List<Dog> dogs = getAllDogs();
        for(Dog dog : dogs){
            if(dog.getDateOfBirth().after(date)){
                deleteDog(dog);
            }
        }
    }

    @Override
    public void updateDog(Dog dog) {
        sessionFactory.getCurrentSession().update(dog);
    }

    @Transactional
    @Override
    public void deleteDog(Dog dog) {
        dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class,dog.getId());
        List<Owner> owners = sessionFactory.getCurrentSession().getNamedQuery("owner.getAll").list();
        for (Owner owner :
                owners) {
            if(owner.getDogList().contains(dog)){
                owner.getDogList().remove(dog);
                sessionFactory.getCurrentSession().update(owner);
            }
        }
        sessionFactory.getCurrentSession().delete(dog);

    }

    @Override
    public void giveToy(Dog dog, Toy toy) {
        dog = (Dog)sessionFactory.getCurrentSession().get(Dog.class,dog.getId());
        toy = (Toy)sessionFactory.getCurrentSession().get(Toy.class,toy.getId());

        if(dog!=null && toy!=null){
            List<Toy> dogsToys = dog.getToyList();
            if(!dogsToys.contains(toy)){
                dogsToys.add(toy);
            }
            dog.setToyList(dogsToys);


            updateDog(dog);
            sessionFactory.getCurrentSession().update(toy);
        }

    }

    @Override
    public void removeToy(Dog dog, Toy toy) {
        dog = (Dog)sessionFactory.getCurrentSession().get(Dog.class,dog.getId());
        toy = (Toy)sessionFactory.getCurrentSession().get(Toy.class,toy.getId());
        if(dog!=null && toy!=null) {
            List<Toy> dogsToys = dog.getToyList();
            dogsToys.remove(toy);
            dog.setToyList(dogsToys);
        }
        updateDog(dog);
        sessionFactory.getCurrentSession().update(toy);
    }
}
