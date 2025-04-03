// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Core pages
import HomePage from './pages/HomePage';
import CourtReservation from './pages/CourtReservation';
import Navbar from './components/Navbar';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import RefundPage from './pages/RefundPage';

// Account Management Pages
import MyAccountPage from './pages/account/MyAccountPage';
import UpdateProfilePage from './pages/account/UpdateProfilePage';
import MemberBillingPage from './pages/MemberBillingPage';

// Treasurer Pages
import TreasurerDashboard from './pages/TreasurerDashboard';

// Admin Pages
import AdminDashboard from './pages/AdminDashboard';
import UserManagementPage from './pages/UserManagementPage';
import AssignRolePage from './pages/AssignRolePage';

// Payment Page for card payments (if needed)
import PaymentPage from './pages/PaymentPage';

import ProtectedRoute from './components/ProtectedRoute';
import './App.css';
import TreasurerBillingManagement from "./pages/TreasurerBillingManagement";

function App() {
  return (
    <div className="app-layout">

        <Navbar />
        <Routes>
          {/* Core Routes */}
          <Route path="/" element={<HomePage />} />
          <Route path="/reserve" element={<CourtReservation />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/refund" element={<RefundPage />} />

          {/* Account Management */}
          <Route path="/account/MyAccountPage" element={<MyAccountPage />} />
          <Route path="/account/UpdateProfilePage" element={<UpdateProfilePage />} />
          <Route path="/billing" element={<MemberBillingPage />} />
          <Route path="/payment/:billingId" element={<PaymentPage />} />

          {/* Treasurer Routes */}
          <Route path="/treasurer" element={
            <ProtectedRoute allowedRoles={['TREASURER', 'ADMIN']}>
              <TreasurerDashboard />
            </ProtectedRoute>
          } />

          <Route path="/treasurer/billing" element={
            <ProtectedRoute allowedRoles={['TREASURER', 'ADMIN']}>
              <TreasurerBillingManagement />
            </ProtectedRoute>
          } />

          {/* Admin Routes */}
          <Route path="/admin" element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <AdminDashboard />
            </ProtectedRoute>
          } />
          <Route path="/admin/users" element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <UserManagementPage />
            </ProtectedRoute>
          } />
          <Route path="/admin/assign-role" element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <AssignRolePage />
            </ProtectedRoute>
          } />
        </Routes>

    </div>
  );
}

export default App;
