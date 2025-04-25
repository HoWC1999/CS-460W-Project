import React, { useEffect, useState } from 'react';
import { getAllEvents } from '../services/eventService';

const Events = () => {
    const [Events, setEvents] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
      (async () => {
            try {
                const data = await getAllEvents();
                setEvents(data);
            } catch (err) {
                setError('Unable to fetch events.');
            }
      })();
    }, []);

    if (error) {
        return <p>{error}</p>;
    }
    return (
        <div>
            <h2>Events</h2>
            <ul>
                {Events.map(tx => (
                    <li key={tx.eventId}>
                        Title: {tx.title} | Description: {tx.description} | Date: {tx.eventDate} | Time: {tx.eventTime} | Location: {tx.location}
                    </li>
                ))}
            </ul>
        </div>
    );
}


export default Events;
