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

    public void addDog(Dog dog) {
        try {
            addDogPreparedStatement.setString(1,dog.getName());
            addDogPreparedStatement.setString(2,dog.getDateOfBirth());
            addDogPreparedStatement.setBoolean(3,dog.isVaccinated());
            addDogPreparedStatement.setDouble(4,dog.getWeight());
            addDogPreparedStatement.setString(5, String.valueOf(dog.getSex()));
            int affectedRows = addDogPreparedStatement.executeUpdate();

            if(affectedRows==0){
                throw new SQLException("Creating dog failed, no rows affected.");
            }

            ResultSet generatedKeys = addDogPreparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                dog.setId(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating dog failed, no ID obtained");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
    public List<Dog> getAllDogs() {
        List<Dog> dogs = new ArrayList<>();
        ResultSet dogsResultSet = null;
        try {
            dogsResultSet = getAllDogsPreparedStatement.executeQuery();
            while(dogsResultSet.next()){
                Dog dog = new Dog(dogsResultSet.getLong(1), dogsResultSet.getString("name"),
                        dogsResultSet.getString("date_of_birth"),
                        dogsResultSet.getBoolean("is_vaccinated"),
                        dogsResultSet.getDouble("weight"),
                        dogsResultSet.getString("sex").charAt(0));
                dogs.add(dog);

            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Dog where name = \'"+name+"\'");
            if(resultSet.next())
                return getDogById(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Dog removeDog(Dog dog) {
        if(dog != null){
            try{
                deleteDogPreparedStatement.setString(1,String.valueOf(dog.getId()));
                deleteDogPreparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return dog;
        }
        return null;
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
            //if successful, performSelect existing dog in application and change his values
            return findDogById(dog.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Dog> getAllVaccinatedDogs(String sortingColumn, SortingMode sortingMode) {
        List<Dog> vaccinatedDogs = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, vaccinatedDogs, "SELECT * FROM Dog WHERE is_vaccinated = true ORDER BY ");
        return vaccinatedDogs;
    }

    @Override
    public List<Dog> getAllNonVaccinatedDogs(String sortingColumn, SortingMode sortingMode) {
        List<Dog> nonVaccinatedDogs = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, nonVaccinatedDogs, "SELECT * FROM Dog WHERE is_vaccinated = false ORDER BY ");
        return nonVaccinatedDogs;    }

    @Override
    public List<Dog> getAllMaleDogs(String sortingColumn, SortingMode sortingMode) {
        List<Dog> maleDogs = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, maleDogs, "SELECT * FROM Dog WHERE sex = 'm' ORDER BY ");
        return maleDogs;
    }

    @Override
    public List<Dog> getAllFemaleDogs(String sortingColumn, SortingMode sortingMode) {
        List<Dog> femaleDogs = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, femaleDogs, "SELECT * FROM Dog WHERE sex = 'f' ORDER BY ");
        return femaleDogs;
    }

    @Override
    public List<Dog> getAllDogsHeavierThan(double minWeight, String sortingColumn, SortingMode sortingMode) {
        List<Dog> dogsHeavierThan = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, dogsHeavierThan, "SELECT * FROM Dog WHERE weight >"+minWeight+" ORDER BY ");
        return dogsHeavierThan;
    }

    @Override
    public List<Dog> getAllDogsLighterThan(double maxWeight, String sortingColumn, SortingMode sortingMode) {
        List<Dog> dogsLighterThan = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, dogsLighterThan, "SELECT * FROM Dog WHERE weight <"+maxWeight+" ORDER BY ");
        return dogsLighterThan;
    }

    @Override
    public List<Dog> getAllDogsWeightInRange(double minWeight, double maxWeight, String sortingColumn, SortingMode sortingMode) {
        List<Dog> dogsWeightInRange = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, dogsWeightInRange, "SELECT * FROM Dog WHERE weight BETWEEN "+minWeight+"AND "+maxWeight+ " ORDER BY ");
        return dogsWeightInRange;
    }

    @Override
    public List<Dog> getAllDogsBornBefore(String date, String sortingColumn, SortingMode sortingMode) {
        List<Dog> dogsBornBefore = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, dogsBornBefore, "SELECT * FROM Dog WHERE date_of_birth <\'"+date+"\' ORDER BY ");
        return dogsBornBefore;
    }

    @Override
    public List<Dog> getAllDogsBornAfter(String date, String sortingColumn, SortingMode sortingMode) {
        List<Dog> dogsBornAfter = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, dogsBornAfter, "SELECT * FROM Dog WHERE date_of_birth >\'"+date+"\' ORDER BY ");
        return dogsBornAfter;
    }

    @Override
    public List<Dog> getAllDogsBornInRange(String from, String to, String sortingColumn, SortingMode sortingMode) {
        List<Dog> dogsBornInRange = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, dogsBornInRange, "SELECT * FROM Dog WHERE date_of_birth BETWEEN \'"+from+"\' AND \'"+to+"\' ORDER BY ");
        return dogsBornInRange;
    }

    @Override
    public List<Dog> getAllDogsWithNameLike(String namePattern, String sortingColumn, SortingMode sortingMode) {
        List<Dog> dogsWithNameLike = new ArrayList<>();
        performSelect(sortingColumn, sortingMode, dogsWithNameLike, "SELECT * FROM Dog WHERE name LIKE \'"+namePattern+"\' ORDER BY ");
        return dogsWithNameLike;
    }


    private Dog getDogById(ResultSet resultSetDog) throws SQLException {
        return new Dog(resultSetDog.getLong(1),resultSetDog.getString(2),
                    resultSetDog.getString(3),resultSetDog.getBoolean(4),
                resultSetDog.getDouble(5),resultSetDog.getString(6).charAt(0));
    }

    private void performSelect(String sortingColumn, SortingMode sortingMode, List<Dog> vaccinatedDogs, String s) {
        try{
            ResultSet resultSet = statement.executeQuery(s +sortingColumn+ " "+sortingMode);
            while(resultSet.next()){
                vaccinatedDogs.add(getDogById(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
