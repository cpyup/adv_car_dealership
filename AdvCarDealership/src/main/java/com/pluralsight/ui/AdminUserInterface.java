package com.pluralsight.ui;

import com.pluralsight.model.Contract;
import com.pluralsight.model.LeaseContract;
import com.pluralsight.model.SalesContract;
import com.pluralsight.persistence.ContractFileManager;

import java.util.ArrayList;

public class AdminUserInterface extends UserInterface {
    private ArrayList<Contract> contracts;
    private static final String ADMIN_PASSWORD = "password";
    @Override
    public void display() {
        boolean loggedIn = validateLogin(attemptLogin());

        if(loggedIn){
            init();
        }else{
            System.out.println("Invalid Password");
            return;
        }

        while(loggedIn){
            outputMenu();
            String input = scanner.nextLine().trim();

            switch (input){
                case "1" -> displayAllContracts();
                case "2" -> displayRecentContracts();
                case "3" -> displaySalesContracts();
                case "4" -> displayLeaseContracts();
                case "99" -> loggedIn = false;
                default -> System.out.println("Invalid Option");
            }
        }
    }

    @Override
    void outputMenu(){
        System.out.println("Dealership Menu Options:");
        System.out.println("\t1 - List All Contracts");
        System.out.println("\t2 - List Recent Contracts");
        System.out.println("\t3 - List Sales Contracts");
        System.out.println("\t4 - List Lease Contracts");
        System.out.println("\t99 - Logout");
    }

    @Override
    void init(){
        ContractFileManager contractFileManager = new ContractFileManager();
        contracts = contractFileManager.loadContracts();
    }

    private void displayAllContracts(){
        contracts.forEach(System.out::println);
    }

    private void displayRecentContracts(){

    }

    private void displaySalesContracts(){
        for(Contract contract : contracts){
            if(contract instanceof SalesContract salesContract){
                System.out.println(salesContract);
            }
        }
    }

    private void displayLeaseContracts(){
        for(Contract contract : contracts){
            if(contract instanceof LeaseContract leaseContract){
                System.out.println(leaseContract);
            }
        }
    }

    private String attemptLogin(){
        return getStringInput("Administrator Password To Continue",false);
    }

    private boolean validateLogin(String password){
        return password.equals(ADMIN_PASSWORD);
    }
}
