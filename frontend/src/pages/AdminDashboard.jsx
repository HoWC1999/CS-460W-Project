// src/pages/admin/AdminDashboard.jsx
// src/pages/admin/AdminDashboard.jsx
import React, { useEffect, useState } from 'react';
import '../styles/AdminDashboard.css';
import { getAllUsers, deleteUser, assignRole, getAllPasses } from "../services/adminService";
import { getAllEvents, createEvent, updateEvent, cancelEvent } from "../services/eventService";


const AdminDashboard = () => {
  // Users state
  const [users, setUsers] = useState([]);
  const [userError, setUserError] = useState(null);

  // Roles state
  const [selectedUserId, setSelectedUserId] = useState('');
  const [role, setRole] = useState('');
  const [roleMessage, setRoleMessage] = useState('');

  // Events state
  const [events, setEvents] = useState([]);
  const [eventError, setEventError] = useState('');
  const [newEvent, setNewEvent] = useState({ title: '', description: '', eventDate: '', eventTime: '', location: '' });
  const [editEvent, setEditEvent] = useState(null);

  // Guest passes state
  const [passes, setPasses] = useState([]);
  const [passError, setPassError] = useState(null);

  // Fetch all data on mount
  useEffect(() => {
    fetchUsers();
    fetchEvents();
    fetchPasses();
  }, []);

  // ----- Users -----
  const fetchUsers = async () => {
    try {
      const data = await getAllUsers();
      setUsers(data);
    } catch (err) {
      setUserError('Unable to fetch users.');
    }
  };

  const handleDeleteUser = async (userId) => {
    await deleteUser(userId);
    setUsers(users.filter(u => u.userId !== userId));
  };

  const handleAssignRole = async (e) => {
    e.preventDefault();
    try {
      await assignRole(selectedUserId, role);
      setRoleMessage('Role assigned successfully.');
    } catch (err) {
      setRoleMessage('Role assignment failed.');
    }
  };

  // ----- Events -----
  const fetchEvents = async () => {
    try {
      const data = await getAllEvents();
      setEvents(data);
    } catch (err) {
      setEventError('Error fetching events');
    }
  };

  const handleNewEventChange = e => setNewEvent({ ...newEvent, [e.target.name]: e.target.value });
  const handleCreateEvent = async e => {
    e.preventDefault();
    await createEvent(newEvent);
    fetchEvents();
    setNewEvent({ title: '', description: '', eventDate: '', eventTime: '', location: '' });
  };

  const handleEditClick = event => {
    setEditEvent({
      ...event,
      eventDate: event.eventDate.split('T')[0],
      eventTime: event.eventTime.slice(0,5)
    });
  };

  const handleEditEventChange = e => setEditEvent({ ...editEvent, [e.target.name]: e.target.value });
  const handleUpdateEvent = async e => {
    e.preventDefault();
    await updateEvent(editEvent.eventId, editEvent);
    setEditEvent(null);
    fetchEvents();
  };

  const handleDeleteEvent = async id => {
    await cancelEvent(id);
    fetchEvents();
  };

  // ----- Guest Passes -----
  const fetchPasses = async () => {
    try {
      const data = await getAllPasses();
      setPasses(data);
    } catch {
      setPassError('Unable to fetch passes.');
    }
  };

  return (
    <div className="admin-dashboard">
      {/* User Management Section */}
      <section className="section">
        <h2 className="section-header">User Management</h2>
        {userError ? <p className="error">{userError}</p> : (
          <table className="admin-table">
            <thead>
            <tr>
              <th>Username</th><th>Role</th><th>Email</th><th>Name</th><th>Address</th><th>Phone</th><th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {users.map(u => (
              <tr key={u.userId}>
                <td>{u.username}</td><td>{u.role}</td><td>{u.email}</td><td>{u.fullName}</td><td>{u.address}</td><td>{u.phoneNumber}</td>
                <td><button onClick={() => handleDeleteUser(u.userId)}>Delete</button></td>
              </tr>
            ))}
            </tbody>
          </table>
        )}

        <h3>Assign Roles</h3>
        <form className="admin-form" onSubmit={handleAssignRole}>
          <select value={selectedUserId} onChange={e => setSelectedUserId(e.target.value)} required>
            <option value="">Select user</option>
            {users.map(u => <option key={u.userId} value={u.userId}>{u.username}</option>)}
          </select>
          <select value={role} onChange={e => setRole(e.target.value)} required>
            <option value="">Select role</option>
            <option value="MEMBER">Member</option>
            <option value="TREASURER">Treasurer</option>
            <option value="ADMIN">Admin</option>
          </select>
          <button type="submit">Assign</button>
          {roleMessage && <p className="message">{roleMessage}</p>}
        </form>
      </section>

      {/* Event Management Section */}
      <section className="section">
        <h2 className="section-header">Event Management</h2>
        {eventError && <p className="error">{eventError}</p>}
        <div className="event-forms">
          <form className="admin-form" onSubmit={handleCreateEvent}>
            <h4>Create Event</h4>
            <input name="title" placeholder="Title" value={newEvent.title} onChange={handleNewEventChange} required />
            <textarea name="description" placeholder="Description" value={newEvent.description} onChange={handleNewEventChange} />
            <input type="date" name="eventDate" value={newEvent.eventDate} onChange={handleNewEventChange} required />
            <input type="time" name="eventTime" value={newEvent.eventTime} onChange={handleNewEventChange} required />
            <input name="location" placeholder="Location" value={newEvent.location} onChange={handleNewEventChange} required />
            <button type="submit">Create</button>
          </form>

          {editEvent && (
            <form className="admin-form" onSubmit={handleUpdateEvent}>
              <h4>Edit Event</h4>
              <input name="title" value={editEvent.title} onChange={handleEditEventChange} required />
              <textarea name="description" value={editEvent.description} onChange={handleEditEventChange} />
              <input type="date" name="eventDate" value={editEvent.eventDate} onChange={handleEditEventChange} required />
              <input type="time" name="eventTime" value={editEvent.eventTime} onChange={handleEditEventChange} required />
              <input name="location" value={editEvent.location} onChange={handleEditEventChange} required />
              <button type="submit">Update</button>
              <button type="button" onClick={() => setEditEvent(null)}>Cancel</button>
            </form>
          )}
        </div>

        <ul className="event-list">
          {events.map(evt => (
            <li key={evt.eventId} className="event-item">
              <div><strong>{evt.title}</strong> on {new Date(evt.eventDate).toLocaleDateString()} at {evt.eventTime}</div>
              <div>{evt.description}</div>
              <div>Location: {evt.location}</div>
              <button onClick={() => handleEditClick(evt)}>Edit</button>
              <button onClick={() => handleDeleteEvent(evt.eventId)}>Delete</button>
            </li>
          ))}
        </ul>
      </section>

      {/* Guest Passes Section */}
      <section className="section">
        <h2 className="section-header">Guest Passes</h2>
        {passError ? <p className="error">{passError}</p> : (
          <ul className="pass-list">
            {passes.map(p => (
              <li key={p.guestPassId}>
                <strong>ID:</strong> {p.guestPassId} | <strong>User:</strong> {p.userId} | <strong>Purchased:</strong> {new Date(p.purchaseDate).toLocaleString()} | <strong>Expires:</strong> {new Date(p.expirationDate).toLocaleString()}
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  );
};

export default AdminDashboard;
