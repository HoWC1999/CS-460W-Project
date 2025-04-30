// src/pages/account/UpdateProfilePage.jsx
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getMyProfile, updateMyProfile } from '../../services/userService';
import '../../styles/UpdateProfilePage.css';

const UpdateProfilePage = () => {
  const [updateData, setUpdateData] = useState({
    email: '',
    username: '',
    address: '',
    phoneNumber: '',
    password: '',
    confirmPassword: ''
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // Prefill all fields from current profile
  useEffect(() => {
    (async () => {
      try {
        const data = await getMyProfile();
        setUpdateData({
          email: data.email || '',
          username: data.username || '',
          address: data.address || '',
          phoneNumber: data.phoneNumber || '',
          password: '',
          confirmPassword: ''
        });
      } catch {
        setError('Unable to fetch profile.');
      }
    })();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdateData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    if (updateData.password !== updateData.confirmPassword) {
      setError('Passwords do not match.');
      return;
    }
    try {
      await updateMyProfile({
        email: updateData.email,
        username: updateData.username,
        address: updateData.address,
        phoneNumber: updateData.phoneNumber,
        password: updateData.password || undefined // omit if blank
      });
      // Force re-login
      localStorage.removeItem("jwtToken");
      navigate('/login', { replace: true });
    } catch {
      setError('Profile update failed.');
    }
  };

  return (
    <div className="update-profile-page">
      <div className="update-profile-container">
        <h2>Update Profile</h2>
        {error && <p className="error">{error}</p>}
        <form className="update-profile-form" onSubmit={handleSubmit}>
          <label>Email:</label>
          <input
            type="email"
            name="email"
            value={updateData.email}
            onChange={handleChange}
            required
          />

          <label>Username:</label>
          <input
            type="text"
            name="username"
            value={updateData.username}
            onChange={handleChange}
            required
          />

          <label>Address:</label>
          <input
            type="text"
            name="address"
            value={updateData.address}
            onChange={handleChange}
          />

          <label>Phone Number:</label>
          <input
            type="tel"
            name="phoneNumber"
            value={updateData.phoneNumber}
            onChange={handleChange}
          />

          <label>Password (leave blank to keep current):</label>
          <input
            type="password"
            name="password"
            value={updateData.password}
            onChange={handleChange}
          />

          <label>Confirm Password:</label>
          <input
            type="password"
            name="confirmPassword"
            value={updateData.confirmPassword}
            onChange={handleChange}
          />

          <button type="submit">Update</button>
        </form>
      </div>
    </div>
  );
};

export default UpdateProfilePage;
