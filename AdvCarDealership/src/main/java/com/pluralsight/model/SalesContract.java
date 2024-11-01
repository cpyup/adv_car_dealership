package com.pluralsight.model;

public class SalesContract extends Contract{
    private double salesTax;
    private double recordingFee;
    private double processingFee;
    private String isFinanced;

    public SalesContract(String contractDate, String customerName, String customerEmail, Vehicle vehicleSold, String isFinanced) {
        super(contractDate, customerName, customerEmail,vehicleSold);
        this.salesTax = (getVehicleSold().price() / 100) * 5;
        this.recordingFee = 100;
        this.processingFee = (getVehicleSold().price() < 10000 ? 295 : 495);
        this.isFinanced = isFinanced;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public String isFinanced() {
        return isFinanced;
    }

    public void setFinanced(String financed) {
        isFinanced = financed;
    }

    @Override
    public double getTotalPrice() {
        return getVehicleSold().price() + salesTax + recordingFee + processingFee;
    }

    @Override
    public double getMonthlyPayment() {
        int numberOfPayments = 0;
        double interestRate = 0;
        if (isFinanced.equalsIgnoreCase("yes")) {
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
