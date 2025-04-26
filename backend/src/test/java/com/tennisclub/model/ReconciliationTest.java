package com.tennisclub.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReconciliationTest {

    private Reconciliation reconciliation;
    private FinancialTransaction tx1;
    private FinancialTransaction tx2;
    private FinancialTransaction tx3;
    private Date today;

    @BeforeEach
    void setUp() {
        reconciliation = new Reconciliation();
        today = new Date();

        // Common dummy transactions
        tx1 = new FinancialTransaction();
        tx1.setTransactionId(1);
        tx1.setTransactionType("DEBIT");
        tx1.setAmount(new BigDecimal("100.00"));
        tx1.setTransactionDate(today);

        tx2 = new FinancialTransaction();
        tx2.setTransactionId(2);
        tx2.setTransactionType("CREDIT");
        tx2.setAmount(new BigDecimal("50.00"));
        tx2.setTransactionDate(today);

        tx3 = new FinancialTransaction();
        tx3.setTransactionId(3);
        tx3.setTransactionType("DEBIT");
        // will adjust amount/date per test
    }

    @Test
    void noArgsConstructor_ShouldInitializeEmptyDiscrepanciesList() {
        assertNotNull(reconciliation.getDiscrepancies(), "Discrepancies list must never be null");
        assertTrue(reconciliation.getDiscrepancies().isEmpty(), "Should start empty");
    }

    @Test
    void allArgsConstructor_ShouldUseProvidedList() {
        List<String> custom = Arrays.asList("foo", "bar");
        reconciliation = new Reconciliation(custom);
        assertSame(custom, reconciliation.getDiscrepancies(), "Should use the list passed into constructor");
    }

    @Test
    void reconcileTransactions_EmptyInternalAndExternal_ReturnsEmpty() {
        List<String> result = reconciliation.reconcileTransactions(
                Arrays.asList(), Arrays.asList()
        );
        assertTrue(result.isEmpty(), "No transactions → no discrepancies");
    }

    @Test
    void reconcileTransactions_MatchingTransactions_NoDiscrepancies() {
        // internal and external share tx1
        List<String> result = reconciliation.reconcileTransactions(
                Arrays.asList(tx1),
                Arrays.asList(tx1)
        );
        assertTrue(result.isEmpty(), "Exact match → no discrepancies");
    }

    @Test
    void reconcileTransactions_UnmatchedInternal_FlagsMissingExternal() {
        // internal has tx1, external empty
        List<String> result = reconciliation.reconcileTransactions(
                Arrays.asList(tx1),
                Arrays.asList()
        );
        assertEquals(1, result.size());
        assertTrue(result.get(0).startsWith("Missing external record for internal transaction ID 1"),
                "Should call out missing external record for tx1");
    }

    @Test
    void reconcileTransactions_UnmatchedExternal_FlagsMissingInternal() {
        // internal empty, external has tx2
        List<String> result = reconciliation.reconcileTransactions(
                Arrays.asList(),
                Arrays.asList(tx2)
        );
        assertEquals(1, result.size());
        assertTrue(result.get(0).startsWith("Missing internal record for external transaction (Amount: " + tx2.getAmount()),
                "Should call out missing internal record for tx2");
    }

    @Test
    void reconcileTransactions_AmountWithinTolerance_StillMatches() {
        // tx1 vs external with amount diff = 0.009
        FinancialTransaction ext = new FinancialTransaction();
        ext.setTransactionId(99);
        ext.setTransactionType("DEBIT");
        ext.setAmount(new BigDecimal("100.009")); // within 0.01
        ext.setTransactionDate(today);

        List<String> result = reconciliation.reconcileTransactions(
                Arrays.asList(tx1),
                Arrays.asList(ext)
        );
        assertTrue(result.isEmpty(), "Difference of .009 ≤ tolerance → should match");
    }

    @Test
    void reconcileTransactions_AmountOutsideTolerance_DoesNotMatch() {
        // tx1 vs external with amount diff = 0.02
        FinancialTransaction ext = new FinancialTransaction();
        ext.setTransactionId(100);
        ext.setTransactionType("DEBIT");
        ext.setAmount(new BigDecimal("100.02")); // > 0.01
        ext.setTransactionDate(today);

        List<String> result = reconciliation.reconcileTransactions(
                Arrays.asList(tx1),
                Arrays.asList(ext)
        );
        assertEquals(2, result.size(),
                "Internal tx1 has no external match AND external ext has no internal match");
        assertTrue(result.get(0).contains("Missing external record for internal transaction ID 1"));
        assertTrue(result.get(1).contains("Missing internal record for external transaction"));
    }

    @Test
    void reconcileTransactions_DateMismatch_DoesNotMatch() {
        // tx1 vs external same type/amount but different date
        FinancialTransaction ext = new FinancialTransaction();
        ext.setTransactionType("DEBIT");
        ext.setAmount(tx1.getAmount());
        ext.setTransactionDate(new Date(today.getTime() + 86400000L)); // +1 day

        List<String> result = reconciliation.reconcileTransactions(
                Arrays.asList(tx1),
                Arrays.asList(ext)
        );
        assertEquals(2, result.size(),
                "Date mismatch → treated as unmatched both ways");
    }
}
