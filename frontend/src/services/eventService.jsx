// src/services/eventService.jsx
import api from './api';

export const createEvent = async (eventData) => {
    // eventData should include title, description, eventDate, eventTime, location, etc.
    try {
        const response = await api.post('/Events/create', eventData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Event creation failed';
    }
};

export const updateEvent = async (eventId, eventData) => {
    try {
        const response = await api.put(`/Events/update/${eventId}`, eventData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Event update failed';
    }
};

export const cancelEvent = async (eventId) => {
    try {
        const response = await api.delete(`/Events/cancel/${eventId}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Event cancellation failed';
    }
};

export const getEvent = async (eventId) => {
    try {
        const response = await api.get(`/Events/${eventId}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Event retrieval failed';
    }
};
export const getAllEvents = async () => {
    const response = await api.get('/Events/all');
    return response.data;
};
