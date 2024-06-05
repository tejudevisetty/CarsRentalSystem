package com.example.carrental.model;

public class Car {
    private int carId;
    private String brand;
    private String model;
    private int year;
    private boolean available;

    // Constructors, getters, and setters
    public Car() {}

    public Car(int carId, String brand, String model, int year, boolean available) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.available = available;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void isAvailable(boolean available) {
        this.available = available;
    }
}
