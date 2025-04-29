import React, { useEffect, useState } from 'react';
import { getAllUsers, deleteUser } from '../services/adminService';
import '../styles/UserManagementPage.css';

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
		return (
			<div className="user-management-page">
				<div className="user-management-container">
					<p className="error">{error}</p>
				</div>
			</div>
		);
	}

	return (
		<div className="user-management-page">
			<div className="user-management-container">
				<h2>User Management</h2>
				<ul>
					{users.map(user => (
						<li key={user.userId}>
							<span>
								{user.username} | ({user.role}) | ({user.email})
							</span>
							<button onClick={() => handleDelete(user.userId)}>
								Delete
							</button>
						</li>
					))}
				</ul>
			</div>
		</div>
	);
};

export default UserManagementPage;
