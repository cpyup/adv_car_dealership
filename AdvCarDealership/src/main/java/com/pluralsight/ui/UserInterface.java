package com.pluralsight.ui;

import com.pluralsight.model.*;
import com.pluralsight.persistence.ContractFileManager;
import com.pluralsight.persistence.DealershipFileManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static com.pluralsight.ui.ColorCodes.*;

/**
 * The {@code UserInterface} class provides a command-line interface
 * for interacting with a car dealership. It allows users to perform
 * various operations such as searching for vehicles, adding, and
 * removing vehicles from the dealership's inventory.
 * <p>
 * This class manages user input and output, handles data retrieval
 * from the {@code Dealership} model, and saves changes to the
 * dealership's data.
 * </p>
 */
public class UserInterface {
    private Dealership dealership;
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the main menu and processes user input until the user
     * decides to quit the application.
     */
    public void display(){
        init();
        String input;

        while(true){
            outputMenu();
            input = scanner.nextLine().trim();

            switch (input){
                case "1" -> processGetByPriceRequest();
                case "2" -> processGetByMakeModelRequest();
                case "3" -> processGetByYearRequest();
                case "4" -> processGetByColorRequest();
                case "5" -> processGetByMileageRequest();
                case "6" -> processGetByVehicleTypeRequest();
                case "7" -> processGetAllVehiclesRequest();
                case "8" -> processAddVehicleRequest();
                case "9" -> processRemoveVehicleRequest();
                case "10" -> processSaleOrLease();
                case "98" -> processAdminLogin();
                case "99" -> {
                    return;
                }
                default -> System.out.println("Invalid Option");
            }
        }
    }

    /**
     * Processes user input to retrieve vehicles based on price range
     * and displays the matching vehicles.
     */
    public void processGetByPriceRequest() {
        Double min = getDoubleInput(0.0,"Minimum Price",true);
        Double max = getDoubleInput((min != null ? min : 0.0),"Maximum Price",(min != null));
        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }

    /**
     * Processes user input to retrieve vehicles based on make and
     * model and displays the matching vehicles.
     */
    public void processGetByMakeModelRequest(){
        String make = getStringInput("Desired Make",false);
        String model = getStringInput("Desired Model",true);

        displayVehicles(dealership.getVehiclesByMakeModel(make,model));
    }

    /**
     * Processes user input to retrieve vehicles based on year range
     * and displays the matching vehicles.
     */
    public void processGetByYearRequest(){
        Integer min = getIntegerInput(2000, getMaxYear(),"Minimum Year",true);
        Integer max = getIntegerInput((min != null ? min : 2000),getMaxYear(),"Maximum Year",min!=null);

        displayVehicles(dealership.getVehiclesByYear(min, max));
    }

    /**
     * Processes user input to retrieve vehicles based on color and
     * displays the matching vehicles.
     */
    public void processGetByColorRequest(){
        displayVehicles(dealership.getVehiclesByColor(getStringInput("Desired Color",false)));
    }

    /**
     * Processes user input to retrieve vehicles based on mileage
     * range and displays the matching vehicles.
     */
    public void processGetByMileageRequest(){
        Integer min = getIntegerInput(0,null, "Minimum Mileage",true);
        Integer max = getIntegerInput((min != null ? min : 0),null,"Maximum Mileage",true);

        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }

    /**
     * Processes user input to retrieve vehicles based on type and
     * displays the matching vehicles.
     */
    public void processGetByVehicleTypeRequest(){
        displayVehicles(dealership.getVehiclesByType(getStringInput("Desired Type",false)));
    }

    /**
     * Displays all vehicles in the dealership's inventory.
     */
    public void processGetAllVehiclesRequest(){
        displayVehicles(dealership.getAllVehicles());
    }

    /**
     * Processes user input to add a new vehicle to the dealership's
     * inventory and initiates saving the updated data.
     */
    public void processAddVehicleRequest(){
        try{
            String make = getStringInput("Make",false);
            String model = getStringInput("Model",false);
            String type = getStringInput("Type",false);
            String color = getStringInput("Color",false);
            Integer vin = getIntegerInput(0,null,"Vin",false);
            Integer year = getIntegerInput(1900,getMaxYear(),"Year",false);
            Integer odometer = getIntegerInput(0,null,"Mileage",false);
            Double price = getDoubleInput(0.0,"Price",false);

            if(vin != null && year != null && odometer != null && price != null){
                Vehicle vehicle = new Vehicle(vin,year,make,model,type,color,odometer,price);
                if(confirmUserAction("Addition")){
                    dealership.addVehicle(vehicle);
                    initiateSaveDealership();
                }
            }else{
                throw new RuntimeException();
            }
        }catch (Exception e){
            System.out.println("Error processing input");
        }
    }

