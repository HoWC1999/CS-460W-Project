import React, { useEffect, useState } from 'react';
import '../styles/Events.css';

const sampleEvents = [
	{
		id: 1,
		title: 'Grand Opening of Our New Courts',
		description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus lacinia odio vitae vestibulum vestibulum.',
		date: '2025-05-01',
		time: '10:00 AM',
		location: 'Main Tennis Complex'
	},
	{
		id: 2,
		title: 'All-Day Free Play',
		description: 'Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Cras justo odio, dapibus ac facilisis in.',
		date: '2025-05-07',
		time: '8:00 AM – 5:00 PM',
		location: 'All Courts'
	},
	{
		id: 3,
		title: 'Weekend Club Mixer',
		description: 'Donec ullamcorper nulla non metus auctor fringilla. Etiam porta sem malesuada magna mollis euismod.',
		date: '2025-05-14',
		time: '2:00 PM – 6:00 PM',
		location: 'Court 5 & 6'
	}
];

const Events = () => {
	const [events, setEvents] = useState([]);

	// simulate fetching from your API
	useEffect(() => {
		setEvents(sampleEvents);
	}, []);

	return (
		<div className="events-page">
			<div className="events-container">
				<h2 className="events-header">Upcoming Events</h2>
				{events.map((event, idx) => (
					<div
						key={event.id}
						className="event-card"
						style={{ '--order': idx }}
					>
						<div className="event-meta">
							<span className="event-date">{event.date}</span>
							<span className="event-time">{event.time}</span>
							<span className="event-location">{event.location}</span>
						</div>
						<h3 className="event-title">{event.title}</h3>
						<p className="event-description">{event.description}</p>
					</div>
				))}
			</div>
		</div>
	);
};

export default Events;
