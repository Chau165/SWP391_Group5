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

      {/* Section ảnh và số liệu nổi bật */}
      <section className="statsSection">
        <h2 className="title">
          Nền tảng năng lượng tiên tiến cho xe điện hai bánh
        </h2>
        <p className="subtitle">
          Hơn 50 thành phố | Một nền tảng | Hơn 55 mẫu xe điện hỗ trợ
        </p>
        <div className="statsRow">
          <div className="statBlock">
            <div className="statNumber blue">50<sup>+</sup></div>
            <div className="statLabel">Thành phố</div>
          </div>
          <div className="statBlock">
            <div className="statNumber green">ONE</div>
            <div className="statLabel">Nền tảng</div>
          </div>
          <div className="statBlock">
            <div className="statNumber blue">55<sup>+</sup></div>
            <div className="statLabel">Mẫu xe</div>
          </div>
        </div>
        <img
          src="/img-pbgn-avengers@2x.jpg"
          alt="EV Battery Platform"
          className="statsImage"
        />
      </section>

      {/* Section pin nổi bật */}
      <section className="battery-pin-highlight">
      <h2 className="bph-title">Power Packed.</h2>
      <p className="bph-desc">
        Super simple, ultra-smart, ready to go.<br />
        This is on-demand electric fuel for<br />
        a new generation of smart vehicles.
      </p>
    </section>
    </div>
    
  );
}