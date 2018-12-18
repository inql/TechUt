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
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addDogToOwner(Long ownerId, Long dogId) {
        Owner owner = (Owner) sessionFactory.getCurrentSession().get(Owner.class,ownerId);
        Dog dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class,dogId);
        owner.getDogList().add(dog);
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
    public void updateOwner(Owner owner) {
        sessionFactory.getCurrentSession().update(owner);
    }

    @Override
    public void deleteOwner(Owner owner) {
        owner = (Owner) sessionFactory.getCurrentSession().get(Owner.class,owner.getId());
        for(Dog dog: owner.getDogList()){
            dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class,dog.getId());
            sessionFactory.getCurrentSession().delete(dog);
        }

        sessionFactory.getCurrentSession().delete(owner);



    }
