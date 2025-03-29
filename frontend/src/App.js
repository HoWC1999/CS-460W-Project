import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Pages
import HomePage from './pages/HomePage';
import CourtReservation from './pages/CourtReservation';
import Navbar from './pages/Navbar';
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";

// Styles (global, if any)
import './App.css';

function App() {
  return (
    <div className="app-layout">
      <Router>
      <Navbar />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/reserve" element={<CourtReservation />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
