package loan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV Test Data Validator
 * 
 * Reads test data from CSV file and validates against the calculator implementation.
 * Generates a detailed report comparing expected results with actual results.
 * 
 * @author Banking System Developer
 * @version 1.0
 */
public class CsvTestValidator {
    
    private static class TestCase {
        String testCase;
        String description;
        Double principal;
        Double annualInterestRate;
        Integer years;
        Integer compoundPeriodPerYear;
        String expectedResult;
        String resultWeb;
        String testCategory;
        String actualResult;
        boolean passed;
        String errorMessage;
    }
    
    public static void main(String[] args) {
        String csvFile = "test/loan/test-data-deferred-loan.csv";
        String reportFile = "test/loan/test-validation-report.html";
        
        List<TestCase> testCases = loadTestCases(csvFile);
        runTests(testCases);
        generateHtmlReport(testCases, reportFile);
        printSummary(testCases);
    }
    
    private static List<TestCase> loadTestCases(String csvFile) {
        List<TestCase> testCases = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isHeader = true;
            
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                
                String[] values = line.split(",");
                TestCase tc = new TestCase();
                tc.testCase = values[0];
                tc.description = values[1];
                tc.principal = parseDoubleOrNull(values[2]);
                tc.annualInterestRate = parseDoubleOrNull(values[3]);
                tc.years = parseIntOrNull(values[4]);
                tc.compoundPeriodPerYear = parseIntOrNull(values[5]);
                tc.expectedResult = values[6];
                tc.resultWeb = values[7];
                tc.testCategory = values[8];
                
                testCases.add(tc);
            }
            
