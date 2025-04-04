// src/pages/admin/AdminDashboard.jsx
import React from 'react';
import { Link } from 'react-router-dom';

const AdminDashboard = () => {
    return (
        <div>
            <h2>Admin Dashboard</h2>
            <ul>
                <li><Link to="/admin/users">Manage Users</Link></li>
                <li><Link to="/admin/assign-role">Assign Roles</Link></li>
                <li><Link to={"/admin/events"}>Manage Events</Link></li>
            </ul>
        </div>
    );
};

export default AdminDashboard;
