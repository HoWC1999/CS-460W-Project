import React from 'react';
import {Link} from 'react-router-dom';
import '../styles/Navbar.css';

const Navbar =()=>{
    return(
        <>
            <nav className="header">
                <div className = "header-left">
                    <span role="img" aria-label="search">🔍</span>
                    <Link to="/register" className="header-item"> Become a Member</Link>
                </div>
                <div className="header-center">
                    <Link to="/" className="header-item">Home</Link>
                    <Link to="/login" className="header-item">Login</Link>
                    <Link to="/events" className="header-item">Events</Link>
                    <Link to="/reserve" className="header-item">Reserve Court</Link>
                    <Link to="/about" className="header-item">About Us</Link>
                </div>
                <div className="header-right">
                    <span role="img" aria-label="settings">⚙️</span>
                    <span className="language">en-US</span>
                </div>
            </nav>

            <footer className="footer">
                <p>&copy; {new Date().getFullYear()} CS460 Tennis. Al rights reserved. </p>
                <p> Contact Us: <a href="kurinaah@hartford.edu"> info@tennisclub.com </a> </p>
            </footer>
        </>    
    );
}

export default Navbar;