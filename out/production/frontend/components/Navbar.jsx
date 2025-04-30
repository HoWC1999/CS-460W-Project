import React, { useContext }       from 'react';
import { NavLink, Link }           from 'react-router-dom';
import { AuthContext }             from "../context/AuthContext";
import logo                         from '../images/logo.jpg';
import '../styles/Navbar.css';

const Navbar = () => {
	const { token, user } = useContext(AuthContext);
	const userRole        = user?.role;

	return (
		<>
			<nav className="header">
				{/* Logo on the left */}
				<div className="header-left">
					<Link to="/">
						<img
							src={logo}
							alt="Tennis Club Logo"
							className="header-logo"
						/>
					</Link>
				</div>

				{/* Nav links in center */}
				<div className="header-center">
					<NavLink
						to="/"
						end
						className={({ isActive }) =>
							isActive ? 'header-item active' : 'header-item'
						}
					>
						HOME
					</NavLink>

					{!token ? (
						<NavLink
							to="/login"
							className={({ isActive }) =>
								isActive ? 'header-item active' : 'header-item'
							}
						>
							LOGIN
						</NavLink>
					) : (
						<NavLink
							to="/account/MyAccountPage"
							className={({ isActive }) =>
								isActive ? 'header-item active' : 'header-item'
							}
						>
							My Account
						</NavLink>
					)}

					<NavLink
						to="/events"
						className={({ isActive }) =>
							isActive ? 'header-item active' : 'header-item'
						}
					>
						EVENTS
					</NavLink>

					<NavLink
						to="/reserve"
						className={({ isActive }) =>
							isActive ? 'header-item active' : 'header-item'
						}
					>
						RESERVE COURT
					</NavLink>

					{token && (userRole === "TREASURER" || userRole === "ADMIN") && (
						<NavLink
							to="/treasurer"
							className={({ isActive }) =>
								isActive ? 'header-item active' : 'header-item'
							}
						>
							Treasurer Dashboard
						</NavLink>
					)}

					{token && userRole === "ADMIN" && (
						<NavLink
							to="/admin"
							className={({ isActive }) =>
								isActive ? 'header-item active' : 'header-item'
							}
						>
							Admin Dashboard
						</NavLink>
					)}

					<NavLink
						to="/about"
						className={({ isActive }) =>
							isActive ? 'header-item active' : 'header-item'
						}
					>
						ABOUT US
					</NavLink>
				</div>
			</nav>

			<footer className="footer">
				<p>&copy; {new Date().getFullYear()} CS460 Tennis. All rights reserved.</p>
				<p>
					Contact Us:{' '}
					<a href="mailto:info@tennisclub.com">info@tennisclub.com</a>
				</p>
			</footer>
		</>
	);
};

export default Navbar;
