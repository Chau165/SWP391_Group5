package loan;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for DeferredPaymentLoanCalculator.
 * 
 * Test Coverage Strategy:
 * - Equivalence Partitioning (EP): Valid and invalid partitions for each parameter
 * - Boundary Value Analysis (BVA): Testing at boundaries and just beyond
 * - Statement Coverage: All code statements executed
 * - Branch Coverage: All decision branches tested
 * 
 * Test Categories:
 * 1. Valid calculations (EP - valid partition)
 * 2. Boundary value tests (BVA)
 * 3. Special cases (zero values, edge cases)
 * 4. Invalid inputs (EP - invalid partition)
 * 5. Different compounding periods
 * 
 * @author Banking System Developer
 * @version 1.0
 */
@DisplayName("Deferred Payment Loan Calculator Tests")
public class DeferredPaymentLoanCalculatorTest {
    
    private static final double DELTA = 0.01; // Tolerance for floating-point comparison
    
    // ==================== VALID CALCULATION TESTS ====================
    
    @Test
    @DisplayName("Test Example 1: 100,000 VND at 6% for 10 years, compounded annually")
    public void testExample1_AnnualCompounding() throws InvalidLoanParameterException {
        // Given: Principal = 100,000; Rate = 6%; Years = 10; Compound = 1 (Annually)
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then: Expected ~179,084.77
        assertEquals(179084.77, result, DELTA, 
            "Annual compounding should yield approximately 179,084.77");
    }
    
    @Test
    @DisplayName("Test Example 2: 100,000 VND at 6% for 10 years, compounded monthly")
    public void testExample2_MonthlyCompounding() throws InvalidLoanParameterException {
        // Given: Principal = 100,000; Rate = 6%; Years = 10; Compound = 12 (Monthly)
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 12;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then: Expected ~181,939.67 (higher than annual due to more frequent compounding)
        assertEquals(181939.67, result, DELTA, 
            "Monthly compounding should yield higher amount than annual");
        assertTrue(result > 179084.77, 
            "Monthly compounding should be greater than annual compounding");
    }
    
    @Test
    @DisplayName("Test with daily compounding (365 periods per year)")
    public void testDailyCompounding() throws InvalidLoanParameterException {
        // Given
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 365;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then: Should be even higher than monthly
        assertTrue(result > 181939.67, 
            "Daily compounding should be greater than monthly compounding");
        assertEquals(182211.88, result, DELTA);
    }
    
    @Test
    @DisplayName("Test with quarterly compounding (4 periods per year)")
    public void testQuarterlyCompounding() throws InvalidLoanParameterException {
        // Given
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 4;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertEquals(180611.23, result, DELTA,
            "Quarterly compounding calculation should be correct");
    }
    
    // ==================== BOUNDARY VALUE ANALYSIS TESTS ====================
    
    @Test
    @DisplayName("BVA: Principal = 0 should return 0")
    public void testBVA_PrincipalZero() throws InvalidLoanParameterException {
        // Given: Principal = 0 (boundary value)
        double principal = 0;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertEquals(0.0, result, DELTA, 
            "Zero principal should return zero payment");
    }
    
    @Test
    @DisplayName("BVA: Interest rate = 0 should return principal only")
    public void testBVA_InterestRateZero() throws InvalidLoanParameterException {
        // Given: Interest rate = 0 (boundary value)
        double principal = 100000;
        double annualInterestRate = 0;
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertEquals(100000.0, result, DELTA, 
            "Zero interest rate should return principal amount only");
    }
    
    @Test
    @DisplayName("BVA: Years = 1 (minimum valid boundary)")
    public void testBVA_MinimumYears() throws InvalidLoanParameterException {
        // Given: Years = 1 (minimum boundary)
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 1;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then: 100000 * (1.06)^1 = 106000
        assertEquals(106000.0, result, DELTA,
            "One year loan calculation should be correct");
    }
    
    @Test
    @DisplayName("BVA: Compound period = 1 (minimum valid boundary)")
    public void testBVA_MinimumCompoundPeriod() throws InvalidLoanParameterException {
        // Given: Compound period = 1 (minimum boundary)
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertNotNull(result);
        assertTrue(result > principal, 
            "Result should be greater than principal with positive interest");
    }
    
    @Test
    @DisplayName("BVA: Very small principal (0.01)")
    public void testBVA_VerySmallPrincipal() throws InvalidLoanParameterException {
        // Given
        double principal = 0.01;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertTrue(result > 0, "Very small principal should still calculate");
        assertEquals(0.02, result, 0.001);
    }
    
    @Test
    @DisplayName("BVA: Very large principal")
    public void testBVA_VeryLargePrincipal() throws InvalidLoanParameterException {
        // Given
        double principal = 1000000000; // 1 billion
        double annualInterestRate = 5;
        int years = 5;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertTrue(result > principal, "Large principal should calculate correctly");
        assertEquals(1276281563.00, result, 1.0);
    }
    
