package ug.dbinkus.jdbcdemo.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dog {

    private long id;
    private String name;
    private boolean isVaccinated;
    private double weight;
    private char sex;
    private Date dateOfBirth;

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth) + '\'' +
                ", isVaccinated=" + isVaccinated +
                ", weight=" + weight +
                ", sex=" + sex +
                "}\n";
    }

    public Dog(String name, String dateOfBirth, boolean isVaccinated, double weight, char sex) {
        this.name = name;
        this.isVaccinated = isVaccinated;
        this.weight = weight;
        this.sex = sex;
        try {
            this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        id = 0;
    }

    public Dog(long id, String name, String dateOfBirth, boolean isVaccinated, double weight, char sex){
        this(name,dateOfBirth,isVaccinated,weight,sex);
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.dateOfBirth);
    }

    public void setDateOfBirth(String dateOfBirth) {
        try {
            this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        isVaccinated = vaccinated;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }
}
