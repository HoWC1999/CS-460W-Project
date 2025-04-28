// src/pages/RegisterPage.jsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/RegisterPage.css";
import { register } from "../services/userService";

function RegisterPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [fullName, setFullName] = useState("");
  const [address, setAddress] = useState("");
  const [billingPlan, setBillingPlan] = useState("MONTHLY");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleRegistration = async (e) => {
    e.preventDefault();
    setError("");

    if (password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }
    if (!fullName.trim() || !address.trim()) {
      setError("Name and address are required.");
      return;
    }

    const userData = {
      username,
      password,
      email,
      phoneNumber,
      fullName,
      address,
      billingPlan,  // backend will charge according to this plan
    };

    try {
      const registeredUser = await register(userData);
      console.log("User registered successfully:", registeredUser);
      navigate("/login");
    } catch (err) {
      console.error("Registration failed:", err);
      setError("Registration failed: " + (err.message || err));
    }
  };

  return (
    <div className="register-container">
      <h2>Register</h2>
      {error && <p className="error">{error}</p>}
      <form className="register-form" onSubmit={handleRegistration}>
        <label htmlFor="name">Full Name</label>
        <input
          id="name"
          type="text"
          value={fullName}
          onChange={e => setFullName(e.target.value)}
          required
        />

        <label htmlFor="address">Address</label>
        <input
          id="address"
          type="text"
          value={address}
          onChange={e => setAddress(e.target.value)}
          required
        />

        <label htmlFor="username">Username</label>
        <input
          id="username"
          type="text"
          value={username}
          onChange={e => setUsername(e.target.value)}
          required
        />

        <label htmlFor="password">Password</label>
        <input
          id="password"
          type="password"
          value={password}
          onChange={e => setPassword(e.target.value)}
          required
        />

        <label htmlFor="confirmPassword">Confirm Password</label>
        <input
          id="confirmPassword"
          type="password"
          value={confirmPassword}
          onChange={e => setConfirmPassword(e.target.value)}
          required
        />

        <label htmlFor="email">Email</label>
        <input
          id="email"
          type="email"
          value={email}
          onChange={e => setEmail(e.target.value)}
          required
        />

        <label htmlFor="phoneNumber">Telephone</label>
        <input
          id="phoneNumber"
          type="tel"
          value={phoneNumber}
          onChange={e => setPhoneNumber(e.target.value)}
          required
        />

        <label htmlFor="billingPlan">Billing Plan</label>
        <select
          id="billingPlan"
          value={billingPlan}
          onChange={e => setBillingPlan(e.target.value)}
          required
        >
          <option value="MONTHLY">Monthly</option>
          <option value="ANNUAL">Annual</option>
          {/* add more plans as needed */}
        </select>

        <button type="submit">
          Complete Registration & Charge Account
        </button>
      </form>
    </div>
  );
}

export default RegisterPage;
