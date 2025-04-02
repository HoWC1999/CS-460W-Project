import React, { useState, useEffect } from "react";
import "../styles/QuoteCarousel.css"; // optional if you'd like separate styling

const quotesData = [
    {
        player: "Billie Jean King",
        text: "Champions keep playing until they get it right"
    },
    {
        player: "Roger Federer",
        text: "I’m a very positive thinker, and I think that is what helps me the most in difficult moments."
    },
    {
        player: "Serena Williams",
        text: "A champion is defined not by their wins but by how they can recover when they fall."
    },
    {
        player: "Rafael Nadal",
        text: "I play each point like my life depends on it."
    },
    {
        player: "Venus Williams",
        text: "Just believe in yourself. Even if you don’t, pretend that you do and, at some point, you will."
    }
];

function QuoteCarousel() {
    const [currentIndex, setCurrentIndex] = useState(0);

    useEffect(() => {
    // Rotate quotes every 4 seconds
        const timer = setInterval(() => {
            setCurrentIndex((prevIndex) => (prevIndex + 1) % quotesData.length);
        }, 4000);

        return () => clearInterval(timer); // Cleanup on unmount
    }, []);

    const currentQuote = quotesData[currentIndex];

    return (
        <div className="quote-carousel-container">
            <h2>{currentQuote.player}</h2>
            <p>"{currentQuote.text}"</p>
        </div>
    );
}

export default QuoteCarousel;
