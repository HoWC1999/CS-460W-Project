import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getMyProfile, updateMyProfile } from '../../services/userService';
import '../../styles/UpdateProfilePage.css';

const UpdateProfilePage = () => {
	const [updateData, setUpdateData] = useState({
		username: '',
		email: '',
		password: '',
		confirmPassword: ''
	});
	const [error, setError] = useState('');
	const navigate = useNavigate();

	// Prefill email & username
	useEffect(() => {
		(async () => {
			try {
				const data = await getMyProfile();
				setUpdateData({
					username: data.username || '',
					email: data.email || '',
					password: '',
					confirmPassword: ''
				});
			} catch (err) {
				setError('Unable to fetch profile.');
			}
		})();
	}, []);

	const handleChange = (e) => {
		const { name, value } = e.target;
		setUpdateData({ ...updateData, [name]: value });
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		if (updateData.password !== updateData.confirmPassword) {
			setError('Passwords do not match.');
			return;
		}
		try {
			await updateMyProfile({
				username: updateData.username,
				email: updateData.email,
				password: updateData.password
			});
			localStorage.removeItem("jwtToken");
			navigate('/login');
		} catch (err) {
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
