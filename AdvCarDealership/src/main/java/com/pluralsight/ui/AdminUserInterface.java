package com.pluralsight.ui;

import com.pluralsight.model.Contract;
import com.pluralsight.persistence.ContractFileManager;

import java.util.ArrayList;

public class AdminUserInterface extends UserInterface {
    @Override
    public void display() {
        attemptLogin();
    }

    private void outputMenu(){

    }

    private void displayAllContracts(){
        ContractFileManager contractFileManager = new ContractFileManager();
        ArrayList<Contract> contracts = contractFileManager.loadContracts();
        contracts.forEach(System.out::println);
    }

    private void attemptLogin(){
        String password = getStringInput("Administrator Password To Continue",false);
        validateLogin(password);
    }

    private void validateLogin(String password){

    }
}
