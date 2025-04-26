package com.tennisclub.model;

import com.tennisclub.model.enums.DisputeStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDisputeTest {

    private PaymentDispute dispute;

    @BeforeEach
    void setUp() {
        dispute = new PaymentDispute();
    }

    @Test
    void defaultConstructor_ShouldInitializeDefaults() {
        assertEquals(0, dispute.getDisputeId(),   "Default disputeId should be 0");
        assertNull(dispute.getReason(),           "Default reason should be null");
        assertEquals(DisputeStatus.OPEN, dispute.getStatus(),
                "Default status should be OPEN");
        assertNull(dispute.getTransaction(),      "Default transaction should be null");
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // dummy transaction entity
        FinancialTransaction tx = new FinancialTransaction();
        dispute.setDisputeId(123);
        dispute.setReason("Incorrect charge");
        dispute.setTransaction(tx);
        dispute.setStatus(DisputeStatus.RESOLVED);

        assertEquals(123, dispute.getDisputeId());
        assertEquals("Incorrect charge", dispute.getReason());
        assertSame(tx, dispute.getTransaction());
        assertEquals(DisputeStatus.RESOLVED, dispute.getStatus());
    }

    @Test
    void resolveDispute_WithValidResolution_ShouldReturnTrueAndSetStatusResolved() {
        boolean result = dispute.resolveDispute("Customer provided proof of return");
        assertTrue(result, "Valid resolution should return true");
        assertEquals(DisputeStatus.RESOLVED, dispute.getStatus(),
                "Status should be updated to RESOLVED");
    }

    @Test
    void resolveDispute_WithNullOrBlankResolution_ShouldReturnFalseAndKeepStatusOpen() {
        // case: null
        boolean nullResult = dispute.resolveDispute(null);
        assertFalse(nullResult, "Null resolution should return false");
        assertEquals(DisputeStatus.OPEN, dispute.getStatus(),
                "Status should remain OPEN after null resolution");

        // case: blank
        boolean blankResult = dispute.resolveDispute("   ");
        assertFalse(blankResult, "Blank resolution should return false");
        assertEquals(DisputeStatus.OPEN, dispute.getStatus(),
                "Status should remain OPEN after blank resolution");
    }
}
