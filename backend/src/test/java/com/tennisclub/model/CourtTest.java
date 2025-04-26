package com.tennisclub.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourtTest {

    @Test
    void noArgsConstructor_ShouldInitializeDefaults() {
        Court court = new Court();
        // default courtNumber is 0, default isAvailable is true
        assertEquals(0, court.getCourtNumber(), "Default courtNumber should be 0");
        assertTrue(court.isAvailable(), "Default availability should be true");
        assertTrue(court.checkAvailability(), "checkAvailability() should return true by default");
    }

    @Test
    void allArgsConstructor_ShouldSetFieldsCorrectly() {
        Court court = new Court(5, false);
        assertEquals(5, court.getCourtNumber(), "Constructor should set courtNumber");
        assertFalse(court.isAvailable(), "Constructor should set isAvailable");
        assertFalse(court.checkAvailability(), "checkAvailability() should reflect initial state");
    }

    @Test
    void gettersAndSetters_ShouldModifyAndReturnValues() {
        Court court = new Court();

        court.setCourtNumber(12);
        court.setAvailable(false);

        assertEquals(12, court.getCourtNumber(), "getCourtNumber() should return what was set");
        assertFalse(court.isAvailable(), "isAvailable() should return what was set");
    }

    @Test
    void updateAvailability_ShouldToggleAvailabilityCorrectly() {
        Court court = new Court(1, true);

        // flip to false
        court.updateAvailability(false);
        assertFalse(court.checkAvailability(), "After updateAvailability(false), availability should be false");

        // flip back to true
        court.updateAvailability(true);
        assertTrue(court.checkAvailability(), "After updateAvailability(true), availability should be true");
    }
}
