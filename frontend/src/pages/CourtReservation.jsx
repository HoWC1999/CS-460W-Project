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
			const resp = await createReservation({
				name: formData.name,
				email: formData.email,
				court: Number(formData.court),
				date: formData.date,
				time: formData.time
			});
			setMessage(`Reserved court ${resp.court} on ${resp.reservationDate} at ${resp.startTime}.`);
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
					<input type="text"   name="name"  placeholder="Your Name"           onChange={handleChange} required />
					<input type="email"  name="email" placeholder="Email Address"      onChange={handleChange} required />
					<input type="number" name="court" placeholder="Court Number (1–12)" min="1" max="12" onChange={handleChange} required />
					<input type="date"   name="date"  onChange={handleChange} required />
					<input type="time"   name="time"  onChange={handleChange} required />
					<button type="submit">Reserve</button>
				</form>
			</div>
		</div>
	);
};

export default CourtReservation;
