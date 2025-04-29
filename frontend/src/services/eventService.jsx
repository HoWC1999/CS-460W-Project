// src/services/eventService.jsx
import api from './api';

/** Create a new event */
export const createEvent = async (eventData) => {
  try {
    const response = await api.post('/events/create', eventData);
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Event creation failed';
  }
};

/** Update an existing event */
export const updateEvent = async (eventId, eventData) => {
  try {
    const response = await api.put(`/events/update/${eventId}`, eventData);
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Event update failed';
  }
};

/** Cancel (delete) an event */
export const cancelEvent = async (eventId) => {
  try {
    const response = await api.delete(`/events/cancel/${eventId}`);
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Event cancellation failed';
  }
};

/** Fetch a single event by ID */
export const getEvent = async (eventId) => {
  try {
    const response = await api.get(`/events/${eventId}`);
    return response.data;
  } catch (error) {
    throw error.response?.data || 'Event retrieval failed';
  }
};

/** Fetch all events */
export const getAllEvents = async () => {
  const response = await api.get('/events/all');
  return response.data;
};

/** Sign the current user up for an event */
export const signupForEvent = async (eventId, payload) => {
  try {
    const res = await api.post(`/events/${eventId}/signup`, payload);
    return res.data;
  } catch (err) {
    throw err.response?.data || 'Signup failed';
  }
};

/** Fetch all registrations for a given event */
export const getEventRegistrations = async (eventId) => {
  try {
    const res = await api.get(`/events/${eventId}/registrations`);
    return res.data;
  } catch (err) {
    throw err.response?.data || 'Could not fetch registrations';
  }
};
