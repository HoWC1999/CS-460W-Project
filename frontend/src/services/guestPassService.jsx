// src/services/guestPassService.js
import api from './api';

export const purchaseGuestPass = async (userId) => {
  try {
    // Assume that your backend has an endpoint to purchase a guest pass
    const response = await api.post('/guestpasses/purchase/', null, { params: { userId, price: 5 } });
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Guest pass purchase failed';
  }
};


