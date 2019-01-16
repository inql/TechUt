package com.dbinkus.dogs.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "owner.getAll",query = "Select o from Owner o"),
        @NamedQuery(name = "owner.getById",query = "Select o from Owner o where :id=o.id"),
        @NamedQuery(name = "owner.getByName",query = "Select o from Owner o where :firstName=o.firstName")
})
public class Owner {

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;

    private List<Dog> dogList = new ArrayList<Dog>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    public List<Dog> getDogList() {
        return dogList;
    }

    public void setDogList(List<Dog> dogList) {
        this.dogList = dogList;
    }
}
