package com.tennisclub.controller;

import com.tennisclub.model.CourtReservation;
import com.tennisclub.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    
    @PostMapping("/create")
    public CourtReservation createReservation(@RequestBody CourtReservation reservation) {
        return reservationService.createReservation(reservation);
    }
    
    @PostMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable int id) {
        return reservationService.cancelReservation(id) ? "Cancellation successful" : "Cancellation failed";
    }
    
    @PutMapping("/modify/{id}")
    public CourtReservation modifyReservation(@PathVariable int id, @RequestBody CourtReservation reservation) {
        return reservationService.modifyReservation(id, reservation);
    }
}
