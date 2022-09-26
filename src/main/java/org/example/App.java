package org.example;

import org.example.model.City;
import org.example.model.Country;
import org.example.model.MyException;
import org.example.model.People;
import org.example.util.Db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

//        Db.connection();

//        createTablePeople();

//        insertTablePeople(1, "Садыр", "Жапаров", 53, "Президент");
//        insertTablePeople(2, "Айбек", "Джунушалиев", 47, "Мэр");
//
//        insertTablePeople(3, "Владимир", "Путин", 69, "Президент");
//        insertTablePeople(4, "Сергей", "Собянин", 64, "Мэр");
//
//        insertTablePeople(5, "Джо", "Байден", 80, "Президент");
//        insertTablePeople(6, "Мюриэл", "Баузер", 50, "Мэр");

        List<People> allPeople = getAllPeople();
        System.out.println(allPeople);


//        createTableCountry();

//        insertTableCountry(1, "Кыргызстан", 996, 6700000, 1);
//        insertTableCountry(2, "Россия", 7, 147182123, 3);
//        insertTableCountry(3, "США", 1, 301693000, 5);

        List<Country> allCountry = getAllCountry();
        System.out.println(allCountry);

//        createTableCity();

//        insertTableCity(1, "Бишкек", 1071261, 2, 1);
//        insertTableCity(2, "Москва", 12635466, 4, 2);
//        insertTableCity(3, "Вашингтон", 689545, 6, 3);

        List<City> allCities = getAllCities();
        System.out.println(allCities);

        System.out.println(getByIdCity(2));


    }

    public static void createTablePeople() {

        String sql = "CREATE TABLE IF NOT EXISTS people (" +
                "    id INT PRIMARY KEY," +
                "    name VARCHAR (30) NOT NULL," +
                "    surname VARCHAR (30) NOT NULL," +
                "    age INT NOT NULL," +
                "    position VARCHAR (30)" +
                ");";

        try (Connection conn = Db.connection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("table success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertTablePeople(int id, String name, String surname, int age, String position) {

        String sql = "INSERT INTO people (id, name, surname, age, position)" +
                "VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = Db.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, position);
            preparedStatement.executeUpdate();
            System.out.println("Succesfully added  " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<People> getAllPeople() {

        String sql = "SELECT * FROM people";
        List<People> people = new ArrayList<>();

        try (Connection connection = Db.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                People people1 = new People();
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                String position = resultSet.getString("position");
                System.out.println(id + " " + name + " " + surname + " " + age + " " + position);
                people1.setId(id);
                people1.setName(name);
                people1.setSurname(surname);
                people1.setAge(age);
                people1.setPosition(position);
                people.add(people1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return people;
    }

    public static void createTableCountry() {

        String sql = "CREATE TABLE IF NOT EXISTS country (" +
                "    id INT PRIMARY KEY," +
                "    name VARCHAR (30) NOT NULL," +
                "    number INT UNIQUE NOT NULL," +
                "    population BIGINT NOT NULL," +
                "    people_id INT REFERENCES people(id)" +
                ");";

        try (Connection connection = Db.connection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("table success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertTableCountry(int id, String name, int number, int population, int poople_id) {

        String sql = "INSERT INTO country (id, name, number, population, poople_id)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = Db.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, number);
            preparedStatement.setInt(4, population);
            preparedStatement.setInt(5, poople_id);
            preparedStatement.executeUpdate();
            System.out.println("Succesfully added  " + name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Country> getAllCountry() {

        String sql = "SELECT * FROM country";
        List<Country> countries = new ArrayList<>();

        try (Connection connection = Db.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Country country = new Country();
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int number = resultSet.getInt("number");
                int population = resultSet.getInt("population");
                int peopleId = resultSet.getInt("poople_id");
                System.out.println(id + " " + name + " " + number + " " + population + " " + peopleId);
                country.setId(id);
                country.setName(name);
                country.setNumber(number);
                country.setPopulation(population);
                country.setPeopleId(peopleId);
                countries.add(country);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return countries;
    }

    public static void createTableCity() {

        String sql = "CREATE TABLE IF NOT EXISTS city (" +
                "    id INT PRIMARY KEY," +
                "    name VARCHAR (30) NOT NULL," +
                "    population INT NOT NULL," +
                "    people_id INT REFERENCES people(id)," +
                "    country_id INT REFERENCES country(id)" +
                ");";

        try (Connection connection = Db.connection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("table success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertTableCity(int id, String name, int population, int people_id, int country_id) {

        String sql = "INSERT INTO city (id, name, population, people_id, country_id)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = Db.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, population);
            preparedStatement.setInt(4, people_id);
            preparedStatement.setInt(5, country_id);
            preparedStatement.executeUpdate();
            System.out.println("Succesfully added  " + name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<City> getAllCities() {

        String sql = "SELECT * FROM city";
        List<City> cities = new ArrayList<>();

        try (Connection connection = Db.connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                City city = new City();
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int population = resultSet.getInt("population");
                int people_id = resultSet.getInt("people_id");
                int country_id = resultSet.getInt("country_id");
                System.out.println(id + " " + name + " " + population + " " + people_id + " " + country_id);
                city.setId(id);
                city.setName(name);
                city.setPopulation(population);
                city.setPeopleId(people_id);
                city.setCountryId(country_id);
                cities.add(city);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cities;
    }

    public static City getByIdCity(int id) {

        City city = null;

        for (City c : getAllCities()) {
            if (c.getId() == id) {
                city = c;
            }
        }
        if (city == null) {
            throw new MyException ("Такого id нет");
        } else {
            return city;
        }
    }


}

