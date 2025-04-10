// src/pages/RegisterPage.jsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/RegisterPage.css";
import { register } from "../services/userService";

function RegisterPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleRegistration = async (e) => {
    e.preventDefault();
    // Map telephone to phoneNumber, matching your database field.
    const userData = {
      username,
      password,
      email,
      phoneNumber,
    };

    try {
      const registeredUser = await register(userData);
      console.log("User registered successfully:", registeredUser);
      // Optionally, you may redirect to a login page or home page upon success.
      navigate("/login");
    } catch (err) {
      console.error("Registration failed:", err);
      setError("Registration failed: " + (err.message || err));
    }
  };

    return (
        <div className="register-container">
            <h2>Register</h2>
            <form className="register-form" onSubmit={handleRegistration}>
                <label htmlFor="username">Username</label>
                <input
                    id="username"
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />

                <label htmlFor="password">Password</label>
                <input
                  id="password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />

                <label htmlFor="email">Email</label>
                <input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <label htmlFor="phoneNumber">Telephone</label>
                <input
                    id="phoneNumber"
                    type="tel"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                    required
                />

                <button type="submit">Click here to complete registration</button>
            </form>
        </div>
    );
}

export default RegisterPage;
