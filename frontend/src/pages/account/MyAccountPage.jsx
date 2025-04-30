// src/pages/account/MyAccountPage.jsx
import React, { useContext, useEffect, useState } from 'react';
import { getMyProfile } from '../../services/userService';
import {
  getMyReservations,
  cancelReservation
} from '../../services/reservationService';
import { AuthContext } from '../../context/AuthContext';
import '../../styles/MyAccountPage.css';

const MyAccountPage = () => {
  const [profile, setProfile] = useState(null);
  const [reservations, setReservations] = useState([]);
  const [error, setError] = useState(null);
  const { logout } = useContext(AuthContext);

  // Fetch profile once
  useEffect(() => {
    getMyProfile()
      .then(setProfile)
      .catch(() => setError('Unable to fetch profile'));
  }, []);

  // Whenever profile loads, fetch that user’s reservations
  useEffect(() => {
    if (profile?.userId) {
      getMyReservations(profile.userId)
        .then(setReservations)
        .catch(() => setError('Unable to fetch reservations'));
    }
  }, [profile]);

  const handleCancel = async (reservationId) => {
    if (!window.confirm('Cancel this reservation?')) return;
    try {
      await cancelReservation(reservationId);
      // remove from list
      setReservations(res =>
        res.filter(r => r.reservationId !== reservationId)
      );
    } catch (e) {
      alert('Cancellation failed: ' + e);
    }
  };

  if (error) return <p className="error">{error}</p>;
  if (!profile) return <p>Loading profile…</p>;

  return (
    <div className="my-account-page">
      <h2>My Account</h2>
      <p><strong>Username:</strong> {profile.username}</p>
      <p><strong>Email:</strong> {profile.email}</p>

      <h3>My Reservations</h3>
      {reservations.length === 0
        ? <p>No active reservations.</p>
        : (
          <ul className="reservation-list">
            {reservations.map(r => (
              <li key={r.reservationId}>
                Court {r.courtNumber} on{' '}
                {new Date(r.reservationDate).toLocaleDateString()}{' '}
                {r.startTime}–{r.endTime}
                <button
                  className="cancel-btn"
                  onClick={() => handleCancel(r.reservationId)}
                >
                  Cancel
                </button>
              </li>
            ))}
          </ul>
        )}
      <button onClick={logout} className="logout-btn">Logout</button>
    </div>
  );
};

export default MyAccountPage;
