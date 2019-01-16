package com.dbinkus.dogs.service;

import com.dbinkus.dogs.domain.Dog;
import com.dbinkus.dogs.domain.Description;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class DescriptionServiceImpl implements DescriptionService{

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List getAllDescriptions() {
        return sessionFactory.getCurrentSession().getNamedQuery("description.getAll").list();
    }

    @Override
    public void addDescriptionToDog(Long descriptionId, Long dogId) {
        Description description = getDescriptionById(descriptionId);
        Dog dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class, dogId);
        if(dog.getDescription()!=null){
            dog.setDescription(description);
        }
    }

    @Override
    public Long addDescription(Description description) {
        description.setId(null);
        return (Long) sessionFactory.getCurrentSession().save(description);
    }

    @Override
    public Description getDescriptionById(Long descriptionId) {
        return (Description) sessionFactory.getCurrentSession().get(Description.class,descriptionId);
    }

    @Override
    public Description getDescriptionByContents(String content) {
        return (Description) sessionFactory.getCurrentSession().getNamedQuery("description.getByContents").setString("description",content).uniqueResult();
    }

    @Override
    public void updateDescription(Description description) {
        sessionFactory.getCurrentSession().update(description);
    }

    @Transactional
    @Override
    public void deleteDescription(Description description) {
        description = (Description) this.getDescriptionById(description.getId());
        if(description.getDog().getId()!=null){
            Dog dog = (Dog) sessionFactory.getCurrentSession().get(Dog.class, description.getDog().getId());
            dog.setDescription(null);
            sessionFactory.getCurrentSession().update(dog);
        }
        sessionFactory.getCurrentSession().delete(description);
    }
}
