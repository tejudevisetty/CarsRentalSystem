package com.example.carrental.dao;

import com.example.carrental.model.Rental;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {
    private final Connection connection;

    public RentalDAO(Connection connection) {
        this.connection = connection;
    }

    public void rentCar(Rental rental) throws SQLException {
        String query = "INSERT INTO bookings (customer_id, car_id, rental_date, return_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, rental.getCustomerId());
            statement.setInt(2, rental.getCarId());
            statement.setDate(3, new Date(rental.getRentalDate().getTime()));
            statement.setDate(4, new Date(rental.getReturnDate().getTime()));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
            	rental.setRentalId(generatedKeys.getInt(1));
            }
        }
    }

    public void returnCar(int rentalDAO) throws SQLException {
        String query = "UPDATE bookings SET return_date=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new Date(System.currentTimeMillis())); // Set return date to current date
            statement.setInt(2, rentalDAO);
            statement.executeUpdate();
        }
    }

    public Rental getRentalById(int rentalId) throws SQLException {
        String query = "SELECT * FROM bookings WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, rentalId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractRentalFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Rental> getAllRentals() throws SQLException {
        List<Rental> rentals = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                rentals.add(extractRentalFromResultSet(resultSet));
            }
        }
        return rentals;
    }

   

	private Rental extractRentalFromResultSet(ResultSet resultSet) throws SQLException {
        int rental_id = resultSet.getInt("rental_id");
        int customerId = resultSet.getInt("customer_id");
        int carId = resultSet.getInt("car_id");
        Date rentalDate = resultSet.getDate("rental_date");
        Date returnDate = resultSet.getDate("return_date");
        return new Rental(rental_id, customerId, carId, rentalDate, returnDate);
    }
}




