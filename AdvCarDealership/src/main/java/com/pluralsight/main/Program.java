package com.pluralsight.main;

import com.pluralsight.ui.StandardUserInterface;

/**
 * The entry point of the application.
 * This class contains the main method which serves as the starting point
 * for the program. It initializes the UserInterface and calls the
 * display method to present the user interface.
 */
public class Program {

    /**
     * The main method that runs the application.
     *
     * @param args Command-line arguments passed to the program (not used).
     */
    public static void main(String[] args) {
        StandardUserInterface standardUserInterface = new StandardUserInterface();
        standardUserInterface.display();
    }
}
