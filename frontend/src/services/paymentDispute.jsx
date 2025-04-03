// src/services/paymentDisputeService.js
import api from './api';

export const createDispute = async (billingId, disputeData) => {
  try {
    const response = await api.post(`/disputes/create/${billingId}`, disputeData);
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Dispute creation failed';
  }
};
