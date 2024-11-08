package com.pluralsight.ui;

import com.pluralsight.model.Contract;
import com.pluralsight.model.LeaseContract;
import com.pluralsight.model.SalesContract;
import com.pluralsight.persistence.ContractFileManager;
import com.pluralsight.utility.LoginVerification;

import java.util.ArrayList;

public class AdminUserInterface extends UserInterface {
    private ArrayList<Contract> contracts;
    @Override
    public void display() {
        boolean loggedIn = LoginVerification.verifyPassword(attemptLogin());

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
        confirmContinue();
    }

    private void displayRecentContracts(){
        System.out.print("Enter Amount To Display: ");
        String input = scanner.nextLine();

        try{
            int amount = Integer.parseInt(input);
            if(amount <= contracts.size()){
                for(int i =0; i < amount; i++){
                    System.out.println(contracts.get(i));
                }
            }else{
                System.out.println("Only "+contracts.size()+" Current Contracts");
            }
        }catch (Exception e){
            System.out.println("Input Error");
        }
        confirmContinue();
    }

    private void displaySalesContracts(){
        for(Contract contract : contracts){
            if(contract instanceof SalesContract salesContract){
                System.out.println(salesContract);
            }
        }
        confirmContinue();
    }

    private void displayLeaseContracts(){
        for(Contract contract : contracts){
            if(contract instanceof LeaseContract leaseContract){
                System.out.println(leaseContract);
            }
        }
        confirmContinue();
    }

    private String attemptLogin(){
        return getStringInput("Administrator Password To Continue",false);
    }

    @Override
    public void confirmContinue(){
        System.out.println();
        super.confirmContinue();
    }
}
