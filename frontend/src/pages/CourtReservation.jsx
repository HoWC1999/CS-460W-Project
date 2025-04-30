// src/pages/CourtReservation.jsx
import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { Calendar, dateFnsLocalizer } from 'react-big-calendar';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import startOfWeek from 'date-fns/startOfWeek';
import getDay from 'date-fns/getDay';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import '../styles/CourtReservation.css';
import { createReservation, getAllReservations } from '../services/reservationService';
import { AuthContext } from '../context/AuthContext';

// date-fns localizer setup
const locales = { 'en-US': require('date-fns/locale/en-US') };
const localizer = dateFnsLocalizer({ format, parse, startOfWeek, getDay, locales });

const CourtReservation = () => {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({ court: 1, date: '', time: '' });
  const [message, setMessage]   = useState('');
  const [events, setEvents]     = useState([]);

  // redirect if not logged in
  useEffect(() => {
    if (!user) navigate('/login', { replace: true });
  }, [user, navigate]);

  // load calendar events
  useEffect(() => {
    loadReservations();
  }, []);

  const loadReservations = async () => {
    try {
      const all = await getAllReservations();
      const evts = all.map(r => ({
        title: `Court ${r.courtNumber}`,
        start: new Date(`${r.reservationDate}T${r.startTime}`),
        end:   new Date(`${r.reservationDate}T${r.endTime}`)
      }));
      setEvents(evts);
    } catch (err) {
      console.error('Failed to load reservations', err);
    }
  };

  const handleChange = e => {
    const { name, value } = e.target;
    setFormData(fd => ({ ...fd, [name]: value }));
  };

  const handleSubmit = async e => {
    e.preventDefault();
    setMessage('');
    try {
      const { court, date, time } = formData;
      await createReservation({
        userId: user.userId,
        court: Number(court),
        date,
        time
      });
      setMessage(`Reserved court ${court} on ${date} at ${time}.`);
      setFormData(fd => ({ ...fd, time: '' }));
      await loadReservations();
    } catch (err) {
      setMessage('Reservation failed: ' + err);
    }
  };

  // generate 15-minute time slots between 08:00 and 18:00
  const timeOptions = [];
  for (let h = 8; h <= 18; h++) {
    for (let m of [0,15,30,45]) {
      if (h === 18 && m > 0) continue; // only up to 18:00
      const hh = String(h).padStart(2,'0');
      const mm = String(m).padStart(2,'0');
      timeOptions.push(`${hh}:${mm}`);
    }
  }

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
            <select
              name="time"
              value={formData.time}
              onChange={handleChange}
              required
            >
              <option value="">Select a time</option>
              {timeOptions.map(t => (
                <option key={t} value={t}>{t}</option>
              ))}
            </select>
          </label>

          <button type="submit">Reserve</button>
        </form>
      </div>

      <div className="calendar-container">
        <Calendar
          localizer={localizer}
          events={events}
          startAccessor="start"
          endAccessor="end"
          style={{ height: 500, margin: '20px 0' }}
          views={['week','day','month']}
          defaultView="week"
          step={60}
          timeslots={1}
          min={new Date(1970,1,1,8,0,0)}
          max={new Date(1970,1,1,18,0,0)}
        />
      </div>
    </div>
  );
};

export default CourtReservation;
