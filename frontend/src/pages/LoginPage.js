import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../styles/LoginPage.css";

function LoginPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = (e) => {
        e.preventDefault();
    // Add logic to handle login, e.g. calling an API
        console.log("Username:", username);
        console.log("Password:", password);
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <form className="login-form" onSubmit={handleLogin}>
                <label htmlFor="username">Username</label>
                <input
                    id="username"
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Enter Username"
                required
            />

            <label htmlFor="password">Password</label>
            <input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Enter Password"
                required
            />

            <button type="submit">Login</button>
        </form>

        <p className="register-link">
            Not a member?{" "}
            <Link to="/register">Register with us now to get started!</Link>
        </p>
    </div>
);
}

export default LoginPage;
