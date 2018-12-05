package com.example.shdemo.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "dog.getAll",query = "Select d from Dog d"),
                @NamedQuery(name = "dog.getByName", query = "Select d from Dog d where d.name = :name")
        }
)
public class Dog {

    private Long id;
    private String name;
    private Boolean isVaccinated;
    private Double weight;
    private Character sex;
    private Date dateOfBirth;
    private List<Toy> toyList = new ArrayList<Toy>();
    private Description description;

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

    @Column(unique = true, nullable = false)
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Toy> getToyList() {
        return toyList;
    }

    public void setToyList(List<Toy> toyList) {
        this.toyList = toyList;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}
