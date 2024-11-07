package com.pluralsight.model;

public abstract class Contract {
    private final String contractDate;
    private final String customerName;
    private final String customerEmail;
    private final Vehicle vehicleSold;

    public Contract(String contractDate, String customerName, String customerEmail, Vehicle vehicleSold) {
        this.contractDate = contractDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
    }

    public String getContractDate() {
        return contractDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Vehicle getVehicleSold() {
        return vehicleSold;
    }

    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();

    public String customerString(){
        return "\nDate: "+getContractDate() + "\nCustomer: "+getCustomerName() + "\nEmail: "+getCustomerEmail();
    }

    public String vehicleString(){
        return "\nVin: "+getVehicleSold().vin()+ "\nYear: " + getVehicleSold().year() + "\nMake: " + getVehicleSold().make()+
                "\nModel: " + getVehicleSold().model()+ "\nType: " + getVehicleSold().vehicleType()+ "\nColor: " + getVehicleSold().color() +
                "\nMileage: " + getVehicleSold().odometer()+ "\nPrice: " + getVehicleSold().price();
    }
}
