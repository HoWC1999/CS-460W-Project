import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from "../context/AuthContext"; // Ensure your AuthContext is set up correctly
import '../styles/Navbar.css';

const Navbar = () => {
    const { token, user } = useContext(AuthContext);
    const userRole = user ? user.role : null; // Assume user has a "role" property

    return (
        <>
            <nav className="header">
                <div className="header-left">
                    <span role="img" aria-label="search">🔍</span>
                </div>
                <div className="header-center">
                    <Link to="/" className="header-item">Home</Link>

                    {/* Conditionally render Login or My Account based on token */}
                    {!token ? (
                        <Link to="/login" className="header-item">Login</Link>
                    ) : (
                        <Link to="/account/MyAccountPage" className="header-item">My Account</Link>
                    )}
                    <Link to="/events" className="header-item">Events</Link>
                    <Link to="/reserve" className="header-item">Reserve Court</Link>
                    {/* Optionally, show additional role-based links */}

                    {token && userRole === "TREASURER", "ADMIN" && (
                        <>
                            <Link to="/treasurer" className="header-item">Treasurer Dashboard</Link>

                    </>)}
                
                    {token && userRole === "ADMIN" && (
                        <>
                            <Link to="/admin" className="header-item">Admin Dashboard</Link>
                        </>
                    )}

                    <Link to="/about" className="header-item">About Us</Link>
                </div>
                <div className="header-right">
                    <span role="img" aria-label="settings">⚙️</span>
                    <span className="language">en-US</span>
                </div>
            </nav>

            <footer className="footer">
                <p>&copy; {new Date().getFullYear()} CS460 Tennis. All rights reserved.</p>
                <p>Contact Us: <a href="mailto:info@tennisclub.com">info@tennisclub.com</a></p>
            </footer>
        </>
    );
};

export default Navbar;
