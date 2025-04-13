import React, { useEffect, useState, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getMyProfile } from '../../services/userService';
import { getMyReservations } from '../../services/reservationService';
import { AuthContext } from '../../context/AuthContext';

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

    // Fetch the user's profile on component mount
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

    // Once profile is fetched, use the profile.user_id to fetch reservations
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
        return <p>{error}</p>;
    }
    if (!profile) {
        return <p>Loading profile...</p>;
    }

    return (
        <div>
            <h2>My Account</h2>
            <p><strong>Username:</strong> {profile.username}</p>
            <p><strong>Email:</strong> {profile.email}</p>
            <p><strong>Status:</strong> {profile.status}</p>
            <p><Link to="/account/UpdateProfilePage">Update Profile</Link></p>
            <p><Link to="/billing">Billing</Link></p>
            <p><Link to="/account/guestpass">Purchase Guest Pass</Link></p>
            <br />
            <button onClick={handleLogout}>Logout</button>

            <h3>My Court Reservations</h3>
            {resError && <p>{resError}</p>}
            {reservations.length === 0 ? (
                <p>No reservations found.</p>
            ) : (
                <ul>
                    {reservations.map((reservation) => (
                        <li key={reservation.reservationId}>
                            Court {reservation.courtNumber} on {new Date(reservation.reservationDate).toLocaleDateString()} from {reservation.startTime} to {reservation.endTime}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default MyAccountPage;