    @Test
    @DisplayName("BVA: Very high interest rate (99%)")
    public void testBVA_VeryHighInterestRate() throws InvalidLoanParameterException {
        // Given
        double principal = 100000;
        double annualInterestRate = 99;
        int years = 5;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertTrue(result > principal * 10, 
            "Very high interest rate should compound significantly");
    }
    
    @Test
    @DisplayName("BVA: Very long term (50 years)")
    public void testBVA_VeryLongTerm() throws InvalidLoanParameterException {
        // Given
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 50;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertTrue(result > principal * 15, 
            "Long term should compound significantly");
        assertEquals(1842019.79, result, 1.0);
    }
    
    // ==================== SPECIAL CASES ====================
    
    @Test
    @DisplayName("Special Case: Both principal and interest rate are 0")
    public void testSpecialCase_BothZero() throws InvalidLoanParameterException {
        // Given
        double principal = 0;
        double annualInterestRate = 0;
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertEquals(0.0, result, DELTA, 
            "Zero principal with zero interest should return zero");
    }
    
    @Test
    @DisplayName("Special Case: Very small interest rate (0.01%)")
    public void testSpecialCase_VerySmallInterestRate() throws InvalidLoanParameterException {
        // Given
        double principal = 100000;
        double annualInterestRate = 0.01;
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then
        assertTrue(result > principal, "Even tiny interest should compound");
        assertTrue(result < principal * 1.01, "Very small interest shouldn't compound much");
    }
    
    @Test
    @DisplayName("Special Case: Continuous compounding simulation (n=365*24)")
    public void testSpecialCase_ContinuousCompounding() throws InvalidLoanParameterException {
        // Given: Simulating continuous compounding with hourly periods
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 365 * 24; // Hourly
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then: Should approach e^(rt) = 100000 * e^(0.06*10) ≈ 182211.88
        assertTrue(result > 182200, "Continuous compounding should approach e^rt");
    }
    
    // ==================== INVALID INPUT TESTS (EXCEPTION HANDLING) ====================
    
    @Test
    @DisplayName("Invalid: Negative principal should throw exception")
    public void testInvalid_NegativePrincipal() {
        // Given: Invalid negative principal
        double principal = -100000;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When & Then
        InvalidLoanParameterException exception = assertThrows(
            InvalidLoanParameterException.class,
            () -> DeferredPaymentLoanCalculator.calculateLumpSumPayment(
                principal, annualInterestRate, years, compoundPeriodPerYear),
            "Negative principal should throw InvalidLoanParameterException"
        );
        
        assertTrue(exception.getMessage().contains("Principal"),
            "Exception message should mention principal");
        assertTrue(exception.getMessage().contains("negative"),
            "Exception message should mention negative");
    }
    
    @Test
    @DisplayName("Invalid: Negative interest rate should throw exception")
    public void testInvalid_NegativeInterestRate() {
        // Given: Invalid negative interest rate
        double principal = 100000;
        double annualInterestRate = -6;
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When & Then
        InvalidLoanParameterException exception = assertThrows(
            InvalidLoanParameterException.class,
            () -> DeferredPaymentLoanCalculator.calculateLumpSumPayment(
                principal, annualInterestRate, years, compoundPeriodPerYear),
            "Negative interest rate should throw InvalidLoanParameterException"
        );
        
        assertTrue(exception.getMessage().contains("interest rate"),
            "Exception message should mention interest rate");
    }
    
    @Test
    @DisplayName("Invalid: Zero years should throw exception")
    public void testInvalid_ZeroYears() {
        // Given: Invalid zero years
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 0;
        int compoundPeriodPerYear = 1;
        
        // When & Then
        InvalidLoanParameterException exception = assertThrows(
            InvalidLoanParameterException.class,
            () -> DeferredPaymentLoanCalculator.calculateLumpSumPayment(
                principal, annualInterestRate, years, compoundPeriodPerYear),
            "Zero years should throw InvalidLoanParameterException"
        );
        
        assertTrue(exception.getMessage().contains("years"),
            "Exception message should mention years");
    }
    
    @Test
    @DisplayName("Invalid: Negative years should throw exception")
    public void testInvalid_NegativeYears() {
        // Given: Invalid negative years
        double principal = 100000;
        double annualInterestRate = 6;
        int years = -10;
        int compoundPeriodPerYear = 1;
        
        // When & Then
        assertThrows(
            InvalidLoanParameterException.class,
            () -> DeferredPaymentLoanCalculator.calculateLumpSumPayment(
                principal, annualInterestRate, years, compoundPeriodPerYear),
            "Negative years should throw InvalidLoanParameterException"
        );
    }
    
