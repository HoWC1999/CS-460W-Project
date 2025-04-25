// src/pages/AboutUs.jsx
import React from 'react';
import '../styles/AboutUs.css';

export default function AboutUs() {
	return (
		<div className="about-page">
			<div className="about-container">
				<h2>About Us</h2>

				<section className="about-section">
					<h3>Our Mission</h3>
					<p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

					Lorem ipsum dolor sit amet consectetur adipiscing elit. Quisque faucibus ex sapien vitae pellentesque sem placerat. In id cursus mi pretium tellus duis convallis. Tempus leo eu aenean sed diam urna tempor. Pulvinar vivamus fringilla lacus nec metus bibendum egestas. Iaculis massa nisl malesuada lacinia integer nunc posuere. Ut hendrerit semper vel class aptent taciti sociosqu. Ad litora torquent per conubia nostra inceptos himenaeos.
					</p>
				</section>

				<section className="about-section">
					<h3>Our History</h3>
					<p>
						Since 2025, CS460 Tennis Club has provided first-class courts and events for players of all levels. 
						Nulla facilisi. Donec convallis tellus nec ligula tincidunt, sit amet convallis magna cursus.
					</p>
				</section>

				<section className="about-section">
					<h3>Meet the Team</h3>
					<ul className="team-list">
						<li><strong>Andrew Fletcher</strong> — Backend Architect &amp; API Designer</li>
						<li><strong>Manny Kurinaah</strong> — Frontend Developer &amp; UI/UX Specialist</li>
						<li><strong>Nicolas Viera</strong> — Project Manager &amp; Documentation Lead</li>
					</ul>
				</section>

				<section className="about-section">
					<h3>Contact Us</h3>
					<p>
						Have questions? <a href="mailto:info@tennisclub.com">info@tennisclub.com</a> or call (555) 123-4567.
					</p>
				</section>
			</div>
		</div>
	);
}
