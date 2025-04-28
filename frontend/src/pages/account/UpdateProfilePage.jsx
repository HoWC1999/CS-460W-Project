// src/pages/account/UpdateProfilePage.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getMyProfile, updateMyProfile } from '../../services/userService';


const UpdateProfilePage = () => {
    const [updateData, setUpdateData] = useState({
        username: '',
        email: '',
        password: '',
        address: ''
    });
    const [error, setError] = useState('');
    const navigate = useNavigate();

    // Fetch profile on mount and prefill updateData with username and email
    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const data = await getMyProfile();
                // Pre-populate updateData with current username and email;
                setUpdateData({
                    username: data.username || '',
                    email: data.email || '',
                    password: '',
                    address:data.address || ''
                });
            } catch (err) {
                console.error("Fetch profile error:", err);
                setError('Unable to fetch profile.');
            }
        };
        fetchProfile();
    }, []);

    const handleChange = (e) => {
        setUpdateData({ ...updateData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateMyProfile(updateData);
            // Clear stored token and force login
            localStorage.removeItem("jwtToken");
            navigate('/login');
        } catch (err) {
            console.error("Update profile error:", err);
            setError('Profile update failed.');
        }
    };

    return (
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
              <p>
                <label>Username:</label>
                <input
                    type="text"
                    name="username"
                    value={updateData.username}
                    onChange={handleChange}
                    required
                />
              </p>
                <label>Password (leave blank to keep current):</label>
                <input
                    type="password"
                    name="password"
                    value={updateData.password}
                    onChange={handleChange}
                />
              <label>Confirm Password</label>
                <input
                  type="password"
                  name="confirmPassword"
                  />
              <label> Address</label>
                <input
                  type="text"
                  name="address"
                />
              <p>
                <button type="submit">Update</button>
              </p>
            </form>
        </div>
    );
};

export default UpdateProfilePage;

