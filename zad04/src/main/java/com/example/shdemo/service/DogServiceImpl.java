package com.example.shdemo.service;

import com.example.shdemo.domain.Dog;
import com.example.shdemo.domain.Owner;
import com.example.shdemo.domain.Toy;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public Dog getDogByName(String name) {
        return (Dog) sessionFactory.getCurrentSession().getNamedQuery("dog.getByName").setString("name",name).uniqueResult();
    }

    @Override
    public void updateDog(Dog dog) {
        sessionFactory.getCurrentSession().update(dog);
    }

    @Override
    public void deleteDog(Dog dog) {
        dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class,dog.getId());
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
