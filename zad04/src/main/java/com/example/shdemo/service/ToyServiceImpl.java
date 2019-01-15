package com.example.shdemo.service;

import com.example.shdemo.domain.Dog;
import com.example.shdemo.domain.Toy;
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
    public void updateToy(Toy toy) {
        sessionFactory.getCurrentSession().update(toy);
    }

    @Override
    public void deleteToy(Toy toy) {
        toy = (Toy) sessionFactory.getCurrentSession().get(Toy.class,toy.getId());
        if(toy!=null){
            sessionFactory.getCurrentSession().delete(toy);
        }
    }
}
