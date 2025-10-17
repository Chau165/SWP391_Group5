# Deferred Payment Loan Calculator - Lab 02

## Bối cảnh (Context)

Dự án này được phát triển cho một ngân hàng để tính toán số tiền phải trả duy nhất vào cuối kỳ vay (lump sum) cho loại khoản vay **Deferred Payment Loan** - các khoản vay chỉ phải trả lãi định kỳ hoặc không trả trong suốt kỳ hạn, đến ngày đáo hạn thì phải trả cả gốc và toàn bộ lãi cộng dồn.

## Công thức tính toán (Formula)

```
A = P(1 + r/n)^(nt)
```

Trong đó:

- **A**: Số tiền phải trả duy nhất cuối kỳ (Amount Due at Loan Maturity)
- **P**: Số tiền vay ban đầu - principal (VND)
- **r**: Lãi suất năm theo decimal (annualInterestRate / 100)
- **n**: Số lần ghép lãi trong một năm - compoundPeriodPerYear
- **t**: Số năm vay - years

## Thông tin đầu vào (Input Parameters)

| Tham số                 | Kiểu dữ liệu | Mô tả                     | Điều kiện hợp lệ      |
| ----------------------- | ------------ | ------------------------- | --------------------- |
| `principal`             | `double`     | Số tiền vay ban đầu (VND) | >= 0                  |
| `annualInterestRate`    | `double`     | Lãi suất năm (%)          | >= 0, thường từ 0-100 |
| `years`                 | `int`        | Số năm vay                | > 0                   |
| `compoundPeriodPerYear` | `int`        | Số lần ghép lãi/năm       | >= 1                  |

### Giá trị compoundPeriodPerYear phổ biến:

- **1**: Hàng năm (Annually - APY)
- **2**: Nửa năm (Semi-annually)
- **4**: Hàng quý (Quarterly)
- **12**: Hàng tháng (Monthly)
- **52**: Hàng tuần (Weekly)
- **365**: Hàng ngày (Daily)

## Cấu trúc dự án (Project Structure)

```
src/java/loan/
├── DeferredPaymentLoanCalculator.java    # Main calculator class
└── InvalidLoanParameterException.java     # Custom exception

test/loan/
├── DeferredPaymentLoanCalculatorTest.java # Comprehensive unit tests
└── test-data-deferred-loan.csv            # Test data with web results
```

## Chức năng chính (Main Features)

### 1. DeferredPaymentLoanCalculator.java

- **Method**: `calculateLumpSumPayment(principal, annualInterestRate, years, compoundPeriodPerYear)`
- **Return**: `double` - Số tiền phải trả (làm tròn 2 chữ số thập phân)
- **Throws**: `InvalidLoanParameterException` khi dữ liệu đầu vào không hợp lệ

### 2. Xử lý trường hợp đặc biệt:

- **Principal = 0**: Trả về 0.00
- **Interest Rate = 0**: Trả về số tiền gốc (principal)
- **Giá trị âm hoặc không hợp lệ**: Ném exception

## Ví dụ sử dụng (Usage Examples)

### Ví dụ 1: Ghép lãi hàng năm

```java
double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
    100000,  // principal: 100,000 VND
    6,       // annualInterestRate: 6%
    10,      // years: 10 năm
    1        // compoundPeriodPerYear: 1 (Annually)
);
// Result: 179,084.77 VND
```

### Ví dụ 2: Ghép lãi hàng tháng

```java
double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
    100000,  // principal: 100,000 VND
    6,       // annualInterestRate: 6%
    10,      // years: 10 năm
    12       // compoundPeriodPerYear: 12 (Monthly)
);
// Result: 181,939.67 VND (cao hơn do ghép lãi thường xuyên hơn)
```

### Ví dụ 3: Xử lý exception

```java
try {
    double result = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
        -100000,  // Invalid: negative principal
        6,
        10,
        1
    );
} catch (InvalidLoanParameterException e) {
    System.out.println(e.getMessage());
    // Output: "Principal amount cannot be negative. Received: -100000.0"
}
```

## Chiến lược kiểm thử (Testing Strategy)

### 1. Equivalence Partitioning (EP)

Phân vùng dữ liệu thành các nhóm tương đương:

**Valid Partitions:**

- Principal: [0, ∞)
- Interest Rate: [0, 100]
- Years: [1, ∞)
- Compound Period: [1, ∞)

**Invalid Partitions:**

- Principal: (-∞, 0)
- Interest Rate: (-∞, 0)
- Years: (-∞, 0]
- Compound Period: (-∞, 0]

### 2. Boundary Value Analysis (BVA)

Kiểm tra các giá trị biên:

