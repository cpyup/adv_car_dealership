package com.pluralsight.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Dealership} class represents a car dealership that manages a
 * collection of vehicles in its inventory.
 * <p>
 * This class provides methods to add, remove, and filter vehicles based
 * on various criteria such as price, make, model, year, color, mileage,
 * and type.
 * </p>
 */
public class Dealership {
    private final String name;
    private final String address;
    private final String phone;
    private final List<Vehicle> inventory;

    /**
     * Constructs a {@code Dealership} with the specified name, address,
     * and phone number.
     *
     * @param name    the name of the dealership
     * @param address the address of the dealership
     * @param phone   the phone number of the dealership
     */
    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    /**
     * Retrieves a list of vehicles within the specified price range.
     *
     * @param min the minimum price (inclusive) or {@code null} for no minimum
     * @param max the maximum price (inclusive) or {@code null} for no maximum
     * @return a list of vehicles within the specified price range
     */
    public List<Vehicle> getVehiclesByPrice(Double min, Double max){
        return inventory.stream().filter(vehicle -> (min == null || vehicle.price() >= min) && (max == null || vehicle.price() <= max)).toList();
    }

    /**
     * Retrieves a list of vehicles that match the specified make and model.
     *
     * @param make  the make of the vehicle
     * @param model the model of the vehicle (empty string for no model filter)
     * @return a list of vehicles that match the specified make and model
     */
    public List<Vehicle> getVehiclesByMakeModel(String make, String model){
        return inventory.stream().filter(vehicle -> (vehicle.make().equalsIgnoreCase(make) && vehicle.model().equalsIgnoreCase(model)) ||
                (vehicle.make().equalsIgnoreCase(make) && model.equals(""))).toList();
    }

    /**
     * Retrieves a list of vehicles within the specified year range.
     *
     * @param min the minimum year (inclusive) or {@code null} for no minimum
     * @param max the maximum year (inclusive) or {@code null} for no maximum
     * @return a list of vehicles within the specified year range
     */
    public List<Vehicle> getVehiclesByYear(Integer min, Integer max){
        return  inventory.stream().filter(vehicle -> (max == null || vehicle.year() <= max)&&(min == null || vehicle.year() >= min)).toList();
    }

    /**
     * Retrieves a list of vehicles that match the specified color.
     *
     * @param color the color of the vehicle
     * @return a list of vehicles that match the specified color
     */
    public List<Vehicle> getVehiclesByColor(String color){
        return inventory.stream().filter(vehicle -> vehicle.color().equalsIgnoreCase(color)).toList();
    }

    /**
     * Retrieves a list of vehicles within the specified mileage range.
     *
     * @param min the minimum mileage (inclusive) or {@code null} for no minimum
     * @param max the maximum mileage (inclusive) or {@code null} for no maximum
     * @return a list of vehicles within the specified mileage range
     */
    public List<Vehicle> getVehiclesByMileage(Integer min, Integer max){
        return inventory.stream().filter(vehicle ->(max == null || vehicle.odometer() <= max) && (min == null || vehicle.odometer() >= min)).toList();
    }

    /**
     * Retrieves a list of vehicles of the specified type.
     *
     * @param type the type of the vehicle
     * @return a list of vehicles that match the specified type
     */
    public List<Vehicle> getVehiclesByType(String type){
        return inventory.stream().filter(vehicle -> vehicle.vehicleType().equalsIgnoreCase(type)).toList();
    }

    /**
     * Retrieves a list of vehicles that match the specified VIN.
     *
     * @param vin the VIN of the vehicle
     * @return a list of vehicles that match the specified VIN
     */
    public List<Vehicle> getVehiclesByVin(int vin){
        return inventory.stream().filter(vehicle -> vehicle.vin()==vin).toList();
    }

    /**
     * Retrieves a list of all vehicles in the dealership's inventory.
     *
     * @return a list of all vehicles
     */
    public List<Vehicle> getAllVehicles(){
        return inventory;
    }

    /**
     * Adds a vehicle to the dealership's inventory.
     *
     * @param vehicle the vehicle to be added
     */
    public void addVehicle(Vehicle vehicle){
        inventory.add(vehicle);
    }

    /**
     * Removes a vehicle from the dealership's inventory.
     *
     * @param vehicle the vehicle to be removed
     */
    public void removeVehicle(Vehicle vehicle){
        inventory.remove(vehicle);
    }

    /**
     * Returns a string representation of the dealership, including its name,
     * address, phone number, and details of all vehicles in its inventory.
     *
     * @return a string representation of the dealership
     */
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append(String.format("%s|%s|%s",name,address,phone));
        for(Vehicle vehicle : inventory){
            output.append(String.format("%n%d|%d|%s|%s|%s|%s|%d|%.2f",
                    vehicle.vin(),vehicle.year(),vehicle.make(),vehicle.model(),vehicle.vehicleType(),
                    vehicle.color(),vehicle.odometer(),vehicle.price()));
        }
        return output.toString();
    }
}
