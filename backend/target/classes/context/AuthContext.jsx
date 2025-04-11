import React, { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { getMyProfile } from "../services/userService";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(null);
  const [user, setUser] = useState(null); // Full user object including userId, username, role, etc.
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("jwtToken");
    setToken(null);
    setUser(null);
    navigate("/", { replace: true });
  };

  // Function to perform login. It stores the token and then fetches the full user profile.
  const login = async (newToken) => {
    try {
      localStorage.setItem("jwtToken", newToken);
      setToken(newToken);
      // Optionally, decode the token for non-sensitive info like role/username.
      const decoded = jwtDecode(newToken);
      console.log("Decoded token:", decoded);
      // Fetch the full user profile from the backend.
      const fullUser = await getMyProfile();
      setUser(fullUser);
    } catch (error) {
      console.error("Error during login:", error);
      logout();
    }
  };

  // On mount, if a token exists in localStorage, fetch the full user profile.
  useEffect(() => {
    const savedToken = localStorage.getItem("jwtToken");
    if (savedToken) {
      setToken(savedToken);
      getMyProfile()
        .then((fullUser) => {
          setUser(fullUser);
        })
        .catch((error) => {
          console.error("Error fetching user profile on mount:", error);
          logout();
        });
    }
  }, []);

  return (
    <AuthContext.Provider value={{ token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
