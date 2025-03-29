import React, { useState } from 'react';
import '../styles/CourtReservation.css';

const CourtReservation = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        court: '',
        date: '',
        time: ''
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
    // Placeholder for API call
        alert(`Court ${formData.court} reserved on ${formData.date} at ${formData.time}`);
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
