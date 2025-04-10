// src/pages/admin/AdminDashboard.jsx
import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import {getAllPasses, getAllUsers} from "../services/adminService";

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
        <div>
            <h2>Admin Dashboard</h2>
            <ul>
                <li><Link to="/admin/users">Manage Users</Link></li>
                <li><Link to="/admin/assign-role">Assign Roles</Link></li>
                <li><Link to={"/admin/events"}>Manage Events</Link></li>
              <p/>
              <h3>Guest Passes</h3>
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
    );
};

export default AdminDashboard;
