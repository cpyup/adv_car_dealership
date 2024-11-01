package com.pluralsight.model;

public class SalesContract extends Contract{
    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private boolean isFinanced;

    public SalesContract(String contractDate, String customerName, String customerEmail, Vehicle vehicleSold, double totalPrice, double monthlyPayment, boolean isFinanced) {
        super(contractDate, customerName, customerEmail, vehicleSold, totalPrice, monthlyPayment);
        this.salesTax = (getVehicleSold().price() / 100) * 5;
        this.recordingFee = 100;
        this.processingFee = (getVehicleSold().price() < 10000 ? 295 : 495);
        this.isFinanced = isFinanced;
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().price() + salesTax + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        int numberOfPayments = 0;
        double interestRate = 0;
        if (isFinanced) {
            if (getVehicleSold().price() >= 10000) {
                numberOfPayments = 48;
                interestRate = 4.25 / 1200;
            } else {
                numberOfPayments = 24;
                interestRate = 5.25 / 1200;
            }

            double monthlyPayment = getTotalPrice() * (interestRate * Math.pow(1 + interestRate, numberOfPayments)) / (Math.pow(1 + interestRate, numberOfPayments) - 1);
            monthlyPayment = Math.round(monthlyPayment * 100);
            monthlyPayment /= 100;
            return monthlyPayment;
        } else {
            return 0.0;
        }
    }
}