            System.out.println("Loaded " + testCases.size() + " test cases from CSV");
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        
        return testCases;
    }
    
    private static void runTests(List<TestCase> testCases) {
        int testNumber = 0;
        for (TestCase tc : testCases) {
            testNumber++;
            System.out.printf("Running test %d/%d: %s... ", testNumber, testCases.size(), tc.testCase);
            
            try {
                if (tc.expectedResult.equals("EXCEPTION")) {
                    // Expected to throw exception
                    try {
                        double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
                            tc.principal, tc.annualInterestRate, tc.years, tc.compoundPeriodPerYear);
                        tc.actualResult = String.format("%.2f", result);
                        tc.passed = false;
                        tc.errorMessage = "Expected exception but got result: " + tc.actualResult;
                        System.out.println("FAILED");
                    } catch (InvalidLoanParameterException e) {
                        tc.actualResult = "EXCEPTION";
                        tc.passed = true;
                        tc.errorMessage = "Correctly threw: " + e.getMessage();
                        System.out.println("PASSED");
                    }
                } else {
                    // Expected valid result
                    double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
                        tc.principal, tc.annualInterestRate, tc.years, tc.compoundPeriodPerYear);
                    tc.actualResult = String.format("%.2f", result);
                    
                    double expected = Double.parseDouble(tc.expectedResult);
                    double delta = Math.abs(result - expected);
                    tc.passed = (delta < 0.01);
                    
                    if (tc.passed) {
                        System.out.println("PASSED");
                    } else {
                        tc.errorMessage = String.format("Delta: %.4f", delta);
                        System.out.println("FAILED - " + tc.errorMessage);
                    }
                }
            } catch (Exception e) {
                tc.actualResult = "ERROR";
                tc.passed = false;
                tc.errorMessage = e.getMessage();
                System.out.println("ERROR - " + e.getMessage());
            }
        }
    }
    
    private static void generateHtmlReport(List<TestCase> testCases, String reportFile) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(reportFile))) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html><head><meta charset='UTF-8'>");
            writer.println("<title>Deferred Payment Loan Test Report</title>");
            writer.println("<style>");
            writer.println("body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }");
            writer.println("h1 { color: #1976D2; }");
            writer.println(".summary { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; }");
            writer.println(".summary-box { display: inline-block; margin: 10px; padding: 15px; border-radius: 5px; min-width: 150px; text-align: center; }");
            writer.println(".total { background: #E3F2FD; }");
            writer.println(".passed { background: #C8E6C9; }");
            writer.println(".failed { background: #FFCDD2; }");
            writer.println("table { width: 100%; border-collapse: collapse; background: white; }");
            writer.println("th { background: #1976D2; color: white; padding: 12px; text-align: left; }");
            writer.println("td { padding: 10px; border-bottom: 1px solid #ddd; }");
            writer.println("tr:hover { background: #f5f5f5; }");
            writer.println(".pass { color: #4CAF50; font-weight: bold; }");
            writer.println(".fail { color: #F44336; font-weight: bold; }");
            writer.println(".category { font-size: 0.9em; color: #666; }");
            writer.println("</style></head><body>");
            
            writer.println("<h1>🏦 Deferred Payment Loan Calculator - Test Report</h1>");
            
            // Summary
            long passed = testCases.stream().filter(tc -> tc.passed).count();
            long failed = testCases.stream().filter(tc -> !tc.passed).count();
            double passRate = (passed * 100.0) / testCases.size();
            
            writer.println("<div class='summary'>");
            writer.println("<h2>Test Summary</h2>");
            writer.printf("<div class='summary-box total'><h3>%d</h3><p>Total Tests</p></div>%n", testCases.size());
            writer.printf("<div class='summary-box passed'><h3>%d</h3><p>Passed</p></div>%n", passed);
            writer.printf("<div class='summary-box failed'><h3>%d</h3><p>Failed</p></div>%n", failed);
            writer.printf("<div class='summary-box'><h3>%.1f%%</h3><p>Pass Rate</p></div>%n", passRate);
            writer.println("</div>");
            
            // Test Cases Table
            writer.println("<table>");
            writer.println("<tr>");
            writer.println("<th>Test ID</th><th>Description</th><th>Category</th>");
            writer.println("<th>Expected</th><th>Actual</th><th>Web Result</th><th>Status</th><th>Notes</th>");
            writer.println("</tr>");
            
            for (TestCase tc : testCases) {
                writer.println("<tr>");
                writer.printf("<td><b>%s</b></td>%n", tc.testCase);
                writer.printf("<td>%s</td>%n", tc.description);
                writer.printf("<td class='category'>%s</td>%n", tc.testCategory);
                writer.printf("<td>%s</td>%n", tc.expectedResult);
                writer.printf("<td>%s</td>%n", tc.actualResult != null ? tc.actualResult : "N/A");
                writer.printf("<td>%s</td>%n", tc.resultWeb);
                writer.printf("<td class='%s'>%s</td>%n", 
                    tc.passed ? "pass" : "fail", 
                    tc.passed ? "✓ PASS" : "✗ FAIL");
                writer.printf("<td>%s</td>%n", tc.errorMessage != null ? tc.errorMessage : "");
                writer.println("</tr>");
            }
            
            writer.println("</table>");
            writer.println("<br><p><i>Generated: " + java.time.LocalDateTime.now() + "</i></p>");
            writer.println("</body></html>");
            
            System.out.println("\nHTML Report generated: " + reportFile);
        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }
    
    private static void printSummary(List<TestCase> testCases) {
        long passed = testCases.stream().filter(tc -> tc.passed).count();
        long failed = testCases.stream().filter(tc -> !tc.passed).count();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST SUMMARY");
        System.out.println("=".repeat(60));
        System.out.printf("Total Tests:  %d%n", testCases.size());
        System.out.printf("Passed:       %d ✓%n", passed);
        System.out.printf("Failed:       %d ✗%n", failed);
        System.out.printf("Pass Rate:    %.1f%%%n", (passed * 100.0) / testCases.size());
        System.out.println("=".repeat(60));
        
        if (failed > 0) {
            System.out.println("\nFailed Tests:");
            testCases.stream()
                .filter(tc -> !tc.passed)
                .forEach(tc -> System.out.printf("  - %s: %s%n", tc.testCase, tc.description));
        }
    }
    
    private static Double parseDoubleOrNull(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private static Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
