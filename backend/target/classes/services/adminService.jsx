// src/services/adminService.js
import api from './api';

// Retrieve all users (assuming your backend endpoint returns a list of users)
export const getAllUsers = async () => {
    try {
        const response = await api.get('/admin/users');
        return response.data;
    } catch (error) {
        console.error("Error fetching users:", error);
        throw error.response?.data || "Error fetching users";
    }
};

// Delete a user by ID (assuming DELETE method is supported)
export const deleteUser = async (userId) => {
    try {
        const response = await api.delete(`/admin/delete/${userId}`);
        return response.data;
    } catch (error) {
        console.error("Error deleting user:", error);
        throw error.response?.data || "Error deleting user";
    }
};

// Assign a new role to a user (assuming POST method is used and the backend accepts a JSON body)
export const assignRole = async (userId, role) => {
    try {
        // You can send the role as a query parameter or in the request body.
        // This example sends it in the request body.
        const response = await api.post(`/admin/assignRole/${userId}`, { role });
        return response.data;
    } catch (error) {
        console.error("Error assigning role:", error);
        throw error.response?.data || "Error assigning role";
    }
};

export const getAllPasses = async () => {
  try {
    const response = await api.get('/admin/getAll');
    return response.data;
  } catch (error) {
    console.error("Error fetching users:", error);
    throw error.response?.data || "Error fetching users";
  }
};
