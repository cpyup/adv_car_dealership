package com.pluralsight.persistence;

import com.pluralsight.model.Vehicle;

public abstract class FileManager {

    /**
     * Parses an array of string values into a {@code Vehicle} object.
     *
     * @param values the array of string values representing vehicle details.
     * @return a {@code Vehicle} object created from the parsed values,
     *         or {@code null} if a parsing error occurs.
     */
    Vehicle parseVehicle(String[] values){
        try{
            int vin = Integer.parseInt(values[0]);
            int year = Integer.parseInt(values[1]);
            String make = values[2].trim();
            String model = values[3].trim();
            String vehicleType = values[4].trim();
            String color = values[5].trim();
            int odometer = Integer.parseInt(values[6]);
            double price = Double.parseDouble(values[7]);

            return new Vehicle(vin,year,make,model,vehicleType,color,odometer,price);
        } catch (NumberFormatException e) {
            System.out.println("Error Parsing Data");
            return null;
        }
    }
}
