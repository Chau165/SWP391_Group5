package loan;

/**
 * Calculator for Deferred Payment Loan - Lump Sum Payment
 * 
 * Calculates the amount due at loan maturity where interest is compounded
 * over the loan period and paid in full at the end along with principal.
 * 
 * Formula: A = P(1 + r/n)^(nt)
 * Where:
 * - A: Amount Due at Loan Maturity
 * - P: Principal (initial loan amount)
 * - r: Annual interest rate (decimal)
 * - n: Compound periods per year
 * - t: Time in years
 * 
 * @author Banking System Developer
 * @version 1.0
 */
public class DeferredPaymentLoanCalculator {
    
    /**
     * Calculates the lump sum payment amount due at loan maturity.
     * 
     * @param principal The initial loan amount in VND (must be >= 0)
     * @param annualInterestRate The annual interest rate in percentage (0-100)
     * @param years The loan term in years (must be > 0)
     * @param compoundPeriodPerYear The number of compounding periods per year (must be >= 1)
     *                              Examples: 1 = Annually (APY), 12 = Monthly, 365 = Daily
     * @return The total amount to be paid at loan maturity, rounded to 2 decimal places
     * @throws InvalidLoanParameterException if any input parameter is invalid
     */
    public static double calculateLumpSumPayment(
            double principal, 
            double annualInterestRate, 
            int years, 
            int compoundPeriodPerYear) throws InvalidLoanParameterException {
        
        // Input validation
        validateInputs(principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Special case: if principal is 0, return 0
        if (principal == 0) {
            return 0.0;
        }
        
        // Special case: if interest rate is 0, return principal only
        if (annualInterestRate == 0) {
            return roundToTwoDecimals(principal);
        }
        
        // Convert annual interest rate from percentage to decimal
        double r = annualInterestRate / 100.0;
        
        // Calculate: A = P(1 + r/n)^(nt)
        double ratePerPeriod = r / compoundPeriodPerYear;
        double exponent = compoundPeriodPerYear * years;
        double amount = principal * Math.pow(1 + ratePerPeriod, exponent);
        
        // Round to 2 decimal places
        return roundToTwoDecimals(amount);
    }
    
    /**
     * Validates all input parameters according to business rules.
     * 
     * @param principal Must be >= 0
     * @param annualInterestRate Must be >= 0
     * @param years Must be > 0
     * @param compoundPeriodPerYear Must be >= 1
     * @throws InvalidLoanParameterException if any validation fails
     */
    private static void validateInputs(
            double principal, 
            double annualInterestRate, 
            int years, 
            int compoundPeriodPerYear) throws InvalidLoanParameterException {
        
        if (principal < 0) {
            throw new InvalidLoanParameterException(
                "Principal amount cannot be negative. Received: " + principal);
        }
        
        if (annualInterestRate < 0) {
            throw new InvalidLoanParameterException(
                "Annual interest rate cannot be negative. Received: " + annualInterestRate);
        }
        
        if (years <= 0) {
            throw new InvalidLoanParameterException(
                "Loan term (years) must be greater than 0. Received: " + years);
        }
        
        if (compoundPeriodPerYear < 1) {
            throw new InvalidLoanParameterException(
                "Compound period per year must be at least 1. Received: " + compoundPeriodPerYear);
        }
    }
    
    /**
     * Rounds a double value to 2 decimal places.
     * 
     * @param value The value to round
     * @return The rounded value
     */
    private static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
