// src/services/reservationService.js
import api from './api';

export const createReservation = async (reservationData) => {
    try {
        const response = await api.post('/reservations/create', reservationData);
        return response.data;
    } catch (error) {
        console.error("Reservation service error:", error);
        throw error.response?.data || "Reservation creation failed";
    }
};

export const cancelReservation = async (reservationId) => {
    try {
        const response = await api.post(`/reservations/cancel/${reservationId}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Cancellation failed';
    }
};

export const modifyReservation = async (reservationId, reservationData) => {
    try {
        const response = await api.put(`/reservations/modify/${reservationId}`, reservationData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Modification failed';
    }
};
export const getMyReservations = async (userId) => {
    try {
        const response = await api.get(`/reservations/my?userId=${userId}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching reservations:", error);
        throw error.response?.data || "Unable to fetch reservations";
    }
};
