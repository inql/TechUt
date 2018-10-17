package com.example.jdbcdemo.domain;

public class Dog {

    private String name;
    private String dateOfBirth;
    private boolean isVaccinated;
    private double weight;
    private char sex;

    public Dog(String name, String dateOfBirth, boolean isVaccinated, double weight, char sex) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.isVaccinated = isVaccinated;
        this.weight = weight;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
