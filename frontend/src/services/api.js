// src/services/api.js
import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8888/api",
    headers: {
        "Content-Type": "application/json"
    }
});

// Add a request interceptor to attach the token if available
api.interceptors.request.use(config => {
    const token = localStorage.getItem("jwtToken");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;
