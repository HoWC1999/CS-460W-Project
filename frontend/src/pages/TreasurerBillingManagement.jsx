// src/pages/TreasurerBillingManagementPage.js
import React, { useEffect, useState } from 'react';
import { getAllBilling, applyLateFee, chargeAnnualMembershipFee } from '../services/billingService';


const TreasurerBillingManagementPage = () => {
  const [billingRecords, setBillingRecords] = useState([]);
  const [error, setError] = useState('');
  const [userId, setUserId] = useState('');
  const [amount, setAmount] = useState('');
  const [baseAmount, setBaseAmount] = useState('');

  const fetchBillingRecords = async () => {
    try {
      const data = await getAllBilling();
      setBillingRecords(data);
    } catch (err) {
      setError('Unable to fetch billing records.');
    }
  };

  useEffect(() => {
    fetchBillingRecords();
  }, []);

  const handleApplyLateFee = async (e) => {
    e.preventDefault();
    try {
      await applyLateFee(Number(userId), Number(baseAmount));
      fetchBillingRecords();
    } catch (err) {
      setError('Late fee application failed.');
    }
  };

  const handleChargeMembership = async (e) => {
    e.preventDefault();
    try {
      await chargeAnnualMembershipFee(Number(userId), Number(amount));
      fetchBillingRecords();
    } catch (err) {
      setError('Annual membership fee processing failed.');
    }
  };

  return (
    <div className="treasurer-billing-container">
      <h2>Billing Management</h2>
      {error && <p className="error">{error}</p>}

      <div className="billing-actions">
        <form onSubmit={handleChargeMembership}>
          <h3>Charge Annual Membership Fee</h3>
          <label>User ID:</label>
          <input type="number" value={userId} onChange={e => setUserId(e.target.value)} required />
          <label>Amount:</label>
          <input type="number" value={amount} onChange={e => setAmount(e.target.value)} required />
          <button type="submit">Charge Membership Fee</button>
        </form>

        <form onSubmit={handleApplyLateFee}>
          <h3>Apply Late Fee</h3>
          <label>User ID:</label>
          <input type="number" value={userId} onChange={e => setUserId(e.target.value)} required />
          <label>Base Amount:</label>
          <input type="number" value={baseAmount} onChange={e => setBaseAmount(e.target.value)} required />
          <button type="submit">Apply Late Fee</button>
        </form>
      </div>

      <h3>All Billing Records</h3>
      {billingRecords.length === 0 ? (
        <p>No billing records found.</p>
      ) : (
        <table className="billing-table">
          <thead>
          <tr>
            <th>Transaction ID</th>
            <th>User ID</th>
            <th>Amount</th>
            <th>Type</th>
            <th>Date</th>
            <th>Description</th>
            <th>Status</th>
          </tr>
          </thead>
          <tbody>
          {billingRecords.map(record => (
            <tr key={record.transactionId}>
              <td>{record.transactionId}</td>
              <td>{record.user ? record.user.userId : 'N/A'}</td>
              <td>{record.amount}</td>
              <td>{record.transactionType}</td>
              <td>{new Date(record.transactionDate).toLocaleString()}</td>
              <td>{record.description}</td>
              <td>{record.status}</td>
            </tr>
          ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default TreasurerBillingManagementPage;
