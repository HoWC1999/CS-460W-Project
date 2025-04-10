// src/services/financialService.js
import api from './api';

export const processTransaction = async (transactionData) => {
    // transactionData should include userId, amount, transactionType, etc.
    try {
        const response = await api.post('/financial/transaction', transactionData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Transaction processing failed';
    }
};

export const generateReport = async () => {
    try {
        const response = await api.get('/financial/report');
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Report generation failed';
    }
};

export const reconcileAccounts = async () => {
    try {
        const response = await api.post('/financial/reconcile');
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Reconciliation failed';
    }
};
export const getAllTransactions = async () => {
    const response = await api.get('/financial/transactions');
    return response.data;
};

export const getDisputedTransactions = async () => {
  try {
    const response = await api.get('/disputes/disputed');
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Unable to fetch disputed transactions';
  }
};
export const processRefund = async (transactionId) => {
  try {
    const response = await api.post(`/disputes/refund/${transactionId}`);
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Refund processing failed';
  }
};
