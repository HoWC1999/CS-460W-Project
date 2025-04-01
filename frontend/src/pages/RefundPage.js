// src/pages/treasurer/RefundPage.js
import React, { useState } from 'react';
import { processRefund } from '../services/financialService';

const RefundPage = () => {
    const [formData, setFormData] = useState({ transactionId: '', amount: '' });
    const [message, setMessage] = useState('');

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await processRefund(formData);
            setMessage('Refund processed: ' + JSON.stringify(response));
        } catch (err) {
            setMessage('Refund failed.');
        }
    };

    return (
        <div>
            <h2>Process Refund</h2>
            <form onSubmit={handleSubmit}>
                <label>Transaction ID:</label>
                <input type="text" name="transactionId" onChange={handleChange} />

                <label>Refund Amount:</label>
                <input type="number" name="amount" onChange={handleChange} />

                <button type="submit">Refund</button>
            </form>
            <p>{message}</p>
        </div>
    );
};

export default RefundPage;
