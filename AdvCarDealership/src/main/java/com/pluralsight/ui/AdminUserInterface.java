package com.pluralsight.ui;

import com.pluralsight.model.Contract;
import com.pluralsight.persistence.ContractFileManager;

import java.util.ArrayList;

public class AdminUserInterface extends UserInterface{
    @Override
    public void display() {

    }

    private void displayAllContracts(){
        ContractFileManager contractFileManager = new ContractFileManager();
        ArrayList<Contract> contracts = contractFileManager.loadContracts();
        contracts.forEach(System.out::println);
    }

    private void attemptLogin(){

    }

    private void validateLogin(){

    }
}
