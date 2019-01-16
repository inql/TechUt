package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.Dog;
import com.dbinkus.dogs.domain.Owner;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        Owner owner = getOwnerById(ownerId);
        Dog dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class, dogId);
        if(!dog.getHasOwner()){
            dog.setHasOwner(true);
            owner.getDogList().add(dog);
        }
    }

    @Transactional
    @Override
    public void deleteDogFromOwner(Long ownerId, Long dogId) {
        Owner owner = getOwnerById(ownerId);
        Dog dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class, dogId);
        if (owner.getDogList().contains(dog)){
            owner.getDogList().remove(dog);
            dog.setHasOwner(false);
        }
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
        return (Owner) sessionFactory.getCurrentSession().get(Owner.class,id);
    }

    @Override
    public Owner getOwnerByName(String name) {
        return (Owner) sessionFactory.getCurrentSession().getNamedQuery("owner.getByName").setString("firstName",name).uniqueResult();
    }

    @Override
    public void updateOwner(Owner owner) {
        sessionFactory.getCurrentSession().update(owner);
    }

    @Transactional
    @Override
    public void deleteOwner(Owner owner) {
        owner = (Owner) sessionFactory.getCurrentSession().get(Owner.class, owner.getId());
        for (Dog dog : owner.getDogList()) {
            dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class, dog.getId());
            dog.setHasOwner(false);
            sessionFactory.getCurrentSession().update(dog);
        }

        sessionFactory.getCurrentSession().delete(owner);
    }

    @Override
    public List getAllDogsFromOwner(Owner owner) {
        List<Dog> result = new ArrayList<Dog>();

        result.addAll(owner.getDogList());

        return result;

    }
}
