import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

const ProtectedRoute = ({ allowedRoles, children }) => {
    const { token, user } = useContext(AuthContext);

    // If no token or no user or user role is not in allowedRoles, redirect to login or unauthorized page.
    if (!token || !user || !allowedRoles.includes(user.role)) {
        return <Navigate to="/login" replace />;
    }

    return children;
};

export default ProtectedRoute;
