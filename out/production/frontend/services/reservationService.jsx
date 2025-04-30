// src/services/reservationService.js
import api from './api';

/**
 * Create a new reservation.
 * POST /api/reservations/create
 */
export const createReservation = async (reservationData) => {
  try {
    const { data } = await api.post('/reservations/create', reservationData);
    return data;
  } catch (error) {
    console.error("Reservation creation error:", error);
    throw error.response?.data || "Reservation creation failed";
  }
};

/**
 * Fetch this user’s reservations.
 * GET /api/reservations/my?userId={userId}
 */
export const getMyReservations = async (userId) => {
  try {
    const { data } = await api.get(`/reservations/my`, { params: { userId } });
    return data;
  } catch (error) {
    console.error("Error fetching reservations:", error);
    throw error.response?.data || "Unable to fetch reservations";
  }
};

/**
 * Cancel (delete) a reservation.
 * DELETE /api/reservations/cancel/{id}
 */
export const cancelReservation = async (reservationId) => {
  try {
    const { data } = await api.delete(`/reservations/cancel/${reservationId}`);
    return data;
  } catch (error) {
    console.error("Error cancelling reservation:", error);
    throw error.response?.data || "Cancellation failed";
  }
};

/**
 * Modify an existing reservation.
 * (Requires a matching @PutMapping("/modify/{id}") on the backend.)
 */
export const modifyReservation = async (reservationId, reservationData) => {
  try {
    const { data } = await api.put(`/reservations/modify/${reservationId}`, reservationData);
    return data;
  } catch (error) {
    console.error("Error modifying reservation:", error);
    throw error.response?.data || "Modification failed";
  }
};

/**
 * Fetch *all* reservations (for admins, etc.)
 * GET /api/reservations/all
 */
export const getAllReservations = async () => {
  try {
    const { data } = await api.get('/reservations/all');
    return data;
  } catch (error) {
    console.error("Error fetching all reservations:", error);
    throw error.response?.data || "Unable to fetch all reservations";
  }
};


