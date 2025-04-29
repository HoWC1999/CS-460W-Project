import React, { useState, useEffect } from 'react';
import { getAllUsers, assignRole } from '../services/adminService';
import '../styles/AssignRolePage.css';

const AssignRolePage = () => {
	const [users, setUsers] = useState([]);
	const [selectedUserId, setSelectedUserId] = useState('');
	const [role, setRole] = useState('');
	const [message, setMessage] = useState('');

	useEffect(() => {
		const fetchUsers = async () => {
			try {
				const usersData = await getAllUsers();
				setUsers(usersData);
			} catch (error) {
				setMessage('Unable to fetch users.');
			}
		};
		fetchUsers();
	}, []);

	const handleSubmit = async (e) => {
		e.preventDefault();
		try {
			await assignRole(selectedUserId, role);
			setMessage('Role assigned successfully.');
		} catch (error) {
			setMessage('Role assignment failed: ' + error);
		}
	};

	return (
		<div className="assign-role-page">
			<div className="assign-role-container">
				<h2>Assign Role</h2>
				<form className="assign-role-form" onSubmit={handleSubmit}>
					<label htmlFor="userSelect">Select User:</label>
					<select
						id="userSelect"
						value={selectedUserId}
						onChange={(e) => setSelectedUserId(e.target.value)}
						required
					>
						<option value="">--Select a user--</option>
						{users.map((user) => (
							<option key={user.userId} value={user.userId}>
								{user.username} (ID: {user.userId})
							</option>
						))}
					</select>

					<label htmlFor="roleSelect">Select Role:</label>
					<select
						id="roleSelect"
						value={role}
						onChange={(e) => setRole(e.target.value)}
						required
					>
						<option value="">--Select a role--</option>
						<option value="MEMBER">Member</option>
						<option value="TREASURER">Treasurer</option>
						<option value="ADMIN">Admin</option>
					</select>

					<button type="submit">Assign Role</button>
				</form>

				{message && <p className="message">{message}</p>}
			</div>
		</div>
	);
};

export default AssignRolePage;
