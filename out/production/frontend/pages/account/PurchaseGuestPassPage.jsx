// src/pages/account/PurchaseGuestPassPage.jsx
import React, { useState, useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { purchaseGuestPass } from '../../services/guestPassService';
import '../../styles/PurchaseGuestPassPage.css';

const PurchaseGuestPassPage = () => {
	const [message, setMessage] = useState('');
	const { user } = useContext(AuthContext);

	const handlePurchase = async () => {
		if (!user || !user.userId) {
			setMessage('User not authenticated. Please log in.');
			return;
		}
		try {
			await purchaseGuestPass(user.userId);
			setMessage('Guest pass purchased successfully.');
		} catch (err) {
			console.error("Error purchasing guest pass:", err);
			setMessage(`Guest pass purchase failed: ${err?.message || 'Unknown error.'}`);
		}
	};

	return (
		<div className="purchase-guest-pass-page">
			<div className="purchase-guest-pass-container">
				<h2>Purchase a Single-Use Guest Pass</h2>
				<p>This guest pass grants one guest access for one day and costs $5.</p>
				<button onClick={handlePurchase}>Purchase Guest Pass</button>
				{message && <p className="message">{message}</p>}
			</div>
		</div>
	);
};

export default PurchaseGuestPassPage;
