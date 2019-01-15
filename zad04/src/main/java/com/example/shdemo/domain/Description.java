package com.example.shdemo.domain;


import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "description.getAll", query = "Select o from Description o"),
        @NamedQuery(name = "description.getById", query = "Select o from Description o where :id=o.id"),
        @NamedQuery(name = "description.getByContents", query = "Select o from Description o where :description=o.description")
})
public class Description {

    private Long id;
    private String description;
    private Dog dog;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription(){return description;}

    public void setDescription(String description){this.description = description;}

    @OneToOne(fetch = FetchType.LAZY)
    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
