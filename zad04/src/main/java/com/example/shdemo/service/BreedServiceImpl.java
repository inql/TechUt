package com.example.shdemo.service;

import com.example.shdemo.domain.Breed;
import com.example.shdemo.domain.Dog;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class BreedServiceImpl implements BreedService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void addBreed(Breed breed) {
        breed.setId(null);
        sessionFactory.getCurrentSession().persist(breed);
    }

    @Override
    public List getAllBreeds() {
        return sessionFactory.getCurrentSession().getNamedQuery("breed.getAll").list();
    }

    @Override
    public Breed getBreedByName(String name) {
        return (Breed) sessionFactory.getCurrentSession().getNamedQuery("breed.getByName").setString("name",name).uniqueResult();
    }

    @Override
    public void updateBreed(Breed breed) {
        sessionFactory.getCurrentSession().update(breed);
    }

    @Transactional
    @Override
    public void deleteBreed(Breed breed) {
        breed = (Breed) sessionFactory.getCurrentSession().get(Breed.class,breed.getId());
        List<Dog> dogs = sessionFactory.getCurrentSession().getNamedQuery("dog.getAll").list();
        for(Dog dog : dogs){
            if(dog.getBreed().equals(breed)){
                sessionFactory.getCurrentSession().delete(dog);
            }
        }
        sessionFactory.getCurrentSession().delete(breed);
    }
}
