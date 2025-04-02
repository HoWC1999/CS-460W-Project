// src/pages/MemberBillingPage.js
import React, { useEffect, useState, useContext } from 'react';
import { getBillingHistory, payBill } from '../services/billingService';
import { AuthContext } from '../context/AuthContext';
import './MemberBillingPage.css';

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

    const handlePay = async (billingId) => {
        try {
            await payBill(billingId);
            // Refresh billing history after payment
            if (user && user.userId) {
                const data = await getBillingHistory(user.userId);
                setBillingHistory(data);
            }
        } catch (err) {
            setError('Payment failed.');
        }
    };

    return (
        <div className="member-billing-container">
            <h2>My Billing</h2>
            {error && <p className="error">{error}</p>}
            {billingHistory.length === 0 ? (
                <p>No billing records found.</p>
            ) : (
                <ul className="billing-list">
                    {billingHistory.map(bill => (
                        <li key={bill.billingId}>
                            <div>
                                <strong>Amount:</strong> {bill.amount} |
                                <strong> Type:</strong> {bill.feeType} |
                                <strong> Date:</strong> {new Date(bill.billingDate).toLocaleString()} |
                                <strong> Description:</strong> {bill.description} |
                                <strong> Status:</strong> {bill.status}
                            </div>
                            {bill.status !== 'PAID' && (
                                <button onClick={() => handlePay(bill.billingId)}>Pay Bill</button>
                            )}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default MemberBillingPage;
