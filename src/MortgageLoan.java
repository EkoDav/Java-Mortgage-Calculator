/*
 * The MotgageLoan class is a supplement to the DavisMortgageCalculator, and is 
 * used to create new loans by giving them an indentifier and calculating the
 * values needed to determine the total monthly mortgage payment.
 */

/**
 *
 * @author Erik Davis
 */
public class MortgageLoan {
    //Data fields
    private char loanType;
    private String loanName;
    private double homeValue;
    private double downPayment;
    private double loanAmount;
    private int loanLengthYears;
    private double annualIntRate;
    
    //Loan constructor no arguments
    public MortgageLoan() {
        homeValue = 0.0;
        loanLengthYears = 30;
        loanName = " ";
        downPayment = 0;
        loanAmount = 0.0;
        loanType = ' ';
        annualIntRate = 0.0;
    }
    
    //Loan constructor with arguments
    public MortgageLoan(String lastName, double homeValue, 
            char downPaymentOption, int loanLength) {
        this.homeValue = homeValue;
        loanLengthYears = loanLength;
        setLoanName(lastName);
        setDownPayment (downPaymentOption);
        setLoanAmount();
        setLoanType();
        setLoanIntRate();
    }
    
    //Setter methods
    /**
     * Set loan name based on the first 6 characters of the home owner's last 
     * name. All letters will be capitalized, and if the name has less than 6
     * characters, will add 'X' for each missing character up to 6 total. 
     * @param lastName 
     */
    public void setLoanName(String lastName) {
        final int NAME_LENGTH_LIMIT = 6;
        final String xPlaceHolders = "XXXXXX";
        String upperCaseLast = lastName.toUpperCase();
        int nameLength = upperCaseLast.length();
        
        if (nameLength >= NAME_LENGTH_LIMIT) {
            loanName = upperCaseLast.substring(0, 6);
        } else {
            String nameCombo = upperCaseLast + xPlaceHolders;
            loanName = nameCombo.substring(0, 6);
        }
    }
    
    /**
     * Set the home value from input received from user. 
     * @param newHomeValue 
     */
    public void setHomeValue(double newHomeValue) { 
        homeValue = newHomeValue; 
    }
    
    /**
     * Set the down payment based on input received from user. 
     * @param downPaymentOption 
     */
    public void setDownPayment (char downPaymentOption) {
        switch (downPaymentOption) {
            case 'N': case 'n':
                downPayment = 0;
                break;
            case 'L': case 'l':
                downPayment = 10;
                break;
            case 'S': case 's':
                downPayment = 20;
                break;
            case 'H': case 'h':
                downPayment = 30;
                break;
            default:
                System.out.println("Invalid input for down payment option. No "
                        + "down payment will be applied.");
                downPayment = 0;
        }
    }
    
    /**
     * Set the loan amount based on the home value and down payment. 
     */
    public void setLoanAmount() { 
        double downPaymentDecimal = downPayment / 100;
        loanAmount = homeValue * (1 - downPaymentDecimal); 
    }
    
    /**
     * Set the loan type depending on the loan amount.
     */
    public void setLoanType() {
        final int CONFORMING_LIMIT = 417000;
        loanType = 'C';
        if (loanAmount > CONFORMING_LIMIT) {
            loanType = 'J';
        }
    }
    
    /**
     * Set the loan interest rate depending on the loan type and length. 
     */
    public void setLoanIntRate() {
        if (loanType == 'C') {
            if (loanLengthYears == 30) {
                annualIntRate = 4.5;
            } else {
                annualIntRate = 3.85;
            }
        } else {
            if (loanLengthYears == 30) {
                annualIntRate = 4.125;
            } else {
                annualIntRate = 3.5;
            }
        } 
    }

     /** 
     * The monthlyPropertyTax method will do the following:
     *   Given the value of the home, will compute the monthly property
     *   tax. Will utilize constant percentages to calculate the home's assessed 
     *   value and percentage of that value used to calculate tax. Will also 
     *   utilize the constant admin fee in dollars. After calculation, will 
     *   return Monthly Property Tax as a double value. 
     * 
     * @return monthlyPropertyTax - the monthly property tax in dollars
     */
    public double calcMonthlyPropertyTax() {
        final double ASSESSED_VALUE_PERCENT = 0.85;
        final double PROPERTY_TAX_PERCENT = 0.0063;
        final int ADMIN_FEE = 35;
        double assessedValue;
        double annualPropertyTax;
        double monthlyPropertyTax;
        
        assessedValue = ASSESSED_VALUE_PERCENT * homeValue;
        annualPropertyTax = (PROPERTY_TAX_PERCENT * assessedValue) + ADMIN_FEE;
        monthlyPropertyTax = annualPropertyTax / 12;
        
        return monthlyPropertyTax;
    }
    
