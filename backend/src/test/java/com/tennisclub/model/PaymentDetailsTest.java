package com.tennisclub.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDetailsTest {

    private Date now;
    private Date futureDate;
    private Date pastDate;

    @BeforeEach
    void setUp() {
        now = new Date();
        futureDate = new Date(System.currentTimeMillis() + 86_400_000L);  // +1 day
        pastDate   = new Date(System.currentTimeMillis() - 86_400_000L);  // -1 day
    }

    @Test
    void noArgsConstructor_ShouldHaveDefaultValues() {
        PaymentDetails pd = new PaymentDetails();
        assertNull(pd.getPaymentMethod(), "paymentMethod should default to null");
        assertNull(pd.getCardNumber(),    "cardNumber should default to null");
        assertNull(pd.getExpiryDate(),    "expiryDate should default to null");
        assertEquals(0, pd.getCvv(),      "cvv should default to 0");
    }

    @Test
    void allArgsConstructor_AndGetters_ShouldReturnProvidedValues() {
        PaymentDetails pd = new PaymentDetails(
                "VISA",
                "4111111111111111",
                futureDate,
                123
        );

        assertEquals("VISA",               pd.getPaymentMethod());
        assertEquals("4111111111111111",   pd.getCardNumber());
        assertEquals(futureDate,           pd.getExpiryDate());
        assertEquals(123,                  pd.getCvv());
    }

    @Test
    void setters_ShouldModifyFieldsCorrectly() {
        PaymentDetails pd = new PaymentDetails();

        pd.setPaymentMethod("MasterCard");
        pd.setCardNumber("5500000000000004");
        pd.setExpiryDate(futureDate);
        pd.setCvv(456);

        assertEquals("MasterCard",         pd.getPaymentMethod());
        assertEquals("5500000000000004",   pd.getCardNumber());
        assertEquals(futureDate,           pd.getExpiryDate());
        assertEquals(456,                  pd.getCvv());
    }

    @Test
    void validateDetails_WithValidData_ReturnsTrue() {
        PaymentDetails pd = new PaymentDetails(
                "AMEX",
                "378282246310005",
                futureDate,
                789
        );
        assertTrue(pd.validateDetails(), "Should be valid when all fields are non-empty and expiry is in the future");
    }

    @Test
    void validateDetails_WithNullOrEmptyPaymentMethod_ReturnsFalse() {
        PaymentDetails pd1 = new PaymentDetails(null, "4111", futureDate, 111);
        assertFalse(pd1.validateDetails(), "Null paymentMethod should be invalid");

        PaymentDetails pd2 = new PaymentDetails("  ", "4111", futureDate, 111);
        assertFalse(pd2.validateDetails(), "Empty paymentMethod should be invalid");
    }

    @Test
    void validateDetails_WithNullOrEmptyCardNumber_ReturnsFalse() {
        PaymentDetails pd1 = new PaymentDetails("VISA", null, futureDate, 222);
        assertFalse(pd1.validateDetails(), "Null cardNumber should be invalid");

        PaymentDetails pd2 = new PaymentDetails("VISA", "   ", futureDate, 222);
        assertFalse(pd2.validateDetails(), "Empty cardNumber should be invalid");
    }

    @Test
    void validateDetails_WithNullOrPastExpiryDate_ReturnsFalse() {
        PaymentDetails pd1 = new PaymentDetails("VISA", "4111", null, 333);
        assertFalse(pd1.validateDetails(), "Null expiryDate should be invalid");

        PaymentDetails pd2 = new PaymentDetails("VISA", "4111", pastDate, 333);
        assertFalse(pd2.validateDetails(), "Past expiryDate should be invalid");
    }
}
