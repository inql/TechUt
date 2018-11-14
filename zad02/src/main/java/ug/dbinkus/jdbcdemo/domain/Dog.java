package ug.dbinkus.jdbcdemo.domain;

public class Dog {

    private static long counter = 0;
    private long id;
    private String name;
    private String dateOfBirth;
    private boolean isVaccinated;
    private double weight;
    private char sex;

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", isVaccinated=" + isVaccinated +
                ", weight=" + weight +
                ", sex=" + sex +
                "}\n";
    }

    public Dog(String name, String dateOfBirth, boolean isVaccinated, double weight, char sex) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.isVaccinated = isVaccinated;
        this.weight = weight;
        this.sex = sex;
        id = counter++;
    }

    public Dog(long id, String name, String dateOfBirth, boolean isVaccinated, double weight, char sex){
        this(name,dateOfBirth,isVaccinated,weight,sex);
        this.id = id;
    }


    public long getId() {
        return id;
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
