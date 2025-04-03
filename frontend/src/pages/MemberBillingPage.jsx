// src/pages/MemberBillingPage.jsx
import React, { useEffect, useState, useContext } from 'react';
import { getBillingHistory, chargeAnnualMembershipFee, payBill } from '../services/billingService';
import { createDispute } from '../services/paymentDispute';
import { AuthContext } from '../context/AuthContext';
import { loadStripe } from '@stripe/stripe-js';
import { Elements } from '@stripe/react-stripe-js';
import PaymentForm from '../components/PaymentForm'; // Your PaymentForm component using Stripe Elements


// Load Stripe with your publishable key (test key for now)
const stripePromise = loadStripe("pk_test_51R9ZpcPiSkYNA9tiKVVWY6a4o9BRCIU1WcT7RNtFmwS4GeuJ16h8yet5jFynWQ5odufAz1QnbFTOMhyU64jssKbV00DYIyWGjv");

const MemberBillingPage = () => {
  const [billingHistory, setBillingHistory] = useState([]);
  const [error, setError] = useState('');
  const { user } = useContext(AuthContext);

  // State for dispute handling
  const [disputeReasons, setDisputeReasons] = useState({});
  const [disputeMode, setDisputeMode] = useState(null); // billingId being disputed

  // Payment-related state
  const [selectedBill, setSelectedBill] = useState(null);
  const [paymentMethod, setPaymentMethod] = useState("CARD"); // only option here; cash is handled in person
  const [autoPay, setAutoPay] = useState(false);
  const [showPaymentForm, setShowPaymentForm] = useState(false);

  // Fetch billing history for the logged-in user
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
  // Handler for charging the annual membership fee
  const handleChargeMembership = async () => {
    try {
      // Call the service that charges the annual membership fee.
      // Assuming it takes the user's id as a parameter.
      await chargeAnnualMembershipFee(user.userId);
      // Refresh billing history after charging
      const data = await getBillingHistory(user.userId);
      setBillingHistory(data);
    } catch (err) {
      setError('Error charging membership fee: ' + err.message);
    }
  };

  // Handle selecting a bill from the list (e.g., via a dropdown or button)
  const handleBillSelect = (billingId) => {
    const bill = billingHistory.find(b => b.transactionId.toString() === billingId.toString());
    setSelectedBill(bill);
    // If auto pay is enabled, trigger payment immediately
    if (autoPay && bill) {
      handlePayBill(bill.transactionId);
    } else {
      // Otherwise, show the payment form
      setShowPaymentForm(true);
    }
  };

  // This function is called when the PaymentForm returns a Stripe token (paymentMethodId)
  const handlePaymentSuccess = async (stripePaymentMethodId) => {
    // In this example, we ignore stripePaymentMethodId because our backend expects just a "CARD" payment.
    // In a real integration you might send the token to your backend.
    if (selectedBill) {
      try {
        await payBill(selectedBill.transactionId, paymentMethod); // using "CARD"
        const data = await getBillingHistory(user.userId);
        setBillingHistory(data);
        setSelectedBill(null);
        setShowPaymentForm(false);
      } catch (err) {
        setError('Bill payment failed: ' + err);
      }
    }
  };

  // Handler for manual payment button (bypasses the PaymentForm)
  const handlePayBill = async (billingId) => {
    try {
      // If autoPay is enabled, assume saved card details exist and process payment directly.
      // Otherwise, if a payment form is shown, the onPaymentSuccess callback will handle it.
      if (!showPaymentForm) {
        await payBill(billingId, paymentMethod);
        const data = await getBillingHistory(user.userId);
        setBillingHistory(data);
        setSelectedBill(null);
      }
    } catch (err) {
      setError('Bill payment failed: ' + err);
    }
  };

  const handleDisputeSubmit = async (billingId) => {
    try {
      const reason = disputeReasons[billingId];
      if (!reason || reason.trim() === '') {
        setError('Please provide a dispute reason.');
        return;
      }
      await createDispute(billingId, { reason });
      const data = await getBillingHistory(user.userId);
      setBillingHistory(data);
      setDisputeMode(null);
      setError('');
    } catch (err) {
      setError('Dispute submission failed: ' + err);
    }
  };

  return (
    <div className="member-billing-container">
      <h2>My Billing</h2>
      {error && <p className="error">{error}</p>}

      <div className="billing-actions">
        <button onClick={handleChargeMembership}>Pay Annual Membership Fee ($400)</button>
        <label>
          Auto Pay:
          <input
            type="checkbox"
            checked={autoPay}
            onChange={(e) => setAutoPay(e.target.checked)}
          />
        </label>
      </div>

      <h3>Select a Bill to Pay</h3>
      <select onChange={(e) => handleBillSelect(e.target.value)} value={selectedBill ? selectedBill.transactionId : ''}>
        <option value="">--Select a bill--</option>
        {billingHistory.map(bill => (
          <option key={bill.transactionId} value={bill.transactionId}>
            {bill.transactionId} - ${bill.amount} - {bill.transactionType} - User: {bill.user && bill.user.username}
          </option>
        ))}
      </select>

      {/* If a bill is selected and auto pay is not enabled, show the payment form */}
      {selectedBill && showPaymentForm && (
        <div className="payment-form-container">
          <h3>Enter Payment Information</h3>
          <Elements stripe={stripePromise}>
            <PaymentForm onPaymentSuccess={handlePaymentSuccess} />
          </Elements>
        </div>
      )}

      <h3>Your Billing History</h3>
      {billingHistory.length === 0 ? (
        <p>No billing records found.</p>
      ) : (
        <ul className="billing-list">
          {billingHistory.map(bill => (
            <li key={bill.transactionId}>
              <div>
                <strong>Transaction ID:</strong> {bill.transactionId} |
                <strong> Amount:</strong> ${bill.amount} |
                <strong> Type:</strong> {bill.transactionType} |
                <strong> Date:</strong> {new Date(bill.transactionDate).toLocaleString()} |
                <strong> Description:</strong> {bill.description} |
                <strong> Status:</strong> {bill.status} |
                <strong> User:</strong> {bill.user && bill.user.username}
              </div>
              {bill.status !== 'SUCCESS' && (
                <button onClick={() => handlePayBill(bill.transactionId)}>Pay Now</button>
              )}
              {bill.status === 'SUCCESS' && (!bill.disputed || !bill.description.includes("Disputed")) && (
                <>
                  {disputeMode === bill.transactionId ? (
                    <div>
                      <input
                        type="text"
                        placeholder="Enter dispute reason"
                        value={disputeReasons[bill.transactionId] || ''}
                        onChange={(e) =>
                          setDisputeReasons({
                            ...disputeReasons,
                            [bill.transactionId]: e.target.value,
                          })
                        }
                      />
                      <button onClick={() => handleDisputeSubmit(bill.transactionId)}>Submit Dispute</button>
                      <button onClick={() => setDisputeMode(null)}>Cancel</button>
                    </div>
                  ) : (
                    <button onClick={() => setDisputeMode(bill.transactionId)}>Dispute Payment</button>
                  )}
                </>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default MemberBillingPage;