    @Test
    @DisplayName("Invalid: Zero compound period should throw exception")
    public void testInvalid_ZeroCompoundPeriod() {
        // Given: Invalid zero compound period
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = 0;
        
        // When & Then
        InvalidLoanParameterException exception = assertThrows(
            InvalidLoanParameterException.class,
            () -> DeferredPaymentLoanCalculator.calculateLumpSumPayment(
                principal, annualInterestRate, years, compoundPeriodPerYear),
            "Zero compound period should throw InvalidLoanParameterException"
        );
        
        assertTrue(exception.getMessage().contains("Compound period"),
            "Exception message should mention compound period");
    }
    
    @Test
    @DisplayName("Invalid: Negative compound period should throw exception")
    public void testInvalid_NegativeCompoundPeriod() {
        // Given: Invalid negative compound period
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 10;
        int compoundPeriodPerYear = -1;
        
        // When & Then
        assertThrows(
            InvalidLoanParameterException.class,
            () -> DeferredPaymentLoanCalculator.calculateLumpSumPayment(
                principal, annualInterestRate, years, compoundPeriodPerYear),
            "Negative compound period should throw InvalidLoanParameterException"
        );
    }
    
    @Test
    @DisplayName("Invalid: Multiple invalid parameters")
    public void testInvalid_MultipleInvalidParameters() {
        // Given: Multiple invalid parameters
        double principal = -100000;
        double annualInterestRate = -6;
        int years = 0;
        int compoundPeriodPerYear = 0;
        
        // When & Then: Should catch the first validation error (principal)
        assertThrows(
            InvalidLoanParameterException.class,
            () -> DeferredPaymentLoanCalculator.calculateLumpSumPayment(
                principal, annualInterestRate, years, compoundPeriodPerYear),
            "Multiple invalid parameters should throw exception"
        );
    }
    
    // ==================== PARAMETERIZED TESTS ====================
    
    @ParameterizedTest
    @DisplayName("Parameterized: Various valid scenarios")
    @CsvSource({
        "100000, 6, 10, 1, 179084.77",    // Annual
        "100000, 6, 10, 12, 181939.67",   // Monthly
        "100000, 6, 10, 4, 180611.23",    // Quarterly
        "50000, 5, 5, 1, 63814.08",       // Different principal
        "200000, 7, 15, 12, 571749.77",   // Higher rate, longer term
        "10000, 3, 20, 1, 18061.11",      // Low rate, long term
        "1000000, 10, 1, 1, 1100000.00"   // 1 year only
    })
    public void testParameterized_ValidScenarios(
            double principal, 
            double rate, 
            int years, 
            int compound, 
            double expected) throws InvalidLoanParameterException {
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, rate, years, compound);
        
        // Then
        assertEquals(expected, result, DELTA,
            String.format("Failed for P=%f, r=%f, y=%d, n=%d", 
                principal, rate, years, compound));
    }
    
    @ParameterizedTest
    @DisplayName("Parameterized: Different compounding frequencies comparison")
    @CsvSource({
        "1",      // Annually
        "2",      // Semi-annually
        "4",      // Quarterly
        "12",     // Monthly
        "52",     // Weekly
        "365"     // Daily
    })
    public void testParameterized_CompoundingFrequencies(int compoundPeriod) 
            throws InvalidLoanParameterException {
        
        // Given: Same principal, rate, and years
        double principal = 100000;
        double annualInterestRate = 6;
        int years = 10;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriod);
        
        // Then: More frequent compounding should yield higher results
        assertTrue(result > principal, 
            "Result should be greater than principal for compound period: " + compoundPeriod);
        
        // Verify result increases with more frequent compounding
        if (compoundPeriod == 1) {
            assertEquals(179084.77, result, DELTA);
        } else if (compoundPeriod == 12) {
            assertEquals(181939.67, result, DELTA);
        } else if (compoundPeriod == 365) {
            assertEquals(182211.88, result, DELTA);
        }
    }
    
    // ==================== ROUNDING TESTS ====================
    
    @Test
    @DisplayName("Rounding: Result should be rounded to 2 decimal places")
    public void testRounding_TwoDecimalPlaces() throws InvalidLoanParameterException {
        // Given: Values that produce many decimal places
        double principal = 12345.67;
        double annualInterestRate = 7.89;
        int years = 8;
        int compoundPeriodPerYear = 12;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then: Check that result has at most 2 decimal places
        double rounded = Math.round(result * 100.0) / 100.0;
        assertEquals(rounded, result, 0.001,
            "Result should be rounded to 2 decimal places");
    }
    
    @Test
    @DisplayName("Rounding: Verify proper rounding behavior")
    public void testRounding_VerifyBehavior() throws InvalidLoanParameterException {
        // Given
        double principal = 100000;
        double annualInterestRate = 6.555; // Will produce number needing rounding
        int years = 10;
        int compoundPeriodPerYear = 1;
        
        // When
        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
            principal, annualInterestRate, years, compoundPeriodPerYear);
        
        // Then: Verify it's properly rounded
        String resultStr = String.format("%.2f", result);
        double reparsed = Double.parseDouble(resultStr);
        assertEquals(reparsed, result, 0.001,
            "Result should match when formatted to 2 decimals");
    }
}