     /**
     * The monthlyInsurancePremium method will do the following:
     *   Given the purchase price of the home, will compute the monthly insurance
     *   premium based on the insurance premium determined by the down payment. 
     *   Will return the result as a double value rounded to the nearest dollar 
     *   amount.
     *   
     * @return monthlyInsuranceRounded - the monthly cost of insurance rounded
     *  to the nearest dollar. 
     */
    public double calcMonthlyInsurancePremium() {
        double annualInsuranceRate = 0;
        double annualInsurancePre;
        double monthlyInsurancePre;
        double monthlyInsuranceRounded;
        
        if (downPayment <= 10) {
            annualInsuranceRate = 0.0052;
        } else if (downPayment <= 20) {
            annualInsuranceRate = 0.0049;
        } else if (downPayment > 20) {
            annualInsuranceRate = 0.0047;
        }
        
        annualInsurancePre = homeValue * annualInsuranceRate;
        monthlyInsurancePre = annualInsurancePre / 12;
        monthlyInsuranceRounded = Math.round(monthlyInsurancePre);
        
        return monthlyInsuranceRounded;
    }
    
     /**
     * The monthlyPriAndIntPayment method will do the following:
     *   Given the loan amount and the annual interest rate for the loan, will
     *   compute the monthly principle and interest loan payment. Will return 
     *   the result as a double value. 
     *   
     * @return monthlyPriAndIntPayment
     */
    public double calcMonthlyPriAndIntPayment() {
        double annualIntDecimal;
        int loanLengthMonths;
        double monthlyInterestRate;
        double factor;
        double monthlyPriAndIntPayment;
        
        annualIntDecimal = annualIntRate / 100;
        monthlyInterestRate = annualIntDecimal / 12;
        loanLengthMonths = loanLengthYears * 12;
        factor = Math.exp(loanLengthMonths * Math.log(1 + monthlyInterestRate));     
        monthlyPriAndIntPayment = ((factor * monthlyInterestRate * loanAmount)/
                (factor - 1));
        
        return monthlyPriAndIntPayment;
    }
    
    /**
     * The displayLoanInfo method will display the basic loan info compiled by 
     * the class methods. 
     */
    public void displayLoanInfo() {
        String displayIdentity = "Loan Identifier";
        String displayAmount = "Loan Amount";
        String displayType = "Loan Type";
        String cType = "Conforming";
        String jType = "Jumbo";
      
        System.out.printf("  %-35s" + "%10s", displayIdentity, loanName);
        System.out.println();
        
        System.out.printf("  %-35s" + "%10.2f", displayAmount, loanAmount);
        System.out.println();
        
        if (loanType == 'C') {
            System.out.printf("  %-35s" + "%10s", displayType, cType);
        } else { 
            System.out.printf("  %-35s" + "%10s", displayType, jType);
        }
        
        System.out.println();
        System.out.println();
    }
    
    /**
     * The calcAndDisplayPaymentInfo method will call the calculation methods to
     * calculate the monthly mortgage payment and display the results, as well 
     * as additional loan information.
     */
    public void calcAndDisplayPaymentInfo () {
        double moPropertyTax;
        double moInsurancePre;
        double moPriAndIntPayment;
        double totalMoMortgagePayment;
        String additionLine = "--------";
        String displayTaxes = "Monthly Taxes";
        String displayInsurance = "Monthly Insurance";
        String displayPriAndInt = "Monthly Principle & Interest";
        String displayTotalPayment = "Total Monthly Mortgage Payment";
        String displayLength = "Loan Length";
        String displayAnnualIntRate = "Annual Interest Rate";
        
        moPropertyTax = this.calcMonthlyPropertyTax();
        moInsurancePre = this.calcMonthlyInsurancePremium();
        moPriAndIntPayment = this.calcMonthlyPriAndIntPayment();
        totalMoMortgagePayment = moPropertyTax + moInsurancePre + 
                moPriAndIntPayment;
        
        System.out.printf("  %-35s" + "%4d years", displayLength, loanLengthYears);
        System.out.println();
        
        System.out.printf("  %-35s" + "%9.3f%%", displayAnnualIntRate, annualIntRate);
        System.out.println();
        System.out.println();
        
        System.out.printf("  %-35s" + "%10.2f", displayTaxes, moPropertyTax);
        System.out.println();
        
        System.out.printf("  %-35s" + "%10.2f", displayInsurance, moInsurancePre);
        System.out.println();
        
        System.out.printf("  %-35s" + "%10.2f", displayPriAndInt, 
                moPriAndIntPayment);
        System.out.println();
        
        System.out.printf("%47s", additionLine);
        System.out.println();
        
        System.out.printf("  %-35s" + "%10.2f", displayTotalPayment, 
                totalMoMortgagePayment);
        System.out.println();
    }
}
