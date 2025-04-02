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

export const processRefund = async (refundData) => {
    // refundData should include necessary fields similar to a transaction
    try {
        const response = await api.post('/financial/refund', refundData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Refund processing failed';
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
