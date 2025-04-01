// src/services/paymentDisputeService.js
import api from './api';

export const resolveDispute = async (disputeId, resolutionData) => {
    // resolutionData might include a resolution note or updated status.
    try {
        const response = await api.post(`/disputes/resolve/${disputeId}`, resolutionData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Dispute resolution failed';
    }
};

export const getDispute = async (disputeId) => {
    try {
        const response = await api.get(`/disputes/${disputeId}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Dispute retrieval failed';
    }
};
