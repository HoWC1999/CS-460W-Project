// src/services/billingService.js
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

export const payBill = async (billingId) => {
  try {
    const response = await api.post(`/financial/pay/${billingId}`);
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Bill payment failed';
  }
};

