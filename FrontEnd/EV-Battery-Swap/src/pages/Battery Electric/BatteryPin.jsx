import React, { useState } from 'react';
import './BatteryPin.css';

export default function BatteryPin() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedBatteryIndex, setSelectedBatteryIndex] = useState(null);

  const handleBatteryClick = (index) => {
    setSelectedBatteryIndex(index);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedBatteryIndex(null);
  };

  return (
    <div className="battery-pin-page">
      {/* Main Container */}
      <div className="gogoro-container">
        {/* Background Video */}
        <video 
          className="background-video"
          autoPlay 
          muted 
          loop 
          playsInline
        >
          <source src="/Pin.mp4" type="video/mp4" />
          Your browser does not support the video tag.
        </video>
        
        {/* Left Side - Text Content */}
        <div className="text-content">
          <h1>Always quick. Always ready.</h1>
          <p>GoStation Sites make swapping batteries a breeze. Way cleaner than gas. Infinitely faster than charging. Full batteries are ready when you are. No waiting. No fumes. No fuss.</p>
        </div>

        {/* Right Side - Battery Station */}
        <div className="station-container">
          {/* Battery Grid */}
          <div className="battery-grid">
            {[...Array(18)].map((_, index) => (
              <div 
                key={index} 
                className="battery-slot"
                onClick={() => handleBatteryClick(index)}
                style={{'--i': index}}
              >
                <div className="battery-inner"></div>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Benefits Section */}
      <div className="benefits-section">
        <div className="benefits-container">
          <h2 className="benefits-title">Lợi ích của thuê pin</h2>
          
          <div className="benefits-grid">
            <div className="benefit-card">
              <div className="benefit-icon">
                <svg viewBox="0 0 24 24" fill="currentColor">
                  <path d="M12 2C13.1 2 14 2.9 14 4C14 5.1 13.1 6 12 6C10.9 6 10 5.1 10 4C10 2.9 10.9 2 12 2ZM21 9V7L15 3.5C14.8 3.4 14.6 3.3 14.4 3.3C14.2 3.3 14 3.4 13.8 3.5L9 6.2L7 5.4V7.6L9.2 8.5L13 6.7L18 9.6V22H8V9C8 7.9 8.9 7 10 7H12C13.1 7 14 7.9 14 9V10H16V9C16 6.8 14.2 5 12 5H10C7.8 5 6 6.8 6 9V22H4V24H20V22H18V9H21Z"/>
                </svg>
              </div>
              <h3>Không tốn chi phí mua, không chịu rủi ro về pin</h3>
              <p>Với chi phí thuê pin hợp lý.</p>
            </div>

            <div className="benefit-card">
              <div className="benefit-icon">
                <svg viewBox="0 0 24 24" fill="currentColor">
                  <path d="M12.5 2C13.81 2 15.04 2.5 15.95 3.41C16.86 4.32 17.36 5.55 17.36 6.86C17.36 8.17 16.86 9.4 15.95 10.31L15.54 10.72L14.83 10.01C14.39 9.57 13.96 9.04 13.55 8.42C13.14 7.8 12.75 7.09 12.38 6.29C12.01 5.49 11.66 4.6 11.33 3.62C11.6 2.65 12.03 2 12.5 2M8.5 6C8.78 6 9 6.22 9 6.5S8.78 7 8.5 7 8 6.78 8 6.5 8.22 6 8.5 6M12 11.5C11.5 12 11 12.5 10.5 13C10 13.5 9.5 14 9 14.5L8.5 15L8 15.5L7.5 16L7 16.5C6.5 17 6 17.5 5.5 18L5 18.5L4.5 19L4 19.5L3.5 20L3 20.5L2.5 21L2 21.5L1.5 22L2.5 23L3.5 22L4.5 21L5.5 20L6.5 19L7.5 18L8.5 17L9.5 16L10.5 15L11.5 14L12.5 13L13.5 12L14.5 11L15.5 10L16.5 9L17.5 8L18.5 7L19.5 6L20.5 5L21.5 4L22.5 3L21.5 2L12 11.5Z"/>
                </svg>
              </div>
              <h3>Sạc pin tại hệ thống trạm sạc rộng khắp của VinFast</h3>
            </div>

            <div className="benefit-card">
              <div className="benefit-icon">
                <svg viewBox="0 0 24 24" fill="currentColor">
                  <path d="M9.5 3A6.5 6.5 0 0 1 16 9.5C16 11.11 15.41 12.59 14.44 13.73L14.71 14H15.5L20.5 19L19 20.5L14 15.5V14.71L13.73 14.44C12.59 15.41 11.11 16 9.5 16A6.5 6.5 0 0 1 3 9.5A6.5 6.5 0 0 1 9.5 3M9.5 5C7 5 5 7 5 9.5S7 14 9.5 14 14 12 14 9.5 12 5 9.5 5Z"/>
                </svg>
              </div>
              <h3>Nhận được sự hỗ trợ tốt nhất từ VinFast nhờ công nghệ quản lý pin tiên tiến.</h3>
            </div>
          </div>

          {/* Battery Compartment Image */}
          <div className="battery-compartment">
            <img src="/batterypin1.jpg" alt="Battery Compartment" />
          </div>
        </div>
      </div>
    </div>
  );
}