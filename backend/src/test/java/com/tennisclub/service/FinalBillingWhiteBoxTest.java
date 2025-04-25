package com.tennisclub.service;

import com.tennisclub.model.FinancialTransaction;
import com.tennisclub.model.enums.BillingPlan;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillingWhiteBoxTest {

    @Test
    public void testLateFeeCalculation() {
        FinancialService service = new FinancialService();
        FinancialTransaction transaction = service.applyLateFee(1, BigDecimal.valueOf(100));
        assertNotNull(transaction);
        assertEquals(BigDecimal.valueOf(110.00), transaction.getAmount()); // assuming 10% late fee
    }

    @Test
    public void testAnnualChargeProcessing() {
        FinancialService service = new FinancialService();
        FinancialTransaction transaction = service.chargeAnnualMembershipFee(1, BigDecimal.valueOf(200));
        assertNotNull(transaction);
        assertEquals(BigDecimal.valueOf(200), transaction.getAmount());
    }

    @Test
    public void testMonthlyChargeProcessing() {
        FinancialService service = new FinancialService();
        FinancialTransaction transaction = service.chargeAnnualMembershipFee(1, BigDecimal.valueOf(20)); // simulate monthly
        assertNotNull(transaction);
        assertEquals(BigDecimal.valueOf(20), transaction.getAmount());
    }

    @Test
    public void testGuestPassIssuance() {
        GuestPassService guestPassService = Mockito.mock(GuestPassService.class);
        when(guestPassService.issueGuestPass(1L)).thenReturn(true);
        boolean result = guestPassService.issueGuestPass(1L);
        assertTrue(result);
    }

    @Test
    public void testGuestPassInvalidUser() {
        GuestPassService guestPassService = Mockito.mock(GuestPassService.class);
        when(guestPassService.issueGuestPass(null)).thenReturn(false);
        boolean result = guestPassService.issueGuestPass(null);
        assertFalse(result);
    }
}
