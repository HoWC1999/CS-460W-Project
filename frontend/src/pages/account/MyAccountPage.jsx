// src/pages/account/MyAccountPage.jsx
import React, { useContext, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getMyProfile } from '../../services/userService';
import {
  getMyReservations,
  cancelReservation
} from '../../services/reservationService';
import { getMyGuestPasses } from '../../services/guestPassService';
import { AuthContext } from '../../context/AuthContext';
import '../../styles/MyAccountPage.css';

const MyAccountPage = () => {
  const [profile, setProfile] = useState(null);
  const [reservations, setReservations] = useState([]);
  const [guestPasses, setGuestPasses] = useState([]);
  const [error, setError] = useState(null);
  const { logout } = useContext(AuthContext);

  // Fetch profile once
  useEffect(() => {
    getMyProfile()
      .then(setProfile)
      .catch(() => setError('Unable to fetch profile.'));
  }, []);

  // Fetch reservations once profile is loaded
  useEffect(() => {
    if (profile?.userId) {
      getMyReservations(profile.userId)
        .then(setReservations)
        .catch(() => setError('Unable to fetch reservations.'));
    }
  }, [profile]);



  const handleCancel = async (reservationId) => {
    if (!window.confirm('Cancel this reservation?')) return;
    try {
      await cancelReservation(reservationId);
      setReservations(r => r.filter(x => x.reservationId !== reservationId));
    } catch (e) {
      alert('Cancellation failed: ' + e);
    }
  };

  if (error) return <p className="error">{error}</p>;
  if (!profile) return <p>Loading profile…</p>;

  // count active (unused & unexpired) passes
  const activePasses = guestPasses.filter(p => !p.used && new Date(p.expirationDate) > new Date());
  const remaining = profile.guestPassesRemaining ?? 0;

  return (
    <div className="my-account-page">
      <div className="my-account-container">
        <h2>My Account</h2>

        {/* Full profile */}
        <div className="profile-grid">
          <div><strong>Username:</strong> {profile.username}</div>
          <div><strong>Full Name:</strong> {profile.fullName}</div>
          <div><strong>Email:</strong> {profile.email}</div>
          <div><strong>Phone:</strong> {profile.phoneNumber || '—'}</div>
          <div><strong>Address:</strong> {profile.address || '—'}</div>
          <div><strong>Status:</strong> {profile.status}</div>
          <div><strong>Billing Plan:</strong> {profile.billingPlan}</div>
        </div>

        {/* Actions */}
        <div className="actions">
          <Link to="/account/update" className="action-link">
            Update Profile
          </Link>
          <button className="logout-btn" onClick={logout}>
            Logout
          </button>
        </div>

        {/* Reservations */}
        <section className="reservations">
          <h3>My Reservations</h3>
          {reservations.length === 0
            ? <p>No active reservations.</p>
            : (
              <ul className="reservation-list">
                {reservations.map(r => (
                  <li key={r.reservationId}>
                    <div><strong>Court:</strong> {r.courtNumber}</div>
                    <div>
                      <strong>Date:</strong> {r.reservationDate.substring(0,10)}
                    </div>
                    <div>
                      <strong>Time:</strong> {r.startTime} – {r.endTime}
                    </div>
                    <button
                      className="cancel-btn"
                      onClick={() => handleCancel(r.reservationId)}
                    >
                      Cancel
                    </button>
                  </li>
                ))}
              </ul>
            )
          }
        </section>

        {/* Guest Passes */}
        <section className="guest-passes">
          <h3>My Guest Passes</h3>
          <p>
            <strong>Remaining this month:</strong> {remaining}
          </p>
          {activePasses.length === 0
            ? <p>No active guest passes.</p>
            : (
              <ul className="pass-list">
                {activePasses.map(p => (
                  <li key={p.guestPassId}>
                    <div><strong>Pass ID:</strong> {p.guestPassId}</div>
                    <div>
                      <strong>Purchased:</strong> {new Date(p.purchaseDate).toLocaleDateString()}
                    </div>
                    <div>
                      <strong>Expires:</strong> {new Date(p.expirationDate).toLocaleDateString()}
                    </div>
                  </li>
                ))}
              </ul>
            )
          }
        </section>
      </div>
    </div>
  );
};

export default MyAccountPage;
