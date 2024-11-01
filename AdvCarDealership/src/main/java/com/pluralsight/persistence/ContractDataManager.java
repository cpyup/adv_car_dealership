package com.pluralsight.persistence;

import com.pluralsight.model.Contract;
import com.pluralsight.model.LeaseContract;
import com.pluralsight.model.SalesContract;
import com.pluralsight.model.Vehicle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractDataManager {
    private static final String FILE_PATH = "contracts.csv";

    public void saveContract(Contract contract){
        if(contract instanceof SalesContract){
            writeContractToFile(salesContractString((SalesContract) contract));
        }else if(contract instanceof LeaseContract){
            writeContractToFile(leaseContractString((LeaseContract) contract));
        }
    }

    private void writeContractToFile(String contractString){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH,true))){
            writer.write(contractString);
        }catch(IOException e){
            System.out.println("Error Writing Data File");
        }
    }

    private String salesContractString(SalesContract salesContract){
        return "SALE|"+ salesContract.getContractDate()+"|"+ salesContract.getCustomerName()+"|"+
                salesContract.getCustomerEmail()+"|"+getVehicleInfo(salesContract.getVehicleSold())+"|"+ salesContract.getSalesTax()+
                "|"+ salesContract.getRecordingFee()+"|"+ salesContract.getTotalPrice()+"|"+ salesContract.isFinanced()+"|"+ salesContract.getMonthlyPayment();
    }

    private String leaseContractString(LeaseContract leaseContract){
        return  "LEASE|"+ leaseContract.getContractDate()+"|"+ leaseContract.getCustomerName()+"|"+
                leaseContract.getCustomerEmail()+"|"+getVehicleInfo(leaseContract.getVehicleSold())+"|"+leaseContract.getExpectedEndingValue()+
                "|"+ leaseContract.getLeaseFee()+"|"+ leaseContract.getTotalPrice()+"|"+ leaseContract.getMonthlyPayment();
    }

    private String getVehicleInfo(Vehicle vehicle){
        return vehicle.vin()+"|"+ vehicle.year()+"|"+vehicle.make()+"|"+vehicle.model()+"|"+vehicle.vehicleType()+
                "|"+vehicle.color()+"|"+vehicle.odometer()+"|"+vehicle.price();
    }
}
