// src/pages/account/UpdateProfilePage.js
import React, { useState } from 'react';
import { updateMyProfile } from '../services/userService';
import { useNavigate } from 'react-router-dom';

const UpdateProfilePage = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({ email: '', phone: '', address: '' });
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateMyProfile(formData);
            navigate('/account');
        } catch (err) {
            setError('Profile update failed.');
        }
    };

    return (
        <div>
            <h2>Update Profile</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <label>Email:</label>
                <input type="email" name="email" value={formData.email} onChange={handleChange} />

                <label>Phone:</label>
                <input type="text" name="phone" value={formData.phone} onChange={handleChange} />

                <label>Address:</label>
                <input type="text" name="address" value={formData.address} onChange={handleChange} />

                <button type="submit">Update</button>
            </form>
        </div>
    );
};

export default UpdateProfilePage;
