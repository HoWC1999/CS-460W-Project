import React, { useState } from 'react';
import '../styles/CourtReservation.css';
import { createReservation } from '../services/reservationService';

const CourtReservation = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        court: '',
        date: '',
        time: ''
    });

    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Prepare the payload; adjust field names as expected by your backend API.
            const reservationData = {
                name: formData.name,
                email: formData.email,
                court: parseInt(formData.court, 10),
                date: formData.date,
                time: formData.time
            };

            // Call the reservation service to create a reservation
            const response = await createReservation(reservationData);
            setMessage(`Reservation successful! Court ${response.court} reserved on ${response.reservationDate} at ${response.startTime}.`);
        } catch (error) {
            setMessage(`Reservation failed: ${error}`);
            console.error("Reservation error:", error);
        }
    };

    return (
        <div className="reservation-form-container">
            <h2>Reserve a Tennis Court</h2>
            <form className="reservation-form" onSubmit={handleSubmit}>
                <input type="text" name="name" placeholder="Your Name" onChange={handleChange} required />
                <input type="email" name="email" placeholder="Email Address" onChange={handleChange} required />
                <input type="number" name="court" placeholder="Court Number (1-12)" min="1" max="12" onChange={handleChange} required />
                <input type="date" name="date" onChange={handleChange} required />
                <input type="time" name="time" onChange={handleChange} required />
                <button type="submit">Reserve</button>
            </form>
        </div>
    );
};

export default CourtReservation;
