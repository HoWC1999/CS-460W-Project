import React, { useEffect, useState } from "react";
import { getAllEvents, createEvent, updateEvent, cancelEvent } from "../services/eventService";
import '../styles/AdminEventManagementPage.css';

const AdminEventManagementPage = () => {
	const [events, setEvents] = useState([]);
	const [error, setError] = useState("");
	const [newEvent, setNewEvent] = useState({
		title: "",
		description: "",
		eventDate: "",
		eventTime: "",
		location: "",
	});
	const [editEvent, setEditEvent] = useState(null);

	const fetchEvents = async () => {
		try {
			const data = await getAllEvents();
			setEvents(data);
		} catch (err) {
			setError("Error fetching events: " + err);
		}
	};

	useEffect(() => {
		fetchEvents();
	}, []);

	const handleNewEventChange = (e) => {
		setNewEvent({ ...newEvent, [e.target.name]: e.target.value });
	};

	const handleCreateEvent = async (e) => {
		e.preventDefault();
		try {
			await createEvent(newEvent);
			fetchEvents();
			setNewEvent({
				title: "",
				description: "",
				eventDate: "",
				eventTime: "",
				location: "",
			});
		} catch (err) {
			setError("Error creating event: " + err);
		}
	};

	const handleEditEventChange = (e) => {
		setEditEvent({ ...editEvent, [e.target.name]: e.target.value });
	};

	const handleUpdateEvent = async (e) => {
		e.preventDefault();
		try {
			await updateEvent(editEvent.eventId, editEvent);
			fetchEvents();
			setEditEvent(null);
		} catch (err) {
			setError("Error updating event: " + err);
		}
	};

	const handleDeleteEvent = async (eventId) => {
		try {
			await cancelEvent(eventId);
			fetchEvents();
		} catch (err) {
			setError("Error deleting event: " + err);
		}
	};

	const handleEditClick = (event) => {
		setEditEvent({
			...event,
			eventDate: event.eventDate ? event.eventDate.split("T")[0] : "",
			eventTime: event.eventTime ? event.eventTime.split("T")[1].substring(0,5) : "",
		});
	};

	return (
		<div className="admin-event-management-page">
			<div className="admin-dashboard-container">
				<h2>Admin Event Management</h2>
				{error && <p className="error">{error}</p>}

				<section className="create-event">
					<h3>Create New Event</h3>
					<form onSubmit={handleCreateEvent}>
						<input
							type="text"
							name="title"
							placeholder="Title"
							value={newEvent.title}
							onChange={handleNewEventChange}
							required
						/>
						<textarea
							name="description"
							placeholder="Description"
							value={newEvent.description}
							onChange={handleNewEventChange}
						/>
						<input
							type="date"
							name="eventDate"
							value={newEvent.eventDate}
							onChange={handleNewEventChange}
							required
						/>
						<input
							type="time"
							name="eventTime"
							value={newEvent.eventTime}
							onChange={handleNewEventChange}
							required
						/>
						<input
							type="text"
							name="location"
							placeholder="Location"
							value={newEvent.location}
							onChange={handleNewEventChange}
							required
						/>
						<button type="submit">Create Event</button>
					</form>
				</section>

				<section className="event-list">
					<h3>Existing Events</h3>
					{events.length === 0 ? (
						<p>No events found.</p>
					) : (
						<ul>
							{events.map(event => (
								<li key={event.eventId}>
									<div>
										<strong>{event.title}</strong> on{" "}
										{new Date(event.eventDate).toLocaleDateString()} at {event.eventTime}
									</div>
									<div>{event.description}</div>
									<div>Location: {event.location}</div>
									<button onClick={() => handleEditClick(event)}>Edit</button>
									<button onClick={() => handleDeleteEvent(event.eventId)}>Delete</button>
								</li>
							))}
						</ul>
					)}
				</section>

				{editEvent && (
					<section className="edit-event">
						<h3>Edit Event</h3>
						<form onSubmit={handleUpdateEvent}>
							<input
								type="text"
								name="title"
								placeholder="Title"
								value={editEvent.title}
								onChange={handleEditEventChange}
								required
							/>
							<textarea
								name="description"
								placeholder="Description"
								value={editEvent.description}
								onChange={handleEditEventChange}
							/>
							<input
								type="date"
								name="eventDate"
								value={editEvent.eventDate}
								onChange={handleEditEventChange}
								required
							/>
							<input
								type="time"
								name="eventTime"
								value={editEvent.eventTime}
								onChange={handleEditEventChange}
								required
							/>
							<input
								type="text"
								name="location"
								placeholder="Location"
								value={editEvent.location}
								onChange={handleEditEventChange}
								required
							/>
							<button type="submit">Update Event</button>
							<button type="button" onClick={() => setEditEvent(null)}>
								Cancel
							</button>
						</form>
					</section>
				)}
			</div>
		</div>
	);
};

export default AdminEventManagementPage;
