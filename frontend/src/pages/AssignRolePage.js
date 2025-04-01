// src/pages/admin/AssignRolePage.js
import React, { useState } from 'react';
import { assignRole } from '../services/adminService';

const AssignRolePage = () => {
    const [formData, setFormData] = useState({ userId: '', role: '' });
    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await assignRole(formData.userId, formData.role);
            setMessage('Role assigned successfully.');
        } catch (err) {
            setMessage('Role assignment failed.');
        }
    };

    return (
        <div>
            <h2>Assign Role</h2>
            <form onSubmit={handleSubmit}>
                <label>User ID:</label>
                <input type="text" name="userId" onChange={handleChange} />

                <label>Role:</label>
                <select name="role" onChange={handleChange}>
                    <option value="">Select a role</option>
                    <option value="MEMBER">MEMBER</option>
                    <option value="ADMIN">ADMIN</option>
                    <option value="TREASURER">TREASURER</option>
                </select>

                <button type="submit">Assign</button>
            </form>
            <p>{message}</p>
        </div>
    );
};

export default AssignRolePage;
