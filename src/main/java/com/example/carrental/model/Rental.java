package com.example.carrental.model;

import java.util.Date;

public class Rental {
    private int rentalId;
    private int customerId;
    private int carId;
    private Date rentalDate;
    private Date returnDate;

    // Constructors, getters, and setters
    public Rental() {}

    public Rental(int rentalId, int customerId, int carId, Date rentalDate, Date returnDate) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.carId = carId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }


	public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}