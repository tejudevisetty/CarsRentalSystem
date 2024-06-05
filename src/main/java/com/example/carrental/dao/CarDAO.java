package com.example.carrental.dao;

import com.example.carrental.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private final Connection connection;

    public CarDAO(Connection connection) {
        this.connection = connection;
    }

    public void addCar(Car car) throws SQLException {
        String query = "INSERT INTO cars (brand, model, year, available) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setBoolean(4, car.isAvailable());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setCarId(generatedKeys.getInt(1));
            }
        }
    }

    public void updateCar(Car car) throws SQLException {
        String query = "UPDATE cars SET brand=?, model=?, year=?, available=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setBoolean(4, car.isAvailable());
            statement.setInt(5, car.getCarId());
            statement.executeUpdate();
        }
    }

    public void deleteCar(int carId) throws SQLException {
        String query = "DELETE FROM cars WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carId);
            statement.executeUpdate();
        }
    }

    public Car getCarById(int carId) throws SQLException {
        String query = "SELECT * FROM cars WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, carId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCarFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                cars.add(extractCarFromResultSet(resultSet));
            }
        }
        return cars;
    }

    private Car extractCarFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String brand = resultSet.getString("brand");
        String model = resultSet.getString("model");
        int year = resultSet.getInt("year");
        boolean available = resultSet.getBoolean("available");
        return new Car(id, brand, model, year, available);
    }
}
