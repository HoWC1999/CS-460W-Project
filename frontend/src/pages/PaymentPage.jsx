// src/pages/PaymentPage.jsx
import React, { useContext } from 'react';
import { loadStripe } from '@stripe/stripe-js';
import { Elements } from '@stripe/react-stripe-js';
import PaymentForm from '../components/PaymentForm';
import { processCardPayment } from '../services/billingService';
import { AuthContext } from '../context/AuthContext';

// Load your Stripe publishable key (test mode)
const stripePromise = loadStripe("pk_test_yourPublishableKey");

const PaymentPage = ({ billingId }) => {
  const { user } = useContext(AuthContext);

  const handlePaymentSuccess = async (paymentMethodId) => {
    try {
      // Call backend to process the card payment using the paymentMethodId.
      const response = await processCardPayment(billingId, paymentMethodId);
      console.log("Payment processed successfully:", response);
      // Optionally, refresh billing history or show a success message.
    } catch (error) {
      console.error("Payment processing failed:", error);
    }
  };

  return (
    <div className="payment-page-container">
      <h2>Pay Your Bill</h2>
      <Elements stripe={stripePromise}>
        <PaymentForm onPaymentSuccess={handlePaymentSuccess} />
      </Elements>
    </div>
  );
};

export default PaymentPage;
