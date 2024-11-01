package com.pluralsight.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealershipTest {

    @Test
    public void dealership_GetAllVehiclesValid_Success(){
        Dealership dealership = new Dealership("Test Dealer","123 Fake Street","8675309");
        dealership.addVehicle(new Vehicle(8675309,2015,"Chevy","Cruze","Sedan","Grey",10000,5000));
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(8675309,2015,"Chevy","Cruze","Sedan","Grey",10000,5000));
        assertEquals(vehicles,dealership.getAllVehicles());
    }

    @Test
    public void dealership_GetAllVehiclesEmptyDealership_IsEmptyTrue(){
        Dealership dealership = new Dealership("Test Dealer","123 Fake Street","8675309");
        assertTrue(dealership.getAllVehicles().isEmpty());
    }

    @Test
    public void dealership_AddVehicleToInventory_Success(){
        Vehicle vehicle = new Vehicle(8675309,2015,"Chevy","Cruze","Sedan","Grey",10000,5000);
        Dealership dealership = new Dealership("Test Dealer","123 Fake Street","8675309");

        dealership.addVehicle(vehicle);

        assertEquals(dealership.getVehiclesByVin(8675309).get(0).vin(),vehicle.vin());
    }

    @Test
    public void dealership_RemoveVehicleFromInventory_Success(){
        Vehicle vehicle = new Vehicle(8675309,2015,"Chevy","Cruze","Sedan","Grey",10000,5000);
        Dealership dealership = new Dealership("Test Dealer","123 Fake Street","8675309");
        List<Vehicle> emptyList = new ArrayList<>();
        dealership.addVehicle(vehicle);

        dealership.removeVehicle(vehicle);

        assertEquals(dealership.getAllVehicles(),emptyList);
    }
}