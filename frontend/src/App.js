import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Core pages
import HomePage from './pages/HomePage';
import CourtReservation from './pages/CourtReservation';
import Navbar from './pages/Navbar';
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import RefundPage from "./pages/RefundPage";

// Account Management Pages
import MyAccountPage from './pages/MyAccountPage';
import UpdateProfilePage from './pages/UpdateProfilePage';

// Treasurer Pages
import TreasurerDashboard from './pages/TreasurerDashboard';
import FinancialTransactionsPage from './pages/FinancialTransactionPage';

// Admin Pages
import AdminDashboard from './pages/AdminDashboard';
import UserManagementPage from './pages/UserManagementPage';
import AssignRolePage from './pages/AssignRolePage';

import './App.css';

function App() {
    return (
        <div className="app-layout">
            <Router>
                <Navbar />
                <Routes>
                    {/* Core Routes */}
                    <Route path="/" element={<HomePage />} />
                    <Route path="/reserve" element={<CourtReservation />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />
                    <Route path="/refund" element={<RefundPage />} />

                    {/* Account Management */}
                    <Route path="/MyAccountPage" element={<MyAccountPage />} />
                    <Route path="/UpdateProfilePage" element={<UpdateProfilePage />} />

                    {/* Treasurer Routes */}
                    <Route path="/treasurer" element={<TreasurerDashboard />} />
                    <Route path="/treasurer/transactions" element={<FinancialTransactionsPage />} />

                    {/* Admin Routes */}
                    <Route path="/admin" element={<AdminDashboard />} />
                    <Route path="/admin/users" element={<UserManagementPage />} />
                    <Route path="/admin/assign-role" element={<AssignRolePage />} />
                </Routes>
            </Router>
        </div>
    );
}

export default App;
