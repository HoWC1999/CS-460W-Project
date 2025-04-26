package com.tennisclub.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class GuestPassTest {

    private User dummyUser;
    private Date purchaseDate;
    private Date expirationDate;

    @BeforeEach
    void setUp() {
        // prepare a dummy User (assuming User has a no-args constructor)
        dummyUser = new User();
        // prepare some dates
        purchaseDate = new Date();
        expirationDate = new Date(purchaseDate.getTime() + 86_400_000L); // +1 day
    }

    @Test
    void noArgsConstructor_ShouldCreateNonNullObject() {
        GuestPass gp = new GuestPass();
        assertNotNull(gp, "No-args constructor should create an instance");
        // default primitive values
        assertEquals(0, gp.getGuestPassId());
        assertFalse(gp.isUsed(), "boolean 'used' should default to false");
        assertNull(gp.getUser(), "user should be null by default");
        assertEquals(0.0, gp.getPrice(), "price should default to 0.0");
        assertNull(gp.getPurchaseDate(), "purchaseDate should be null");
        assertNull(gp.getExpirationDate(), "expirationDate should be null");
    }

    @Test
    void allArgsConstructor_AndGetters_ShouldReturnExpectedValues() {
        GuestPass gp = new GuestPass(dummyUser, 25.50, purchaseDate, expirationDate, true);
        assertSame(dummyUser, gp.getUser(), "getUser() should return the same User instance");
        assertEquals(25.50, gp.getPrice(), "getPrice() should return the price set in constructor");
        assertEquals(purchaseDate, gp.getPurchaseDate(), "getPurchaseDate() mismatch");
        assertEquals(expirationDate, gp.getExpirationDate(), "getExpirationDate() mismatch");
        assertTrue(gp.isUsed(), "isUsed() should be true when set so");
    }

    @Test
    void setters_ShouldModifyFieldsCorrectly() {
        GuestPass gp = new GuestPass();
        gp.setGuestPassId(42);
        gp.setUser(dummyUser);
        gp.setPrice(10.0);
        gp.setPurchaseDate(purchaseDate);
        gp.setExpirationDate(expirationDate);
        gp.setUsed(true);

        assertEquals(42, gp.getGuestPassId());
        assertSame(dummyUser, gp.getUser());
        assertEquals(10.0, gp.getPrice());
        assertEquals(purchaseDate, gp.getPurchaseDate());
        assertEquals(expirationDate, gp.getExpirationDate());
        assertTrue(gp.isUsed());
    }
}