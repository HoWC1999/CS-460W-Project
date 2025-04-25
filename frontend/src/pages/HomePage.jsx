import React from "react";
import "../styles/HomePage.css";
import QuoteCarousel from "./QuoteCarousel";

const HomePage = () => {
    return (
        <div className="homepage">
            <div className="homepage-container">
            {/* Main content includes BOTH the carousel (center) and chat (right) */}
                <div className="main-content">

                {/* Carousel in the center */}
                <div className="handle-carousel">
                    <QuoteCarousel />
                </div>

                {/* AI Chatbox Section on the right */}
                <div className="chatbox">
                    <h2>Chat with Our AI Assistant</h2>
                    <p>Ask any questions about our tennis club, membership, or scheduling!</p>

                    {/* "..." region for user input – e.g. a text area or input */}
                    <textarea
                        className="chat-input"
                        placeholder="Type your question here..."
                    ></textarea>

                    {/* Submit or Send button */}
                    <button className="chat-submit">Ask</button>
                </div>
            </div>
        </div>
    </div>
    );
};

export default HomePage;
