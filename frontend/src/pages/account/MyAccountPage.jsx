import React, { useEffect, useState, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getMyProfile } from '../../services/userService';
import { getMyReservations } from '../../services/reservationService';
import { AuthContext } from '../../context/AuthContext';
import '../../styles/MyAccountPage.css';

const MyAccountPage = () => {
	const [profile, setProfile] = useState(null);
	const [reservations, setReservations] = useState([]);
	const [error, setError] = useState(null);
	const [resError, setResError] = useState(null);
	const { logout } = useContext(AuthContext);
	const navigate = useNavigate();

	const handleLogout = () => {
		logout();
		navigate("/");
	};

	useEffect(() => {
		const fetchProfile = async () => {
			try {
				const data = await getMyProfile();
				setProfile(data);
			} catch (err) {
				setError('Unable to fetch profile.');
			}
		};
		fetchProfile();
	}, []);

	useEffect(() => {
		if (profile && profile.userId) {
			const fetchReservations = async () => {
				try {
					const data = await getMyReservations(profile.userId);
					setReservations(data);
				} catch (err) {
					setResError('Unable to fetch reservations.');
				}
			};
			fetchReservations();
		}
	}, [profile]);

	if (error) {
		return (
			<div className="my-account-page">
				<div className="my-account-container">
					<p className="error">{error}</p>
				</div>
			</div>
		);
	}
	if (!profile) {
		return (
			<div className="my-account-page">
				<div className="my-account-container">
					<p>Loading profile...</p>
				</div>
			</div>
		);
	}

	return (
		<div className="my-account-page">
			<div className="my-account-container">
				<h2>My Account</h2>

				<div className="profile">
					<div className="profile-item">
						<strong>Username:</strong> {profile.username}
					</div>
					<div className="profile-item">
						<strong>Email:</strong> {profile.email}
					</div>
					<div className="profile-item">
						<strong>Status:</strong> {profile.status}
					</div>
				</div>

				<div className="actions">
					<Link to="/account/UpdateProfilePage" className="action-link">
						Update Profile
					</Link>
					<Link to="/billing" className="action-link">
						Billing
					</Link>
					<Link to="/account/guestpass" className="action-link">
						Purchase Guest Pass
					</Link>
					<button onClick={handleLogout} className="logout-btn">
						Logout
					</button>
				</div>

				<h3>My Court Reservations</h3>
				{resError && <p className="res-error">{resError}</p>}
				<div className="reservations">
					{reservations.length === 0 ? (
						<p>No reservations found.</p>
					) : (
						<ul>
							{reservations.map((r) => (
								<li key={r.reservationId}>
									Court {r.courtNumber} on{" "}
									{new Date(r.reservationDate).toLocaleDateString()} from{" "}
									{r.startTime} to {r.endTime}
								</li>
							))}
						</ul>
					)}
				</div>
			</div>
		</div>
	);
};

export default MyAccountPage;
