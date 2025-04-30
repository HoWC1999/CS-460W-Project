// src/services/guestPassService.js
import api from './api';

export const purchaseGuestPass = async (userId) => {
  try {
    const response = await api.post('/guestpasses/purchase/', null, { params: { userId, price: 5 } });
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Guest pass purchase failed';
  }
};

/**
 * Fetch all guest passes for the given user.
 * @param {number} userId
 * @returns {Promise<Array<GuestPass>>}
 */
export const getMyGuestPasses = async (userId) => {
  try {
    const response = await api.get(`/guestpasses/my?userId=${userId}`);
    return response.data;
  } catch (error) {
    // normalize error
    throw error.response?.data || 'Could not fetch guest passes';
  }
};
