// src/services/eventService.js
import api from './api';

export const createEvent = async (eventData) => {
    // eventData should include title, description, eventDate, eventTime, location, etc.
    try {
        const response = await api.post('/events/create', eventData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Event creation failed';
    }
};

export const updateEvent = async (eventId, eventData) => {
    try {
        const response = await api.put(`/events/update/${eventId}`, eventData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Event update failed';
    }
};

export const cancelEvent = async (eventId) => {
    try {
        const response = await api.delete(`/events/cancel/${eventId}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Event cancellation failed';
    }
};

export const getEvent = async (eventId) => {
    try {
        const response = await api.get(`/events/${eventId}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Event retrieval failed';
    }
};
