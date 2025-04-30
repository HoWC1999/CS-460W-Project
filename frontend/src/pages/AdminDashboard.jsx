// src/pages/admin/AdminDashboard.jsx
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getAllPasses } from '../services/adminService';
import '../styles/AdminDashboard.css';

const AdminDashboard = () => {
	const [passes, setPasses] = useState([]);
	const [error, setError] = useState(null);

	useEffect(() => {
		const fetchAllPasses = async () => {
			try {
				const data = await getAllPasses();
				setPasses(data);
			} catch (err) {
				setError('Unable to fetch passes.');
			}
		};
		fetchAllPasses();
	}, []);

	return (
		<div className="admin-dashboard-page">
			<div className="admin-dashboard-container">
				<h2>Admin Dashboard</h2>
				<ul>
					<li><Link to="/admin/users">Manage Users</Link></li>
					<li><Link to="/admin/assign-role">Assign Roles</Link></li>
					<li><Link to="/admin/events">Manage Events</Link></li>
				</ul>
				{error && <p className="error">{error}</p>}
				<h3>Guest Passes</h3>
				<ul>
					{passes.map(pass => (
						<li key={pass.guestPassId}>
							<strong>Pass ID:</strong> {pass.guestPassId} |
							<strong> User ID:</strong> {pass.userId} |
							<strong> User Name:</strong> {pass.user && pass.user.username ? pass.user.username : "N/A"} |
							<strong> Purchase Date:</strong> {new Date(pass.purchaseDate).toLocaleString()} |
							<strong> Expiration Date:</strong> {new Date(pass.expirationDate).toLocaleString()}
						</li>
					))}
				</ul>
			</div>
		</div>
	);
};

export default AdminDashboard;
