// src/pages/treasurer/RefundPage.jsx
import React, { useState, useEffect } from 'react';
import {getDisputedTransactions, issueRefund, processRefund} from '../services/financialService';

const RefundPage = () => {
  const [disputedTransactions, setDisputedTransactions] = useState([]);
  const [message, setMessage] = useState('');

  // Fetch disputed transactions on mount
  useEffect(() => {
    const fetchDisputedTransactions = async () => {
      try {
        const data = await getDisputedTransactions();
        setDisputedTransactions(data);
      } catch (err) {
        console.error("Error fetching disputed transactions:", err);
        setMessage('Unable to fetch disputed transactions.');
      }
    };
    fetchDisputedTransactions();
  }, []);

  const handleRefund = async (transactionId) => {
    try {
      const response = await processRefund(transactionId);
      setMessage(`Refund processed for transaction ${transactionId}.`);
      // Refresh the disputed transactions list
      const data = await getDisputedTransactions();
      setDisputedTransactions(data);
    } catch (err) {
      console.error(`Error issuing refund for transaction ${transactionId}:`, err);
      setMessage(`Refund failed for transaction ${transactionId}: ${err}`);
    }
  };

  return (
    <div>
      <h2>Disputed Transactions (Refunds)</h2>
      {message && <p>{message}</p>}
      {disputedTransactions.length === 0 ? (
        <p>No disputed transactions found.</p>
      ) : (
        <ul>
          {disputedTransactions.map((tx) => (
            <li key={tx.transactionId}>
              <div>
                <strong>ID:</strong> {tx.transactionId} |
                <strong> Amount:</strong> ${tx.amount} |
                <strong> Date:</strong> {new Date(tx.transactionDate).toLocaleString()} |
                <strong> Description:</strong> {tx.description}
              </div>
              <button onClick={() => handleRefund(tx.transactionId)}>Issue Refund</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default RefundPage;

