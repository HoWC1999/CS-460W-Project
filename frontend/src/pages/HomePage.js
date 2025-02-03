import React from "react";
import "../styles/HomePage.css";

const HomePage = () => {
    return (
        <div className="container">
            {/* Navigation Bar */}
            <nav classname="navbar">
                <div className="nav-left">
                    <span className = "search-icon">🔍</span>
                    <a href="#" className="nav-item"> Become a member</a>
                </div>

                <div className="nav-center">
                    <a href="#" className="nav-item"> Home </a>
                    <a href="#" className="nav-item"> Login </a>
                    <a href="#" className="nav-item"> Events </a>
                    <a href="#" className="nav-item"> Reserve Court </a>
                    <a href="#" className="nav-item"> About Us </a>
                </div>

                <div className="nav-right">
                    <span className="Settings">⚙️</span>
                    <span className="language-selector">en-US</span>
                </div>
            </nav>
        </div>
    );
}

export default HomePage;