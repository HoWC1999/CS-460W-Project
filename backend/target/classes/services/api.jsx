// src/services/api.js
import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8888/api",
    headers: {
        "Content-Type": "application/json",
    },
});

// Request interceptor to attach the token if available
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("jwtToken");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

// Interceptor to handle 401/403 errors and force logout
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && (error.response.status === 401 || error.response.status === 403)) {
            console.warn("Received 401/403 error. Token might be invalid. Redirecting to home.");
            // Clear token and force reload / redirect via window.location
            localStorage.removeItem("jwtToken");
            window.location.replace("/");
        }
        return Promise.reject(error);
    }
);


export default api;
