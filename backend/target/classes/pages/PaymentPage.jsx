// src/pages/PaymentPage.jsx
import React, { useContext } from "react";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";
import PaymentForm from "../components/PaymentForm";
import { processCardPayment } from "../services/billingService";
import { AuthContext } from "../context/AuthContext";
import { useParams } from "react-router-dom";

const stripePromise = loadStripe("pk_test_yourPublishableKey");

const PaymentPage = () => {
  // Assuming the billingId is passed as a URL parameter
  const { billingId } = useParams();
  const { user } = useContext(AuthContext);

  const handlePaymentSuccess = async (stripePaymentMethodId) => {
    try {
      // Call the dedicated endpoint with the billingId and the Stripe token (paymentMethodId)
      const response = await processCardPayment(billingId, stripePaymentMethodId);
      console.log("Payment processed successfully:", response);
      // Optionally, you can redirect the user or display a success message here.
    } catch (error) {
      console.error("Card payment failed:", error);
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
