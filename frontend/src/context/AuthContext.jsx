
// src/context/AuthContext.js
import React, { createContext, useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode"; // Import the named export

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(null);
    const [user, setUser] = useState(null); // Store decoded user info

    // On mount, read token from localStorage and decode it
    useEffect(() => {
        const savedToken = localStorage.getItem("jwtToken");
        console.log("AuthContext: retrieved token from localStorage:", savedToken);
        if (savedToken) {
            setToken(savedToken);
            try {
                const decoded = jwtDecode(savedToken);
                setUser(decoded); // Assume token payload includes user details, e.g., role, username, etc.
            } catch (error) {
                console.error("Error decoding token:", error);
            }
        }
    }, []);

    const login = (newToken) => {
        console.log("AuthContext login: saving token", newToken);
        localStorage.setItem("jwtToken", newToken);
        setToken(newToken);
        try {
            const decoded = jwtDecode(newToken);
            setUser(decoded);
        } catch (error) {
            console.error("Error decoding token:", error);
        }
    };

    const logout = () => {
        console.log("AuthContext logout: clearing token");
        localStorage.removeItem("jwtToken");
        setToken(null);
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ token, user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};
