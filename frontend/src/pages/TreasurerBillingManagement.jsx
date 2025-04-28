// src/pages/TreasurerBillingManagementPage.jsx
import React, { useEffect, useState } from 'react';
import {
  getAllBilling,
  applyLateFee,
  chargeAnnualMembershipFee
} from '../services/billingService';
import { getAllUsers } from '../services/adminService';
import '../styles/TreasurerBillingManagementPage.css';

const TreasurerBillingManagementPage = () => {
  const [billingRecords, setBillingRecords] = useState([]);
  const [users, setUsers]               = useState([]);
  const [error, setError]               = useState('');
  const [selectedUserId, setSelectedUserId] = useState('');
  const [amount, setAmount]             = useState('');
  const [baseAmount, setBaseAmount]     = useState('');

  // Fetch billing records once
  useEffect(() => {
    async function loadBilling() {
      try {
        const data = await getAllBilling();
        setBillingRecords(data);
      } catch {
        setError('Unable to fetch billing records.');
      }
    }
    loadBilling();
  }, []);  // no return → no accidental Promise returned

  // Fetch user list once
  useEffect(() => {
    async function loadUsers() {
      try {
        const u = await getAllUsers();
        setUsers(u);
      } catch {
        setError('Unable to fetch users.');
      }
    }
    loadUsers();
  }, []);

  const handleChargeMembership = async (e) => {
    e.preventDefault();
    try {
      await chargeAnnualMembershipFee(
        Number(selectedUserId),
        Number(amount)
      );
      // reload billing after charge
      const data = await getAllBilling();
      setBillingRecords(data);
    } catch {
      setError('Annual membership fee processing failed.');
    }
  };

  const handleApplyLateFee = async (e) => {
    e.preventDefault();
    try {
      await applyLateFee(
        Number(selectedUserId),
        Number(baseAmount)
      );
      const data = await getAllBilling();
      setBillingRecords(data);
    } catch {
      setError('Late fee application failed.');
    }
  };

  return (
    <div className="treasurer-billing-container">
      <h2>Billing Management</h2>
      {error && <p className="error">{error}</p>}

      <section className="billing-actions">
        <form onSubmit={handleChargeMembership}>
          <h3>Charge Annual Membership Fee</h3>
          <select
            value={selectedUserId}
            onChange={(e) => setSelectedUserId(e.target.value)}
            required
          >
            <option value="">Select user…</option>
            {users.map((u) => (
              <option key={u.userId} value={u.userId}>
                {u.username} (ID: {u.userId})
              </option>
            ))}
          </select>
          <input
            type="number"
            placeholder="Amount"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            required
          />
          <button type="submit">Charge Membership</button>
        </form>

        <form onSubmit={handleApplyLateFee}>
          <h3>Apply Late Fee</h3>
          <select
            value={selectedUserId}
            onChange={(e) => setSelectedUserId(e.target.value)}
            required
          >
            <option value="">Select user…</option>
            {users.map((u) => (
              <option key={u.userId} value={u.userId}>
                {u.username} (ID: {u.userId})
              </option>
            ))}
          </select>
          <input
            type="number"
            placeholder="Base Amount"
            value={baseAmount}
            onChange={(e) => setBaseAmount(e.target.value)}
            required
          />
          <button type="submit">Apply Late Fee</button>
        </form>
      </section>

      <h3>All Billing Records</h3>
      <table className="billing-table">
        <thead>
        <tr>
          <th>ID</th><th>User</th><th>Amount</th>
          <th>Type</th><th>Date</th><th>Status</th>
        </tr>
        </thead>
        <tbody>
        {billingRecords.map((r) => (
          <tr key={r.transactionId}>
            <td>{r.transactionId}</td>
            <td>{r.user?.userId ?? '—'}</td>
            <td>{r.amount}</td>
            <td>{r.transactionType}</td>
            <td>{new Date(r.transactionDate).toLocaleString()}</td>
            <td>{r.status}</td>
          </tr>
        ))}
        </tbody>
      </table>
    </div>
  );
};

export default TreasurerBillingManagementPage;
