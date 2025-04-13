// src/pages/PurchaseGuestPassPage.jsx
import React, { useState, useContext } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { purchaseGuestPass } from '../../services/guestPassService';

const PurchaseGuestPassPage = () => {
  const [message, setMessage] = useState('');
  const { user } = useContext(AuthContext);

  const handlePurchase = async () => {
    if (!user || !user.userId) {
      setMessage('User not authenticated. Please log in.');
      return;
    }

    try {
      const response = await purchaseGuestPass(user.userId);
      // Log success with a production logger if available
      console.info(`Guest pass purchased for user ${user.userId}`, response);
      setMessage(`Guest pass purchased successfully.`);
    } catch (err) {
      // Log the error with proper error handling/logging mechanism in production
      console.error("Error purchasing guest pass:", err);
      const errMsg = err && err.message ? err.message : "An unknown error occurred.";
      setMessage(`Guest pass purchase failed: ${errMsg}`);
    }
  };

  return (
    <div>
      <h2>Purchase a Single–Use Guest Pass</h2>
      <p>This guest pass grants one guest access for one day and costs $5.</p>
      <button onClick={handlePurchase}>Purchase Guest Pass</button>
      {message && <p>{message}</p>}
    </div>
  );
};

export default PurchaseGuestPassPage;
