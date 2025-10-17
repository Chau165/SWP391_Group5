package loan;

/**
 * Custom exception for invalid loan parameters.
 * 
 * This exception is thrown when input parameters for loan calculations
 * do not meet the required business rules and validation criteria.
 * 
 * @author Banking System Developer
 * @version 1.0
 */
public class InvalidLoanParameterException extends Exception {
    
    /**
     * Constructs a new InvalidLoanParameterException with the specified detail message.
     * 
     * @param message The detail message explaining why the parameters are invalid
     */
    public InvalidLoanParameterException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new InvalidLoanParameterException with the specified detail message
     * and cause.
     * 
     * @param message The detail message
     * @param cause The cause of the exception
     */
    public InvalidLoanParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
