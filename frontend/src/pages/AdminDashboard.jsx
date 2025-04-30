// src/pages/admin/AdminDashboard.jsx
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getAllPasses } from '../services/adminService';
import {
  getAllReservations,
  cancelReservation
} from '../services/reservationService';
import '../styles/AdminDashboard.css';

const AdminDashboard = () => {
  const [passes, setPasses] = useState([]);
  const [reservations, setReservations] = useState([]);
  const [error, setError] = useState(null);

  // Fetch guest passes
  useEffect(() => {
    (async () => {
      try {
        const data = await getAllPasses();
        setPasses(data);
      } catch {
        setError('Unable to fetch guest passes.');
      }
    })();
  }, []);

  // Fetch all court reservations
  useEffect(() => {
    (async () => {
      try {
        const data = await getAllReservations();
        setReservations(data);
      } catch {
        setError('Unable to fetch reservations.');
      }
    })();
  }, []);

  // Cancel a reservation and remove it from state
  const handleCancel = async (reservationId) => {
    if (!window.confirm('Are you sure you want to cancel this reservation?')) return;
    try {
      await cancelReservation(reservationId);
      setReservations(res =>
        res.filter(r => r.reservationId !== reservationId)
      );
    } catch {
      alert('Cancellation failed');
    }
  };

  return (
    <div className="admin-dashboard-page">
      <div className="admin-dashboard-container">
        <h2>Admin Dashboard</h2>
        <nav className="admin-links">
          <Link to="/admin/users">Manage Users</Link>
          <Link to="/admin/assign-role">Assign Roles</Link>
          <Link to="/admin/events">Manage Events</Link>
        </nav>

        {error && <p className="error">{error}</p>}

        <section>
          <h3>Guest Passes</h3>
          {passes.length === 0
            ? <p>No guest passes found.</p>
            : (
              <table className="passes-table">
                <thead>
                <tr>
                  <th>Pass ID</th>
                  <th>User Name</th>
                  <th>Member Name</th>
                  <th>Purchase Date</th>
                  <th>Expiration Date</th>
                </tr>
                </thead>
                <tbody>
                {passes.map(pass => (
                  <tr key={pass.guestPassId}>
                    <td>{pass.guestPassId}</td>
                    <td>{pass.user?.username || 'N/A'}</td>
                    <td>{pass.user?.fullName}</td>
                    <td>{new Date(pass.purchaseDate).toLocaleString()}</td>
                    <td>{new Date(pass.expirationDate).toLocaleString()}</td>
                  </tr>
                ))}
                </tbody>
              </table>
            )
          }
        </section>

        <section>
          <h3>All Court Reservations</h3>
          {reservations.length === 0
            ? <p>No reservations found.</p>
            : (
              <table className="reservations-table">
                <thead>
                <tr>
                  <th>Reservation ID</th>
                  <th>User</th>
                  <th>Member Name</th>
                  <th>Court #</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {reservations.map(res => (
                  <tr key={res.reservationId}>
                    <td>{res.reservationId}</td>
                    <td>{res.bookedBy?.username || res.bookedBy?.email}</td>
                    <td>{res.bookedBy?.fullName}</td>
                    <td>{res.courtNumber}</td>
                    <td>{res.reservationDate.substring(0, 10)} from{" "}</td>
                    <td>{res.startTime}–{res.endTime}</td>
                    <td>
                      <button
                        className="cancel-btn"
                        onClick={() => handleCancel(res.reservationId)}
                      >
                        Cancel
                      </button>
                    </td>
                  </tr>
                ))}
                </tbody>
              </table>
            )
          }
        </section>
      </div>
    </div>
  );
};

export default AdminDashboard;
