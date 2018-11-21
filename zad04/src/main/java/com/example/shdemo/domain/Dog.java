package com.example.shdemo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Dog {

    private Long id;
    private String name;
    private Boolean isVaccinated;
    private Double weight;
    private Character sex;
    private Date dateOfBirth;

    

    @Override
    public String toString() {
        return "\nDog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth) + '\'' +
                ", isVaccinated=" + isVaccinated +
                ", weight=" + weight +
                ", sex=" + sex +
                "}";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        isVaccinated = vaccinated;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
