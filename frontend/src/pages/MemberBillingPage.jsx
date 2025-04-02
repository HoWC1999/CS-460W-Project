// src/pages/MemberBillingPage.js
import React, { useEffect, useState, useContext } from 'react';
import { getBillingHistory, chargeAnnualMembershipFee } from '../services/billingService';
import { AuthContext } from '../context/AuthContext';


const MemberBillingPage = () => {
  const [billingHistory, setBillingHistory] = useState([]);
  const [error, setError] = useState('');
  const { user } = useContext(AuthContext);

  useEffect(() => {
    if (user && user.userId) {
      const fetchBilling = async () => {
        try {
          const data = await getBillingHistory(user.userId);
          setBillingHistory(data);
        } catch (err) {
          setError('Unable to fetch billing history.');
        }
      };
      fetchBilling();
    }
  }, [user]);

  const handleChargeMembership = async () => {
    try {
      // Charge $400 as an example annual membership fee.
      await chargeAnnualMembershipFee(user.userId, 400.00);
      const data = await getBillingHistory(user.userId);
      setBillingHistory(data);
    } catch (err) {
      setError('Annual membership fee processing failed.');
    }
  };

  return (
    <div className="member-billing-container">
      <h2>My Billing</h2>
      {error && <p className="error">{error}</p>}
      <button onClick={handleChargeMembership}>Pay Annual Membership Fee ($400)</button>
      {billingHistory.length === 0 ? (
        <p>No billing records found.</p>
      ) : (
        <ul className="billing-list">
          {billingHistory.map(bill => (
            <li key={bill.transactionId}>
              <div>
                <strong>Amount:</strong> {bill.amount} |
                <strong> Type:</strong> {bill.transactionType} |
                <strong> Date:</strong> {new Date(bill.transactionDate).toLocaleString()} |
                <strong> Description:</strong> {bill.description} |
                <strong> Status:</strong> {bill.status}
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default MemberBillingPage;
