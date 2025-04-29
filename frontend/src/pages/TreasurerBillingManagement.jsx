// src/pages/TreasurerBillingManagementPage.jsx
import React, { useEffect, useState } from 'react'
import { getAllBilling, applyLateFee, chargeAnnualMembershipFee } from '../services/billingService'
import { getAllUsers } from '../services/adminService'
import '../styles/TreasurerBillingManagement.css'

const TreasurerBillingManagementPage = () => {
	const [billingRecords, setBillingRecords] = useState([])
	const [error, setError] = useState('')
	const [userId, setUserId] = useState('')
	const [amount, setAmount] = useState('')
	const [baseAmount, setBaseAmount] = useState('')
	const [users, setUsers] = useState([])
	const [selectedUserId, setSelectedUserId] = useState('')
	const [message, setMessage] = useState('')

	useEffect(() => {
		const fetchBillingRecords = async () => {
			try {
				const data = await getAllBilling()
				setBillingRecords(data)
			} catch {
				setError('Unable to fetch billing records.')
			}
		}
		fetchBillingRecords()
	}, [])

	useEffect(() => {
		const fetchUsers = async () => {
			try {
				const usersData = await getAllUsers()
				setUsers(usersData)
			} catch {
				setMessage('Unable to fetch users.')
			}
		}
		fetchUsers()
	}, [])

	const handleApplyLateFee = async e => {
		e.preventDefault()
		try {
			await applyLateFee(Number(userId), Number(baseAmount))
			const data = await getAllBilling()
			setBillingRecords(data)
		} catch {
			setError('Late fee application failed.')
		}
	}

	const handleChargeMembership = async e => {
		e.preventDefault()
		try {
			await chargeAnnualMembershipFee(Number(selectedUserId), Number(amount))
			const data = await getAllBilling()
			setBillingRecords(data)
		} catch {
			setError('Annual membership fee processing failed.')
		}
	}

	return (
		<div className="treasurer-billing-page">
			<div className="treasurer-billing-container">
				<h2>Billing Management</h2>
				{error && <p className="error">{error}</p>}

				<div className="billing-actions">
					<form onSubmit={handleChargeMembership}>
						<h3>Charge Annual Membership Fee</h3>
						<label htmlFor="userSelect">Select User:</label>
						<select
							id="userSelect"
							value={selectedUserId}
							onChange={e => setSelectedUserId(e.target.value)}
							required
						>
							<option value="">--Select a user--</option>
							{users.map(u => (
								<option key={u.userId} value={u.userId}>
									{u.username} (ID: {u.userId})
								</option>
							))}
						</select>
						<label>Amount:</label>
						<input
							type="number"
							value={amount}
							onChange={e => setAmount(e.target.value)}
							required
						/>
						<button type="submit">Charge Membership Fee</button>
					</form>

					<form onSubmit={handleApplyLateFee}>
						<h3>Apply Late Fee</h3>
						<label>User ID:</label>
						<input
							type="number"
							value={userId}
							onChange={e => setUserId(e.target.value)}
							required
						/>
						<label>Base Amount:</label>
						<input
							type="number"
							value={baseAmount}
							onChange={e => setBaseAmount(e.target.value)}
							required
						/>
						<button type="submit">Apply Late Fee</button>
					</form>
				</div>

				<h3>All Billing Records</h3>
				{billingRecords.length === 0 ? (
					<p>No billing records found.</p>
				) : (
					<table className="billing-table">
						<thead>
							<tr>
								<th>Transaction ID</th>
								<th>User ID</th>
								<th>Amount</th>
								<th>Type</th>
								<th>Date</th>
								<th>Description</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							{billingRecords.map(rec => (
								<tr key={rec.transactionId}>
									<td>{rec.transactionId}</td>
									<td>{rec.user?.userId ?? 'N/A'}</td>
									<td>{rec.amount}</td>
									<td>{rec.transactionType}</td>
									<td>{new Date(rec.transactionDate).toLocaleString()}</td>
									<td>{rec.description}</td>
									<td>{rec.status}</td>
								</tr>
							))}
						</tbody>
					</table>
				)}
			</div>
		</div>
	)
}

export default TreasurerBillingManagementPage
