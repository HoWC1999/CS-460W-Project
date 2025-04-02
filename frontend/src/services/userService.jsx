// src/services/userService.js
import api from './api';

export const register = async (userData) => {
    // userData should include: username, password, email, etc.
    try {
        const response = await api.post('/users/register', userData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'Registration failed';
    }
};

export const updateUser = async (userId, updateData) => {
    try {
        const response = await api.put(`/users/update/${userId}`, updateData);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'User update failed';
    }
};

export const getUser = async (userId) => {
    try {
        const response = await api.get(`/users/${userId}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || 'User retrieval failed';
    }
};
export const getMyProfile = async () => {
    const response = await api.get('/users/me');
    return response.data;
};

export const updateMyProfile = async (profileData) => {
    console.log("updateMyProfile - sending payload:", profileData);
    const response = await api.put('/users/me', profileData);
    console.log("updateMyProfile - received response:", response.data);
    return response.data;
};
