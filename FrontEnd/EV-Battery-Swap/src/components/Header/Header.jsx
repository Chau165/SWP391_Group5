import { useEffect, useState } from "react";
import logo from "../../assets/react.svg";
import "./Header.css";
import { Link, useLocation } from 'react-router-dom';

// CHỈNH SỬA: Nhận prop onLoginClick từ App.jsx
export default function Header({ onLoginClick, user }) { 
  const [scrolled, setScrolled] = useState(false);
  const [hovered, setHovered] = useState(false);
  const [showBatteryDropdown, setShowBatteryDropdown] = useState(false);
  const location = useLocation();

  // LOGIC CUỘN CHUỘT: Thêm class 'scrolled' khi cuộn quá 20px
  useEffect(() => {
    const onScroll = () => {
      if (window.scrollY > 20) {
        setScrolled(true);
      } else {
        setScrolled(false);
      }
    };
    window.addEventListener("scroll", onScroll);
    return () => window.removeEventListener("scroll", onScroll);
  }, []);

  const isActive = (path) => location.pathname === path;
  const isBatteryActive = () => location.pathname === '/vehicles' || location.pathname === '/battery-pin';

  // Nếu là driver/staff/admin và đang ở dashboard tương ứng thì chỉ hiện Logout (và link riêng nếu cần)
  const role = user && user.role ? user.role.toLowerCase() : '';
  const isDriverDashboard = role === 'driver' && location.pathname === '/dashboard/driver';
  const isStaffDashboard = role === 'staff' && location.pathname === '/dashboard/staff';
  const isAdminDashboard = role === 'admin' && location.pathname === '/dashboard/admin';

  return (
    <header
      className={`site-header ${scrolled ? "scrolled" : hovered ? "hovered" : ""}`}
      onMouseEnter={() => setHovered(true)}
      onMouseLeave={() => setHovered(false)}
    >
      <div className="header-inner">
        <a className="brand" href="/" aria-label="home">
          <img src={logo} alt="Logo" className="brand-logo" />
          <span className="brand-title">EV Battery Swapping</span>
        </a>

  {!(isDriverDashboard || isStaffDashboard || isAdminDashboard) && (
          <nav className="main-nav" aria-label="Primary">
            <Link 
              to="/" 
              className={`nav-link ${isActive('/') ? 'active' : ''}`}
            >
              Home
            </Link>
            {/* Battery Electric với dropdown */}
            <div 
              className="nav-dropdown"
              onMouseEnter={() => setShowBatteryDropdown(true)}
              onMouseLeave={() => setShowBatteryDropdown(false)}
            >
              <span className={`nav-link dropdown-trigger ${isBatteryActive() ? 'active' : ''}`}>
                Battery Electric
                <svg className="dropdown-arrow" viewBox="0 0 20 20" fill="currentColor">
                  <path fillRule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clipRule="evenodd" />
                </svg>
              </span>
              {showBatteryDropdown && (
                <div className="dropdown-menu">
                  <Link 
                    to="/battery" 
                    className={`dropdown-item ${isActive('/battery') ? 'active' : ''}`}
                  >
                    Battery Swap
                  </Link>
                  <Link 
                    to="/battery-pin" 
                    className={`dropdown-item ${isActive('/battery-pin') ? 'active' : ''}`}
                  >
                    Battery Pin
                  </Link>
                </div>
              )}
            </div>
            <Link 
              to="/polices" 
              className={`nav-link ${isActive('/polices') ? 'active' : ''}`}
            >
              Polices
            </Link>
          </nav>
        )}

        <div className="actions">
          {(isDriverDashboard) && (
            <>
              <Link to="/vehicle" className="cta vehicle-link">Xe của tôi</Link>
              <button
                className="cta logout-btn"
                style={{ marginLeft: 12 }}
                onClick={() => {
                  localStorage.removeItem('authToken');
                  localStorage.removeItem('user');
                  window.location.href = '/';
                }}
              >
                Logout
              </button>
            </>
          )}
          {(isStaffDashboard || isAdminDashboard) && (
            <button
              className="cta logout-btn"
              onClick={() => {
                localStorage.removeItem('authToken');
                localStorage.removeItem('user');
                window.location.href = '/';
              }}
            >
              Logout
            </button>
          )}
          {!(isDriverDashboard || isStaffDashboard || isAdminDashboard) && (
            user && role === 'driver' ? (
              <>
                <Link to="/vehicle" className="cta vehicle-link">Xe của tôi</Link>
                <button
                  className="cta logout-btn"
                  style={{ marginLeft: 12 }}
                  onClick={() => {
                    localStorage.removeItem('authToken');
                    localStorage.removeItem('user');
                    window.location.href = '/';
                  }}
                >
                  Logout
                </button>
              </>
            ) : (
              user ? (
                <button
                  className="cta logout-btn"
                  onClick={() => {
                    localStorage.removeItem('authToken');
                    localStorage.removeItem('user');
                    window.location.href = '/';
                  }}
                >
                  Logout
                </button>
              ) : (
                <a 
                  href="#" 
                  className="cta login" 
                  onClick={(e) => { 
                    e.preventDefault(); 
                    onLoginClick();
                  }}
                >
                  Login
                </a>
              )
            )
          )}
        </div>
      </div>
    </header>
  );
}