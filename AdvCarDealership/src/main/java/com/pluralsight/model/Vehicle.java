package com.pluralsight.model;

public record Vehicle(int vin, int year, String make, String model, String vehicleType, String color, int odometer,
                      double price) {

    @Override
    public String toString() {
        return String.format(" %-6d - %-5d - %-10s - %-10s - %-10s - %-10s - %-8d - $%-,10.2f",
                vin,year,make,model,vehicleType,color,odometer,price);
    }
}
