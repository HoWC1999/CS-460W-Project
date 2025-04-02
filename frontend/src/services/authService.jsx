// src/services/authService.js
import api from "./api";

export const login = async (username, password) => {
    try {
        const response = await api.post("/auth/login", { username, password });
        // Log response for debugging:
        console.log("Login response:", response.data);
        // Store token in localStorage
        localStorage.setItem("jwtToken", response.data.token);
        return response.data;
    } catch (error) {
        console.error("Login error:", error);
        throw error.response?.data || "Login failed";
    }
};

export const logout = async () => {
    try {
        await api.post("/auth/logout", {});
        localStorage.removeItem("jwtToken");
    } catch (error) {
        throw error.response?.data || "Logout failed";
    }
};
