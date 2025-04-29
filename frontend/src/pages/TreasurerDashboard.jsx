import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/TreasurerDashboard.css';

const TreasurerDashboard = () => {
	// Wrap everything in a blur-background page container
	return (
		<div className="treasurer-page">
			<div className="treasurer-content">
				<h2>Treasurer Dashboard</h2>
				<ul className="treasurer-nav">
					<li>
						<Link to="/treasurer/refunds">Process Refunds</Link>
					</li>
					<li>
						<Link to="/treasurer/reports">Generate Reports</Link>
					</li>
					<li>
						<Link to="/treasurer/billing">Billing Management</Link>
					</li>
				</ul>
			</div>
		</div>
	);
};

export default TreasurerDashboard;