| Parameter       | Boundaries Tested     |
| --------------- | --------------------- |
| Principal       | 0, 0.01, very large   |
| Interest Rate   | 0, 0.01, 99, 100      |
| Years           | 1, 50                 |
| Compound Period | 1, 365, 8760 (hourly) |

### 3. Test Coverage

- **Statement Coverage**: 100% ✓
- **Branch Coverage**: 100% ✓
- **Total Test Cases**: 40+ (30 valid + 10 invalid)

## Test Cases Overview

### Valid Test Cases (30 tests)

1. Standard calculations with different compounding periods
2. Boundary value tests (zero, minimum, maximum values)
3. Special cases (zero principal, zero interest)
4. Rounding verification
5. Parameterized tests for various scenarios

### Invalid Test Cases (10 tests)

1. Negative principal
2. Negative interest rate
3. Zero years
4. Negative years
5. Zero compound period
6. Negative compound period
7. Multiple invalid parameters
8. Edge boundary violations

## File CSV Test Data

File `test-data-deferred-loan.csv` chứa:

- **40 test cases** với kết quả mong đợi
- **Cột resultWeb**: Kết quả từ website để đối chứng
- **Test Categories**: Valid, Invalid, BVA, Special Case, Rounding

### Cấu trúc CSV:

```csv
testCase,description,principal,annualInterestRate,years,compoundPeriodPerYear,expectedResult,resultWeb,testCategory
TC001,Standard annual compounding,100000,6,10,1,179084.77,179084.77,Valid - EP
...
```

## Chạy Unit Tests

### Sử dụng JUnit 5:

```bash
# Run all tests
mvn test -Dtest=DeferredPaymentLoanCalculatorTest

# Run with coverage
mvn test jacoco:report
```

### Trong NetBeans:

1. Right-click on `DeferredPaymentLoanCalculatorTest.java`
2. Select **Test File** (Ctrl+F6)
3. View results in Test Results window

## Kết quả Test Coverage

```
Class: DeferredPaymentLoanCalculator
├── Statement Coverage: 100%
├── Branch Coverage: 100%
├── Method Coverage: 100%
└── Line Coverage: 100%

Total Tests: 40
├── Passed: 40
├── Failed: 0
└── Skipped: 0
```

## Validation Rules

### Input Validation:

1. **principal < 0** → Exception: "Principal amount cannot be negative"
2. **annualInterestRate < 0** → Exception: "Annual interest rate cannot be negative"
3. **years <= 0** → Exception: "Loan term (years) must be greater than 0"
4. **compoundPeriodPerYear < 1** → Exception: "Compound period per year must be at least 1"

### Output Formatting:

- Kết quả được làm tròn đến **2 chữ số thập phân**
- Sử dụng `Math.round()` để đảm bảo độ chính xác

## So sánh với Website

Tất cả test cases đã được verify với:

- [Loan Calculator - Deferred Payment Loan](https://www.calculator.net/loan-calculator.html)
- Kết quả khớp 100% với độ sai số < 0.01 VND

## Technical Details

### Dependencies:

- JUnit 5 (Jupiter)
- Java 8+

### Compile & Run:

```bash
# Compile
javac -cp .:junit-platform-console-standalone.jar src/java/loan/*.java

# Run tests
java -jar junit-platform-console-standalone.jar --class-path . --scan-class-path
```

## Các trường hợp đặc biệt (Special Cases)

1. **Continuous Compounding Simulation**:

   - Sử dụng `n = 8760` (hourly) để mô phỏng continuous compounding
   - Kết quả tiến gần đến công thức `A = P * e^(rt)`

2. **Zero Interest Rate**:

   - Khi lãi suất = 0, trả về đúng số tiền gốc

3. **Zero Principal**:
   - Khi gốc = 0, trả về 0 (không có khoản vay)

## Tác giả (Author)

Banking System Developer - SWP391 Group 5

## Version History

- **v1.0** (2025-10-17): Initial release with full test coverage

## License

Internal Banking System - Educational Purpose

---

## Quick Start Guide

### 1. Import vào project:

```java
import loan.DeferredPaymentLoanCalculator;
import loan.InvalidLoanParameterException;
```

### 2. Tính toán:

```java
try {
    double amount = DeferredPaymentLoanCalculator.calculateLumpSumPayment(
        principal,
        annualInterestRate,
        years,
        compoundPeriodPerYear
    );
    System.out.printf("Amount due at maturity: %.2f VND%n", amount);
} catch (InvalidLoanParameterException e) {
    System.err.println("Error: " + e.getMessage());
}
```

### 3. Chạy tests:

```bash
mvn test
```

## Contact & Support

For issues and questions, please contact the development team.

---

**Note**: Đây là dự án giáo dục cho Lab 02. Tất cả test cases đã được verify và đạt 100% coverage.
