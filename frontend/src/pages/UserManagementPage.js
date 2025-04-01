// src/pages/admin/UserManagementPage.js
import React, { useEffect, useState } from 'react';
import { getAllUsers, deleteUser } from '../services/adminService';

const UserManagementPage = () => {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const data = await getAllUsers();
                setUsers(data);
            } catch (err) {
                setError('Unable to fetch users.');
            }
        };
        fetchUsers();
    }, []);

    const handleDelete = async (userId) => {
        try {
            await deleteUser(userId);
            setUsers(users.filter(u => u.userId !== userId));
        } catch (err) {
            alert('Delete failed');
        }
    };

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h2>User Management</h2>
            <ul>
                {users.map(user => (
                    <li key={user.userId}>
                        {user.username} ({user.email})
                        <button onClick={() => handleDelete(user.userId)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default UserManagementPage;
