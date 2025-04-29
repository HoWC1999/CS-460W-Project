// src/pages/Events.jsx
import React, { useContext, useEffect, useState } from 'react';
import '../styles/Events.css';
import {
  getAllEvents,
  getEventRegistrations,
  signupForEvent
} from '../services/eventService';
import { AuthContext } from '../context/AuthContext';

const Events = () => {
  const [events, setEvents]           = useState([]);
  const [registrations, setRegistrations] = useState({}); // { [eventId]: [EventRegistration] }
  const [loading, setLoading]         = useState(true);
  const [error, setError]             = useState('');
  const { user }                      = useContext(AuthContext);

  useEffect(() => {
    async function loadAll() {
      try {
        // 1) fetch events
        const evts = await getAllEvents();
        setEvents(evts);

        // 2) fetch registrations in parallel
        const regsArrays = await Promise.all(
          evts.map(evt => getEventRegistrations(evt.eventId))
        );
        const map = {};
        evts.forEach((evt, i) => {
          map[evt.eventId] = regsArrays[i];
        });
        setRegistrations(map);
      } catch (err) {
        console.error('Error loading events or registrations', err);
        setError(typeof err === 'string' ? err : 'Unable to load events');
      } finally {
        setLoading(false);
      }
    }

    loadAll();
  }, []);

  const handleSignup = async (eventId) => {
    if (!user) {
      alert('Please log in to sign up.');
      return;
    }
    try {
      await signupForEvent(eventId, { userId: user.userId });
      // re-fetch this event’s registrations
      const updated = await getEventRegistrations(eventId);
      setRegistrations(regs => ({ ...regs, [eventId]: updated }));
      alert('Successfully signed up!');
    } catch (e) {
      alert('Error signing up: ' + (typeof e === 'string' ? e : e.message));
    }
  };

  if (loading) return <p>Loading events…</p>;
  if (error)   return <p className="error">{error}</p>;

  return (
    <div className="events-page">
      <div className="events-container">
        <h2 className="events-header">Upcoming Events</h2>
        {events.length === 0 && <p>No upcoming events.</p>}
        {events.map((evt, idx) => (
          <div
            key={evt.eventId}
            className="event-card"
            style={{ '--order': idx }}
          >
            <div className="event-meta">
              <span className="event-date">{evt.eventDate}</span>
              <span className="event-time">{evt.eventTime}</span>
              <span className="event-location">{evt.location}</span>
            </div>
            <h3 className="event-title">{evt.title}</h3>
            <p className="event-description">{evt.description}</p>

            <button onClick={() => handleSignup(evt.eventId)}>
              Sign Up
            </button>

            {registrations[evt.eventId]?.length > 0 && (
              <div className="event-registrations">
                <h4>Registered Users:</h4>
                <ul>
                  {registrations[evt.eventId].map(reg => (
                    <li key={reg.id}>{reg.user.username}</li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Events;
