import React from "react";
import { Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import CourtReservation from "./pages/CourtReservation";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import RefundPage from "./pages/RefundPage";
import MyAccountPage from "./pages/account/MyAccountPage";
import UpdateProfilePage from "./pages/account/UpdateProfilePage";
import PurchaseGuestPassPage from "./pages/account/PurchaseGuestPassPage";
import MemberBillingPage from "./pages/MemberBillingPage";
import Events from "./pages/Events";
import AboutUs from "./pages/AboutUs";
import TreasurerDashboard from "./pages/TreasurerDashboard";
import TreasurerBillingManagement from "./pages/TreasurerBillingManagement";
import AdminDashboard from "./pages/AdminDashboard";
import UserManagementPage from "./pages/UserManagementPage";
import AdminEventManagementPage from "./pages/AdminEventManagementPage";
import ProtectedRoute from "./components/ProtectedRoute";
import "./App.css";

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
        <Route path="/account/update" element={<UpdateProfilePage />} />
        <Route path="/account/guestpass" element={<PurchaseGuestPassPage />} />
        <Route path="/billing" element={<MemberBillingPage />} />
        <Route path="/events" element={<Events />} />
        <Route path="/about" element={<AboutUs />} />

        {/* Treasurer */}
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
              <TreasurerBillingManagement />
            </ProtectedRoute>
          }
        />

        {/* Admin */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute allowedRoles={["ADMIN"]}>
              <AdminDashboard />
            </ProtectedRoute>
          }
            />
      </Routes>
    </div>
  );
}

export default App;
