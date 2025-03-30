package com.tennisclub.model;

import java.util.Date;
import java.sql.Time;

public class CourtReservation {
    private int reservationId;
    private Date reservationDate;
    private Time startTime;
    private Time endTime;
    private User bookedBy;
    private Court court;
    
    // Getters and setters omitted for brevity

    public boolean createReservation(User member, Court court, Date resDate, Time startTime, Time endTime) {
        // Implement reservation creation logic
        return true;
    }
    
    public void cancelReservation() {
        // Implement cancellation logic
    }
    
    public boolean modifyReservation(Date newDate, Time newStartTime, Time newEndTime) {
        // Implement modification logic
        return true;
    }
}
