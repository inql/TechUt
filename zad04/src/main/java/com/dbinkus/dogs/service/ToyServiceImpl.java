package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.Dog;
import com.dbinkus.dogs.domain.Toy;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ToyServiceImpl implements ToyService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Long addToy(Toy toy) {
        toy.setId(null);
        return (Long)sessionFactory.getCurrentSession().save(toy);
    }

    @Override
    public List getAllToys() {
        return sessionFactory.getCurrentSession().getNamedQuery("toy.getAll").list();
    }

    @Override
    public Toy getToyByName(String name) {
        return (Toy) sessionFactory.getCurrentSession().getNamedQuery("toy.getByName").setString("name",name).uniqueResult();
    }

    @Override
    public Long countToysOfTheSameType(Toy toy){
        toy = (Toy) sessionFactory.getCurrentSession().get(Toy.class,toy.getId());
        Long result = 0L;
        if(toy!=null){
            List<Dog> dogs = sessionFactory.getCurrentSession().getNamedQuery("dog.getAll").list();
            for(Dog dog: dogs){
                if(dog.getToyList().contains(toy)){
                    result++;
                }
            }
        }
        return result;
    }

    @Override
    public void updateToy(Toy toy) {
        sessionFactory.getCurrentSession().update(toy);
    }

    @Transactional
    @Override
    public void deleteToy(Toy toy) {
        toy = (Toy) sessionFactory.getCurrentSession().get(Toy.class,toy.getId());
        if(toy!=null){
            List<Dog> dogs = sessionFactory.getCurrentSession().getNamedQuery("dog.getAll").list();
            for(Dog dog : dogs){
                if(dog.getToyList().contains(toy)){
                    dog.getToyList().remove(toy);
                    sessionFactory.getCurrentSession().update(dog);
                }
            }
            sessionFactory.getCurrentSession().delete(toy);
        }
    }
}
