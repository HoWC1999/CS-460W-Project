import React, { useState } from "react";
import "../styles/RegisterPage.css";

function RegisterPage() {
    const [firstName, setFirstName] = useState("");
    const [middleName, setMiddleName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [telephone, setTelephone] = useState("");

    const handleRegistration = (e) => {
        e.preventDefault();
        // Add logic to handle registration (e.g., call an API)
        console.log("Registration Data:", {
            firstName,
            middleName,
            lastName,
            email,
            telephone,
        });
    };

    return (
        <div className="register-container">
            <h2>Register</h2>
            <form className="register-form" onSubmit={handleRegistration}>
                <label htmlFor="firstName">First Name</label>
                <input
                    id="firstName"
                    type="text"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    required
                />

                <label htmlFor="middleName">Middle Name</label>
                <input
                    id="middleName"
                    type="text"
                    value={middleName}
                    onChange={(e) => setMiddleName(e.target.value)}
                />

                <label htmlFor="lastName">Last Name</label>
                <input
                    id="lastName"
                    type="text"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    required
                />

                <label htmlFor="email">Email</label>
                <input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <label htmlFor="telephone">Telephone</label>
                <input
                    id="telephone"
                    type="tel"
                    value={telephone}
                    onChange={(e) => setTelephone(e.target.value)}
                    required
                />

                <button type="submit">Click here to complete registration</button>
            </form>
        </div>
    );
}

export default RegisterPage;
