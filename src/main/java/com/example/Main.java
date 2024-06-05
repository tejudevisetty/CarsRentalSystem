
package com.example;

import com.example.carrental.dao.CarDAO;
import com.example.carrental.dao.CustomerDAO;
import com.example.carrental.dao.RentalDAO;
import com.example.carrental.model.Car;
import com.example.carrental.model.Customer;
import com.example.carrental.model.Rental;
import com.example.carrental.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
	 private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(connection);
            CarDAO carDAO = new CarDAO(connection);
            RentalDAO rentalDAO = new RentalDAO(connection);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Select an operation:");
                System.out.println("1. View all Customers");
                System.out.println("2. Add Customer");
                System.out.println("3. Update Customer");
                System.out.println("4. Delete Customer");
                System.out.println("5. View all Cars:");
                System.out.println("6. Add Car");
                System.out.println("7. Update Car");
                System.out.println("8. Delete Car");
                System.out.println("9. Rent Car");
                System.out.println("10. View All Rental Details:");
                System.out.println("11. Exit");

                int choice;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // Clear input buffer
                    continue;
                }

                switch (choice) {
                	case 1:
                		getAllCustomers(customerDAO);
                		break;
                    case 2:
                        addCustomer(scanner, customerDAO);
                        break;
                    case 3:
                        updateCustomer(scanner, customerDAO);
                        break;
                    case 4:
                        deleteCustomer(scanner, customerDAO);
                        break;
                    case 5:
                        getAllCars(carDAO);
                        break;
                    case 6:
                        addCar(scanner, carDAO);
                        break;
                    case 7:
                        updateCar(scanner, carDAO);
                        break;
                    case 8:
                        deleteCar(scanner, carDAO);
                        break;
                    case 9:
                        rentCar(scanner, rentalDAO);
                        break;
                    case 10:
                        getAllRentals(rentalDAO);
                        break;
                    case 11:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 11.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void getAllCustomers(CustomerDAO customerDAO) throws SQLException {
        List<Customer> customers = customerDAO.getAllCustomers();
        System.out.println("Customers List:");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getCustomerId() + ", Name: " + customer.getName() +
                    ", Email: " + customer.getEmail() + ", PhoneNumber: " + customer.getPhoneNumber());
        }
    }


    private static void addCustomer(Scanner scanner, CustomerDAO customerDAO) {
    	System.out.println("Enter Customer Details:");
    	scanner.nextLine();
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("PhoneNumber: ");
        String phoneNumber = scanner.nextLine();

        Customer newCustomer = new Customer(0, name, email, phoneNumber);
        try {
            customerDAO.addCustomer(newCustomer);
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("PhoneNumber: " + phoneNumber);
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    private static void updateCustomer(Scanner scanner, CustomerDAO customerDAO) {
        System.out.println("Enter customer ID to update: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer

        try {
            Customer customerToUpdate = customerDAO.getCustomerById(customerId);
            if (customerToUpdate == null) {
                System.out.println("Customer with ID " + customerId + " not found.");
                return;
            }

            System.out.println("Enter new details for customer (press Enter to keep existing values):");
            System.out.println("Name: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                customerToUpdate.setName(name);
            }
            System.out.println("Email: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                customerToUpdate.setEmail(email);
            }
            System.out.println("PhoneNumber: ");
            String phoneNumber = scanner.nextLine();
            if (!phoneNumber.isEmpty()) {
                customerToUpdate.setPhone(phoneNumber);
            }

            customerDAO.updateCustomer(customerToUpdate);
            System.out.println("Customer updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    private static void deleteCustomer(Scanner scanner, CustomerDAO customerDAO) {
        System.out.println("Enter customer ID to delete: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer

        try {
            customerDAO.deleteCustomer(customerId);
            System.out.println("Customer deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }
    private static void getAllCars(CarDAO carDAO) throws SQLException {
        List<Car> cars = carDAO.getAllCars();
        System.out.println("Car List:");
        for (Car car : cars) {
            System.out.println("ID: " + car.getCarId() + ", Brand: " + car.getBrand() +
                    ", Model: " + car.getModel() + ", Year: " + car.getYear() +
                    ", Available: " + car.isAvailable());
        }
    }

    private static void addCar(Scanner scanner, CarDAO carDAO) {
        System.out.println("Enter car details");
        scanner.nextLine();
        System.out.println("Brand: ");
        String brand = scanner.nextLine();
        System.out.println("Model: ");
        String model = scanner.nextLine();
        System.out.println("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer
        System.out.print("Is the car available? (true/false): ");
        boolean isAvailable = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline character

        Car newCar = new Car(0, brand, model, year, true);
        try {
            carDAO.addCar(newCar);
            System.out.println("Car added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }
    }

    private static void updateCar(Scanner scanner, CarDAO carDAO) {
        System.out.print("Enter car ID to update: ");
        int carId = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer

        try {
            Car carToUpdate = carDAO.getCarById(carId);
            if (carToUpdate == null) {
                System.out.println("Car with ID " + carId + " not found.");
                return;
            }

            System.out.println("Enter new details for car (press Enter to keep existing values):");
            System.out.print("Brand: ");
            String brand = scanner.nextLine();
            if (!brand.isEmpty()) {
                carToUpdate.setBrand(brand);
            }
            System.out.print("Model: ");
            String model = scanner.nextLine();
            if (!model.isEmpty()) {
                carToUpdate.setModel(model);
            }
            System.out.print("Year: ");
            String yearInput = scanner.nextLine();
            if (!yearInput.isEmpty()) {
                int year = Integer.parseInt(yearInput);
                carToUpdate.setYear(year);
            }

            carDAO.updateCar(carToUpdate);
            System.out.println("Car updated successfully.");
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error updating car: " + e.getMessage());
        }
    }

    private static void deleteCar(Scanner scanner, CarDAO carDAO) {
        System.out.print("Enter car ID to delete: ");
        int carId = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer

        try {
            carDAO.deleteCar(carId);
            System.out.println("Car deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting car: " + e.getMessage());
        }
    }
    private static void rentCar(Scanner scanner, RentalDAO rentalDAO) {
        System.out.println("Enter booking details:");
        System.out.print("Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer
        System.out.print("Car ID: ");
        int carId = scanner.nextInt();
        scanner.nextLine(); // Clear input buffer
        System.out.print("Rental Date (yyyy-MM-dd): ");
        String rentalDateStr = scanner.nextLine();
        System.out.print("Return Date (yyyy-MM-dd): ");
        String returnDateStr = scanner.nextLine();

        try {
            Rental rental = new Rental(0, customerId, carId, dateFormat.parse(rentalDateStr), dateFormat.parse(returnDateStr));
            rentalDAO.rentCar(rental);
            System.out.println("Car rented successfully.");
        } catch (ParseException | SQLException e) {
            System.out.println("Error renting car: " + e.getMessage());
        }
    }

    private static void getAllRentals(RentalDAO rentalDAO) throws SQLException {
        List<Rental> rentals = rentalDAO.getAllRentals();
        System.out.println("Rental details are:");
        for (Rental rental : rentals) {
            System.out.println("ID: " + rental.getRentalId() + ", Customer ID: " + rental.getCustomerId() +
                    ", Car ID: " + rental.getCarId() + ", Rental Date: " + rental.getRentalDate() +
                    ", Return Date: " + rental.getReturnDate());
        }
    }
}