    /**
     * Processes user input to remove a vehicle from the dealership's
     * inventory based on VIN and initiates saving the updated data.
     */
    public void processRemoveVehicleRequest(){
        Integer vin = getIntegerInput(0,null,"Vin To Remove",false);
        if(vin != null){
            List<Vehicle> vehicles = dealership.getVehiclesByVin(vin);
            displayVehicles(vehicles);

            if(confirmUserAction("Deletion")){
                vehicles.forEach(vehicle -> dealership.removeVehicle(vehicle));
                initiateSaveDealership();
            }
        }
    }

    public void processSaleOrLease(){  // TODO: Implement error handling for getting these values
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String name = getStringInput("Customer Name",false);
        String email = getStringInput("Customer Email",false);
        Integer vin = getIntegerInput(0,null,"VIN",false); // TODO: Do this first, validate it exists in inventory

        Vehicle vehicle = dealership.getVehiclesByVin(vin).get(0);

        String contractType = getStringInput("SALE Or LEASE?",false);

        Contract contract;

        if(contractType.equalsIgnoreCase("sale")){
            boolean isFinanced = getBoolInput();
            contract = new SalesContract(date,name,email,vehicle,isFinanced ? "YES" : "NO");
            saveContract(contract);
        }else if(contractType.equalsIgnoreCase("lease")){
            contract = new LeaseContract(date,name,email,vehicle);
            saveContract(contract);
        }


    }

    public void processAdminLogin(){
        AdminUserInterface adminUserInterface = new AdminUserInterface();
        adminUserInterface.display();
    }

    private void saveContract(Contract contract){
        if(confirmUserAction("New Contract")){
            ContractFileManager contractFileManager = new ContractFileManager();
            contractFileManager.saveContract(contract);
            dealership.removeVehicle(contract.getVehicleSold());
            initiateSaveDealership();
        }
    }

    /**
     * Initializes the dealership by loading data from the file.
     */
    private void init(){
        DealershipFileManager manager = new DealershipFileManager();
        dealership = manager.getDealership();
    }

    /**
     * Outputs the main menu options to the console.
     */
    private void outputMenu(){
        System.out.println("Dealership Menu Options:");
        System.out.println("\t1 - Search By Price");
        System.out.println("\t2 - Search By Make/Model");
        System.out.println("\t3 - Search By Year");
        System.out.println("\t4 - Search By Color");
        System.out.println("\t5 - Search By Mileage");
        System.out.println("\t6 - Search By Type");
        System.out.println("\t7 - List All Vehicles");
        System.out.println("\t8 - Add A Vehicle");
        System.out.println("\t9 - Remove A Vehicle");
        System.out.println("\t10 - Sell/Lease A Vehicle");
        System.out.println("\t98 - Administrator");
        System.out.println("\t99 - Quit");
    }

    /**
     * Displays a list of vehicles to the console.
     *
     * @param vehicles the list of vehicles to display
     */
    private void displayVehicles(List<Vehicle> vehicles){
        if(!vehicles.isEmpty()){
            System.out.println(inventoryAsTable(vehicles));
            confirmContinue();
        }else{
            System.out.println("No results found.");
        }
    }

