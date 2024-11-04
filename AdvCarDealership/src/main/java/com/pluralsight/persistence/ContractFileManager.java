package com.pluralsight.persistence;

import com.pluralsight.model.Contract;
import com.pluralsight.model.LeaseContract;
import com.pluralsight.model.SalesContract;
import com.pluralsight.model.Vehicle;

import java.io.*;
import java.util.ArrayList;

public class ContractFileManager extends FileManager{
    private static final String FILE_PATH = "contracts.csv";

    public void saveContract(Contract contract){
        if(contract instanceof SalesContract){
            writeContractToFile(salesContractString((SalesContract) contract));
        }else if(contract instanceof LeaseContract){
            writeContractToFile(leaseContractString((LeaseContract) contract));
        }
    }

    public ArrayList<Contract> loadContracts(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))){
            ArrayList<Contract> contracts = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split("\\|");

                if(values.length == 18 && values[0].equalsIgnoreCase("sale")){
                    contracts.add(parseSalesContract(values));
                }

                if(values.length == 16 && values[0].equalsIgnoreCase("lease")){
                    contracts.add(parseLeaseContract(values));
                }

            }
            return contracts;
        }catch(Exception e){
            return null;
        }
    }

    private LeaseContract parseLeaseContract(String[] values){
        String[] vehicleValues = {values[4],values[5],values[6],values[7],values[8],values[9],values[10],values[11]};
        return new LeaseContract(values[1],values[2],values[3],parseVehicle(vehicleValues));
    }

    private SalesContract parseSalesContract(String[] values){
        String[] vehicleValues = {values[4],values[5],values[6],values[7],values[8],values[9],values[10],values[11]};
        return new SalesContract(values[1],values[2],values[3],parseVehicle(vehicleValues),values[16]);
    }

    private void writeContractToFile(String contractString){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH,true))){
            writer.write(contractString);
        }catch(IOException e){
            System.out.println("Error Writing Data File");
        }
    }

    private String salesContractString(SalesContract salesContract){
        return "\nSALE|"+ salesContract.getContractDate()+"|"+ salesContract.getCustomerName()+"|"+
                salesContract.getCustomerEmail()+"|"+getVehicleInfo(salesContract.getVehicleSold())+"|"+ salesContract.getSalesTax()+
                "|"+ salesContract.getRecordingFee()+"|"+ salesContract.getProcessingFee()+"|"+salesContract.getTotalPrice()+"|"+ salesContract.isFinanced()+"|"+ salesContract.getMonthlyPayment();
    }

    private String leaseContractString(LeaseContract leaseContract){
        return  "\nLEASE|"+ leaseContract.getContractDate()+"|"+ leaseContract.getCustomerName()+"|"+
                leaseContract.getCustomerEmail()+"|"+getVehicleInfo(leaseContract.getVehicleSold())+"|"+leaseContract.getExpectedEndingValue()+
                "|"+ leaseContract.getLeaseFee()+"|"+ leaseContract.getTotalPrice()+"|"+ leaseContract.getMonthlyPayment();
    }

    private String getVehicleInfo(Vehicle vehicle){
        return vehicle.vin()+"|"+ vehicle.year()+"|"+vehicle.make()+"|"+vehicle.model()+"|"+vehicle.vehicleType()+
                "|"+vehicle.color()+"|"+vehicle.odometer()+"|"+vehicle.price();
    }

}
