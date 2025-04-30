import api from './api';

// Fetch the list of logs from the backend
export async function getAuditLogs() {
  const response = await api.get('/api/admin/audit/logs');
  return response.data;
}
