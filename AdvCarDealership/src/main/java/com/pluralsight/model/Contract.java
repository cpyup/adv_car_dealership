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
}
