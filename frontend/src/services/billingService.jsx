// src/services/billingService.jsx
import api from './api';

export const getBillingHistory = async (userId) => {
  try {
    const response = await api.get(`/financial/history?userId=${userId}`);
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Unable to fetch billing history';
  }
};

export const getAllBilling = async () => {
  try {
    const response = await api.get('/financial/all');
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Unable to fetch billing transactions';
  }
};

export const chargeAnnualMembershipFee = async (userId, amount) => {
  try {
    const response = await api.post('/financial/membership', null, { params: { userId, amount } });
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Annual membership fee processing failed';
  }
};

export const applyLateFee = async (userId, baseAmount) => {
  try {
    const response = await api.post('/financial/late', null, { params: { userId, baseAmount } });
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Late fee application failed';
  }
};

// payBill accepts an optional paymentMethod parameter (e.g. "CARD" or "CASH")
export const payBill = async (billingId, paymentMethod = "CASH") => {
  try {
    const response = await api.post(`/financial/pay/${billingId}`, null, { params: { paymentMethod } });
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Bill payment failed';
  }
};

// processCardPayment calls the backend endpoint dedicated to card payments (using Stripe)
export const processCardPayment = async (billingId, paymentMethodId) => {
  try {
    const response = await api.post(`/financial/cardPayment/${billingId}`, null, { params: { paymentMethodId } });
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Card payment failed';
  }
};
