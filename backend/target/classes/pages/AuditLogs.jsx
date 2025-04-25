import React, { useEffect, useState } from 'react';
import { getAuditLogs } from '../services/auditService';
import { Link } from 'react-router-dom';

function AuditLogs() {
  const [logs, setLogs] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    getAuditLogs()
      .then(data => setLogs(data))
      .catch(err => setError(err.message));
  }, []);

  return (
    <div className="audit-log-page">
      <h2>Audit Logs</h2>
      <nav><Link to="/admin">Back to Dashboard</Link></nav>

      {error && <p className="error">Error loading logs: {error}</p>}
      {!error && logs.length === 0 && <p>No log entries found.</p>}

      {logs.length > 0 && (
        <table>
          <thead>
          <tr>
            <th>Timestamp</th>
            <th>User</th>
            <th>Action</th>
            <th>Details</th>
          </tr>
          </thead>
          <tbody>
          {logs.map(log => (
            <tr key={log.id}>
              <td>{new Date(log.timestamp).toLocaleString()}</td>
              <td>{log.username}</td>
              <td>{log.action}</td>
              <td>{log.details}</td>
            </tr>
          ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
export default AuditLogs
