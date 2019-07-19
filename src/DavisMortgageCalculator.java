/*
 * The DavisMortgageCalculator program will do the following:
 *   Give a loan a unique name using last name and zip code inputs from user. 
 *   Calculate the total monthly payment of a 30-year mortgage loan given the 
 *   input of the home's value and the loan's annual interest rate, both from 
 *   the user. Display loan details and results of calculations.  
 */

import java.util.Scanner;

/**
 * @author Erik Davis
 * @version 1.0, Java Assn 5
 */
public class DavisMortgageCalculator {

    /**
     * Main method will ask user for input values, create two new objects for 
     * the new loan at a length of 20 years and 30 years, call on methods from 
     * MortgageLoan class to set object values, then call upon the display 
     * methods to display calculations and results.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        String lastName;
        double homeValue;
        
        programDescription ();
        
        Scanner keyboard = new Scanner(System.in);
        System.out.println();
        System.out.print("Please enter the home buyer's last name: ");
        lastName = keyboard.next();
        
        System.out.print("Please enter the home value: ");
        homeValue = keyboard.nextDouble();

        System.out.println();
        System.out.println("Down Payment Options:");
        System.out.println("  N - Nothing down");
        System.out.println("  L - Low down payment");
        System.out.println("  S - Standard down payment");
        System.out.println("  H - High down payment");
        System.out.print("Please enter your choice: ");
        char downPaymentOption = keyboard.next().charAt(0);
        System.out.println();
        
        MortgageLoan loan20Years = new MortgageLoan (lastName, homeValue, 
            downPaymentOption, 20);
        
        MortgageLoan loan30Years = new MortgageLoan (lastName, homeValue, 
            downPaymentOption, 30);
        
        System.out.println();
        
        loan20Years.displayLoanInfo();
        
        System.out.println("Loan Option 1:");
        loan20Years.calcAndDisplayPaymentInfo();
        System.out.println();
        
        System.out.println("Loan Option 2:");
        loan30Years.calcAndDisplayPaymentInfo();
        System.out.println();
    }
    
    /** 
     * The programDescription method will describe to the user what the program
     * will calculate and return.
     */
    public static void programDescription () {
        System.out.println("This program implements a Mortgage Payment "
                + "Calculator.");
        System.out.println();
        System.out.println("Given a home's value and the home owner's preferred"
                + "down payment, it will calculate \nthe monthly mortgage "
                + "payment, which includes taxes, insurance, principle and "
                + "interest \nfor a 20 year loan and a 30 year loan. In "
                + "addition, it will produce detailed information \nabout "
                + "the loan.");
    }
}
