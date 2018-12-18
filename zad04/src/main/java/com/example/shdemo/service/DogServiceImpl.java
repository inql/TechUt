package com.example.shdemo.service;

import com.example.shdemo.domain.Dog;
import com.example.shdemo.domain.Owner;
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
    public void deleteDog(Dog dog) {
        dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class,dog.getId());
        sessionFactory.getCurrentSession().delete(dog);

    }

    @Override
    public Long addOwner(Owner owner) {
        owner.setId(null);
        return (Long) sessionFactory.getCurrentSession().save(owner);
    }

    @Override
    public List getAllOwners() {
        return sessionFactory.getCurrentSession().getNamedQuery("owner.getAll").list();
    }

    @Override
    public Owner getOwnerById(Long id) {
        return (Owner) sessionFactory.getCurrentSession().getNamedQuery("owner.getById").setLong("id",id).uniqueResult();
    }

    @Override
    public void deleteOwner(Owner owner) {
        owner = (Owner) sessionFactory.getCurrentSession().get(Owner.class,owner.getId());
        for(Dog dog: owner.getDogList()){
            deleteDog(dog);
        }

        sessionFactory.getCurrentSession().delete(owner);


    }
}
