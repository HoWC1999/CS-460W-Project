// src/pages/treasurer/TreasurerDashboard.js
import React from 'react';
import { Link } from 'react-router-dom';

const TreasurerDashboard = () => {
    return (
        <div>
            <h2>Treasurer Dashboard</h2>
            <ul>
                <li><Link to="/treasurer/transactions">Manage Financial Transactions</Link></li>
                <li><Link to="/treasurer/refunds">Process Refunds</Link></li>
                <li><Link to="/treasurer/reports">Generate Reports</Link></li>
                <li><Link to ="/treasurer/billing">Billing Management</Link></li>
            </ul>
        </div>
    );
};

export default TreasurerDashboard;
