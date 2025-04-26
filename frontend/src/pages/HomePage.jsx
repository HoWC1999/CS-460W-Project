import React from "react";
import "../styles/HomePage.css";
import QuoteCarousel from "./QuoteCarousel";

const HomePage = () => {
	return (
		<div className="homepage">
			<div className="homepage-container">

				{/* Main content: carousel + AI chat */}
				<div className="main-content">
					<div className="handle-carousel">
						<QuoteCarousel />
					</div>

					<div className="chatbox">
						<h2>Chat with Our AI Assistant</h2>
						<p>Ask any questions about our tennis club, membership, or scheduling!</p>
						<textarea
							className="chat-input"
							placeholder="Type your question here..."
						></textarea>
						<button className="chat-submit">Ask</button>
					</div>
				</div>

				{/* Become a Member section */}
				<section className="membership-section">
					<div className="membership-container">
						<h2>How to Become a Member!</h2>
						<p>
							In addition to free court time throughout the season, our members get to enjoy several
							benefits and club activities such as lessons and clinics, Tournament Play,
							Men’s and Women’s Interclub Leagues, Drop-In tennis, Member-Guest Days,
							Junior programs, our annual U.S. Open bus trip and more. Become a member today!
						</p>
						<p className="promo-note">
							Special Initiation Fee Reduction if you apply before June&nbsp;1st
							(see below for time-limited Promotional Fee)
						</p>

						<h3>2025 Dues &amp; Initiation Fees</h3>
						<table className="membership-table">
							<thead>
								<tr>
									<th>Category</th>
									<th>Annual Dues</th>
									<th>Promo Initiation</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Single Adult (30–74)</td>
									<td>$500</td>
									<td>$250</td>
								</tr>
								<tr>
									<td>Couple / Family</td>
									<td>$900</td>
									<td>$500</td>
								</tr>
								<tr>
									<td>Child of Member</td>
									<td>$50</td>
									<td>$0</td>
								</tr>
								<tr>
									<td>Senior Adult (75+)</td>
									<td>$450</td>
									<td>$200</td>
								</tr>
								<tr>
									<td>Young Adult (23–30)</td>
									<td>$300</td>
									<td>$0</td>
								</tr>
								<tr>
									<td>Junior (under 23, no parent member)</td>
									<td>$150</td>
									<td>$0</td>
								</tr>
							</tbody>
						</table>
					</div>
				</section>

			</div>
		</div>
	);
};

export default HomePage;