    /**
     * Constructs a formatted string representation of a list of vehicles as a table.
     * This method initializes a StringBuilder with a header and then iterates
     * through a list of Vehicle objects. It formats each vehicle using the
     * {@link #rowFormat(String, String)} method, alternating colors for
     * even and odd indexed vehicles. Finally, it appends a footer to the table.
     *
     * @param vehicles A list of Vehicle objects to be included in the table.
     * @return        A string representation of the vehicle inventory formatted as a table.
     */
    private String inventoryAsTable(List<Vehicle> vehicles){
        StringBuilder builder = new StringBuilder(dataHeader());
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle vehicle = vehicles.get(i);
            builder.append(rowFormat(vehicle.toString(),(i % 2 == 0 ? TABLE_COLOR_0  : TABLE_COLOR_1))).
                    append(RESET).append("\n");
        }
        builder.append(String.format(HEADER_COLOR+"%-94s"+RESET," "));
        return builder.toString();
    }

    /**
     * Saves the current state of the dealership to the file.
     */
    private void initiateSaveDealership(){
        DealershipFileManager manager = new DealershipFileManager();
        manager.saveDealership(dealership);
        System.out.println("Data Successfully Updated.");
    }

    /**
     * Retrieves the maximum year based on the current year.
     *
     * @return the maximum year (current year + 1)
     */
    private int getMaxYear(){
        LocalDateTime now = LocalDateTime.now();
        return now.getYear() + 1;
    }

    /**
     * Prompts the user for a string input and handles validation.
     *
     * @param displayType the type of input to be displayed to the user
     * @param isNullable  indicates if the input can be left blank
     * @return the validated string input
     */
    private String getStringInput(String displayType, boolean isNullable){
        String input = "";

        while (input.isBlank()) {
            System.out.printf("Enter The %s"+(isNullable ? " (or press enter to leave blank)":"")+": ",displayType);
            try {
                input = scanner.nextLine().trim();
                if(isNullable)return input;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid type.");
            }
        }
        return input;
    }

    /**
     * Prompts the user for an integer input and handles validation.
     *
     * @param min       the minimum allowable value or {@code null}
     * @param max       the maximum allowable value or {@code null}
     * @param verbiage  a description of the input
     * @param isNullable indicates if the input can be left blank
     * @return the validated integer input
     */
    private Integer getIntegerInput(Integer min, Integer max, String verbiage, boolean isNullable){
        String input;
        int targetInt = -1;
        // Looping until a valid number is entered (factoring minimum value param, if set)
        while(targetInt < 0 || (min != null && targetInt < min) || (max != null && targetInt > max)){
            // Display output for getting the value using the verbiage string to denote usage (e.g. "Minimum Year")
            System.out.printf("Enter The %s"+(isNullable ? " (or press 'enter' to leave blank)" : "")+": ",verbiage);
            input = scanner.nextLine();
            // Return on blank input for nullable values
            if(isNullable&&input.isBlank())return null;
            // Parse the value from the input string/handle input error
            try {
                targetInt = Integer.parseInt(input);
                if (targetInt < 0) System.out.println("Number must be non-negative. Please try again.");
                if(max != null && targetInt > max) System.out.printf("Number cannot be larger than %s. Please try again.%n",max);
                if(min != null && targetInt < min) System.out.printf("Number cannot be smaller than %s. Please try again.%n",min);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return targetInt;
    }

    /**
     * Prompts the user for a double input and handles validation.
     *
     * @param min       the minimum allowable value or {@code null}
     * @param verbiage  a description of the input
     * @param isNullable indicates if the input can be left blank
     * @return the validated double input
     */
    private Double getDoubleInput(Double min, String verbiage, boolean isNullable){
        String input;
        double targetDouble = -1.0;
        // Looping until a valid number is entered (factoring minimum value param, if set)
        while(targetDouble < 0 || (min != null && (targetDouble < min))){
            // Display output for getting the value using the verbiage string to denote usage (e.g. "Minimum Year")
            System.out.printf("Enter The %s"+(isNullable ? " (or press 'enter' to leave blank)" : "")+": ",verbiage);
            input = scanner.nextLine();
            // Return on blank input for nullable values
            if(isNullable&&input.isBlank())return null;
            // Parse the value from the input string/handle input error
            try {
                targetDouble = Double.parseDouble(input);
                if (targetDouble < 0){
                    System.out.println("Number must be non-negative. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return targetDouble;
    }

    private boolean getBoolInput(){
        String input = "";

        while(!input.equalsIgnoreCase("no") && !input.equalsIgnoreCase("yes")){
            System.out.print("Is this sale financed? (Yes/No)");
            input = scanner.nextLine();
        }
        return input.equalsIgnoreCase("yes");
    }

    /**
     * Prompts the user to confirm an action.
     *
     * @param verbiage a description of the action to confirm
     * @return {@code true} if the user confirms; {@code false} if the user cancels
     */
    private boolean confirmUserAction(String verbiage){
        System.out.print("Enter 'X' To Cancel Or Press 'Enter' To Confirm "+verbiage + ":");
        String input = scanner.nextLine();
        return !input.equalsIgnoreCase("X");
    }

    /**
     * Prompts the user to press 'Enter' to continue.
     * This method waits for the user to input a line, effectively pausing
     * the program until the user acknowledges by pressing 'Enter'.
     */
    private void confirmContinue(){
        System.out.print("Press 'Enter' To Continue");
        scanner.nextLine();
    }

    /**
     * Formats a string representing the header for the vehicle display output.
     * @return table header string
     */
    private String dataHeader(){
        return String.format("%n"+BORDER_STRING+HEADER_COLOR+"%6s  %6s %8s %12s %12s %12s %14s %12s  "+BORDER_STRING+RESET+"%n",
                "Vin","Year","Make","Model","Type","Color","Miles","Price");
    }

    /**
     * Formats a vehicle string with a specified color and borders.
     * This method takes a vehicle string and a color, replaces the
     * hyphens in the vehicle string with the specified color and a column
     * separator, and adds a border to the formatted string.
     *
     * @param vehicleString The string representation of the vehicle,
     *                      containing hyphens to be replaced.
     * @param color         The color to format the vehicle string.
     * @return             A formatted string that includes the color and borders.
     */
    private String rowFormat(String vehicleString, String color){
        return BORDER_STRING+color+vehicleString.replace("-",color+COLUMN_SEPARATOR+color)+BORDER_STRING;
    }

}
