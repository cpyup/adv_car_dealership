package com.pluralsight.ui;

import java.util.Scanner;

public abstract class UserInterface {
    static final Scanner scanner = new Scanner(System.in);

    public abstract void display();
    abstract void outputMenu();
    abstract void init();

    /**
     * Prompts the user for a string input and handles validation.
     *
     * @param displayType the type of input to be displayed to the user
     * @param isNullable  indicates if the input can be left blank
     * @return the validated string input
     */
    String getStringInput(String displayType, boolean isNullable){
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
     * Prompts the user to press 'Enter' to continue.
     * This method waits for the user to input a line, effectively pausing
     * the program until the user acknowledges by pressing 'Enter'.
     */
    void confirmContinue(){
        System.out.print("Press 'Enter' To Continue");
        scanner.nextLine();
    }
}
