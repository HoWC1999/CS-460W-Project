// src/pages/CourtReservation.jsx
import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/CourtReservation.css';
import { createReservation } from '../services/reservationService';
import { AuthContext } from '../context/AuthContext';

const CourtReservation = () => {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    court: '',
    date: '',
    time: ''
  });
  const [message, setMessage] = useState('');

  // if not logged in, push to login
  if (!user) {
    navigate('/login', { replace: true });
    return null;
  }

  const handleChange = (e) => {
    setFormData(f => ({ ...f, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');

    try {
      const payload = {
        userId: user.userId,
        court: Number(formData.court),
        date: formData.date,
        time: formData.time
      };
      const resp = await createReservation(payload);
      setMessage(
        `Reserved court ${resp.courtNumber} on ${resp.reservationDate} at ${resp.startTime}.`
      );
    } catch (err) {
      setMessage(`Reservation failed: ${err}`);
    }
  };

  return (
    <div className="reservation-page">
      <div className="reservation-form-container">
        <h2>Reserve a Tennis Court</h2>
        {message && <p className="reservation-message">{message}</p>}
        <form className="reservation-form" onSubmit={handleSubmit}>
          <label>
            Court Number (1–12)
            <input
              type="number"
              name="court"
              min="1"
              max="12"
              value={formData.court}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Date
            <input
              type="date"
              name="date"
              value={formData.date}
              onChange={handleChange}
              required
            />
          </label>
          <label>
            Time
            <input
              type="time"
              name="time"
              value={formData.time}
              onChange={handleChange}
              required
            />
          </label>
          <button type="submit">Reserve</button>
        </form>
      </div>
    </div>
  );
};

export default CourtReservation;
