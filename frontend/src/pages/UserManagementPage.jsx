// src/pages/admin/UserManagementPage.jsx
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
      <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '1em' }}>
        <thead>
        <tr>
          <th style={{ border: '1px solid #ddd', padding: '8px' }}>Username</th>
          <th style={{ border: '1px solid #ddd', padding: '8px' }}>Role</th>
          <th style={{ border: '1px solid #ddd', padding: '8px' }}>Email</th>
          <th style={{ border: '1px solid #ddd', padding: '8px' }}>Name</th>
          <th style={{ border: '1px solid #ddd', padding: '8px' }}>Address</th>
          <th style={{ border: '1px solid #ddd', padding: '8px' }}>Phone Number</th>
          <th style={{ border: '1px solid #ddd', padding: '8px' }}>Actions</th>
        </tr>
        </thead>
        <tbody>
        {users.map(user => (
          <tr key={user.userId}>
            <td style={{ border: '1px solid #ddd', padding: '8px' }}>{user.username}</td>
            <td style={{ border: '1px solid #ddd', padding: '8px' }}>{user.role}</td>
            <td style={{ border: '1px solid #ddd', padding: '8px' }}>{user.email}</td>
            <td style={{ border: '1px solid #ddd', padding: '8px' }}>{user.fullName}</td>
            <td style={{ border: '1px solid #ddd', padding: '8px' }}>{user.address}</td>
            <td style={{ border: '1px solid #ddd', padding: '8px' }}>{user.phoneNumber}</td>
            <td style={{ border: '1px solid #ddd', padding: '8px', textAlign: 'center' }}>
              <button onClick={() => handleDelete(user.userId)}>
                Delete
              </button>
            </td>
          </tr>
        ))}
        {users.length === 0 && (
          <tr>
            <td colSpan="7" style={{ textAlign: 'center', padding: '12px' }}>
              No users found.
            </td>
          </tr>
        )}
        </tbody>
      </table>
    </div>
  );
};

export default UserManagementPage;
