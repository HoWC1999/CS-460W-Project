// src/App.jsx
import React from "react";
import { Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import CourtReservation from "./pages/CourtReservation";
import Navbar from "./components/Navbar";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import RefundPage from "./pages/RefundPage";
import MyAccountPage from "./pages/account/MyAccountPage";
import UpdateProfilePage from "./pages/account/UpdateProfilePage";
import MemberBillingPage from "./pages/MemberBillingPage";
import TreasurerDashboard from "./pages/TreasurerDashboard";
import TreasurerBillingManagementPage from "./pages/TreasurerBillingManagement";
import AdminDashboard from "./pages/AdminDashboard";
import UserManagementPage from "./pages/UserManagementPage";
import AssignRolePage from "./pages/AssignRolePage";
import AdminEventManagementPage from "./pages/AdminEventManagementPage";
import ProtectedRoute from "./components/ProtectedRoute";
import "./App.css";
import Events from "./pages/Events";
import PurchaseGuestPassPage from "./pages/account/PurchaseGuestPassPage";
import AuditLogs from "./pages/AuditLogs";

function App() {
  return (
    <div className="app-layout">
        <Navbar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/reserve" element={<CourtReservation />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/treasurer/refunds" element={<RefundPage />} />
          <Route path="/account/MyAccountPage" element={<MyAccountPage />} />
          <Route path="/account/UpdateProfilePage" element={<UpdateProfilePage />} />
          <Route path="/account/guestpass" element={<PurchaseGuestPassPage />} />
          <Route path="/billing" element={<MemberBillingPage />}/>
          <Route path="/Events" element={<Events />}/>
          {/* Treasurer Routes */}
          <Route
            path="/treasurer"
            element={
              <ProtectedRoute allowedRoles={["TREASURER", "ADMIN"]}>
                <TreasurerDashboard />
              </ProtectedRoute>
            }
          />
          <Route
            path="/treasurer/billing"
            element={
              <ProtectedRoute allowedRoles={["TREASURER", "ADMIN"]}>
                <TreasurerBillingManagementPage />
              </ProtectedRoute>
            }
          />
          {/* Admin Routes */}
          <Route
            path="/admin"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <AdminDashboard />
              </ProtectedRoute>
            }
          />
          <Route
            path="/admin/users"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <UserManagementPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/admin/assign-role"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <AssignRolePage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/admin/events"
            element={
              <ProtectedRoute allowedRoles={["ADMIN"]}>
                <AdminEventManagementPage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/admin/audit-logs"
            element={<AuditLogs />}
          />
        </Routes>

    </div>
  );
}

export default App;
