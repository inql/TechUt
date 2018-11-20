package ug.dbinkus.jdbcdemo.service;

import ug.dbinkus.jdbcdemo.SortingMode;
import ug.dbinkus.jdbcdemo.domain.Dog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DogServiceImpl implements DogService{

    private Connection connection;

    private String url = "jdbc:hsqldb:hsql://localhost/workdb";

    private String createTableDog = "CREATE TABLE Dog(id INTEGER IDENTITY, name varchar(30) UNIQUE, date_of_birth date, is_vaccinated boolean, weight double, sex varchar(1))";

    private Statement statement;

    private PreparedStatement getAllDogsPreparedStatement;
    private PreparedStatement addDogPreparedStatement;
    private PreparedStatement deleteAllDogsPreparedStatement;
    private PreparedStatement deleteDogPreparedStatement;
    private PreparedStatement updateDogPreparedStatement;

    public DogServiceImpl(){
        try{
           connection = DriverManager.getConnection(url);
           statement = connection.createStatement();

            ResultSet resultSet = connection.getMetaData().getTables(null,null,null,null);
            boolean tableExists = false;
            while (resultSet.next()){
                if("Dog".equalsIgnoreCase(resultSet.getString("table_name"))){
                    tableExists = true;
                    break;
                }
            }
            if(!tableExists){
                statement.executeUpdate(createTableDog);
            }

            addDogPreparedStatement = connection.prepareStatement("INSERT INTO Dog(name,date_of_birth,is_vaccinated,weight,sex) VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            deleteAllDogsPreparedStatement = connection.prepareStatement("DELETE FROM Dog");
            getAllDogsPreparedStatement = connection.prepareStatement("SELECT id,name,date_of_birth,is_vaccinated,weight,sex FROM DOG");
            deleteDogPreparedStatement = connection.prepareStatement("DELETE FROM Dog WHERE id = ?");
            updateDogPreparedStatement = connection.prepareStatement("UPDATE DOG SET name=?, date_of_birth=?, is_vaccinated " +
                    "=?, weight=?, sex=? WHERE id=?");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addDog(Dog dog) throws SQLException {
        addDogPreparedStatement.setString(1,dog.getName());
        addDogPreparedStatement.setString(2,dog.getDateOfBirth());
        addDogPreparedStatement.setBoolean(3,dog.isVaccinated());
        addDogPreparedStatement.setDouble(4,dog.getWeight());
        addDogPreparedStatement.setString(5, String.valueOf(dog.getSex()));
        int affectedRows = addDogPreparedStatement.executeUpdate();

        if(affectedRows==0){
            throw new SQLException("Creating dog failes, no rows affected.");
        }

        ResultSet generatedKeys = addDogPreparedStatement.getGeneratedKeys();
        if(generatedKeys.next()){
            dog.setId(generatedKeys.getLong(1));
        }
        else {
            throw new SQLException("Creating dog failed, no ID obtained");
        }
    }

    //todo: implement method deleteDog
    public void deleteDog(Dog dog) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT id FROM Dog WHERE name="+dog.getName());
        String deleteDogSql = "DELETE FROM Dog WHERE id="+resultSet.getLong("id");
        statement.executeUpdate(deleteDogSql);
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void clearDogs() {
        try{
            deleteAllDogsPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Dog> getAllDogs() throws SQLException {
        List<Dog> dogs = new ArrayList<>();
        ResultSet dogsResultSet = getAllDogsPreparedStatement.executeQuery();
        while(dogsResultSet.next()){
            Dog dog = new Dog(dogsResultSet.getLong(1), dogsResultSet.getString("name"),
                    dogsResultSet.getString("date_of_birth"),
                    dogsResultSet.getBoolean("is_vaccinated"),
                    dogsResultSet.getDouble("weight"),
                    dogsResultSet.getString("sex").charAt(0));
            dogs.add(dog);

        }
        return dogs;

    }

    @Override
    public void addAllDogs(List<Dog> dogList) {

        try{
            connection.setAutoCommit(false);
            for(Dog dog : dogList){
                addDog(dog);
            }
            connection.commit();
            connection.setAutoCommit(true);

        }catch (SQLException e){
            try{
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }


    }

    @Override
    public Dog findDogById(long id) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Dog WHERE id="+id);
            if(resultSet.next())
                return getDogById(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Dog findDogByName(String name) {
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Dog WHERE name="+name);
            if(resultSet.next())
                return getDogById(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Dog removeDog(Dog dog) {
        try{
            deleteDogPreparedStatement.setString(1,String.valueOf(dog.getId()));
            deleteDogPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dog;
    }

    @Override
    public Dog updateDog(Dog dog) {
        try{
            //update in database
            updateDogPreparedStatement.setLong(6,dog.getId());
            updateDogPreparedStatement.setString(1,dog.getName());
            updateDogPreparedStatement.setString(2,dog.getDateOfBirth());
            updateDogPreparedStatement.setBoolean(3,dog.isVaccinated());
            updateDogPreparedStatement.setDouble(4,dog.getWeight());
            updateDogPreparedStatement.setString(5,String.valueOf(dog.getSex()));
            updateDogPreparedStatement.executeUpdate();
            connection.commit();
            //if successful, find existing dog in application and change his values
            return findDogById(dog.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Dog> getAllVaccinatedDogs(String sortingColumn, SortingMode sortingMode) {
        List<Dog> vaccinatedDogs = new ArrayList<>();
        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Dog WHERE is_vaccinated = true ORDER BY "+sortingColumn+ " "+sortingMode);
            while(resultSet.next()){
                vaccinatedDogs.add(getDogById(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return vaccinatedDogs;
    }

    private Dog getDogById(ResultSet resultSetDog) throws SQLException {
        return new Dog(resultSetDog.getLong(1),resultSetDog.getString(2),
                    resultSetDog.getString(3),resultSetDog.getBoolean(4),
                resultSetDog.getDouble(5),resultSetDog.getString(6).charAt(0));

    }

}
