// src/pages/treasurer/FinancialTransactionsPage.js
import React, { useEffect, useState } from 'react';
import { getAllTransactions } from '../services/financialService';

const FinancialTransactionsPage = () => {
    const [transactions, setTransactions] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTransactions = async () => {
            try {
                const data = await getAllTransactions();
                setTransactions(data);
            } catch (err) {
                setError('Unable to fetch transactions.');
            }
        };
        fetchTransactions();
    }, []);

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h2>Financial Transactions</h2>
            <ul>
                {transactions.map(tx => (
                    <li key={tx.transactionId}>
                        Amount: {tx.amount} | Type: {tx.transactionType} | Date: {tx.transactionDate}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default FinancialTransactionsPage;
