// src/App.jsx

import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import LoginModal from './components/Login/LoginModal';

// Components Trang
import Home from './pages/Home/Home';
import AboutUs from './pages/About Us/AboutUs';
import Battery from './pages/Battery Electric/Battery';
import BatteryPin from './pages/Battery Electric/BatteryPin';
import Polices from './pages/Polices/polices';

function App() {
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false); 

  const handleOpenModal = () => setIsLoginModalOpen(true);
  const handleCloseModal = () => setIsLoginModalOpen(false);

  return (
    <Router>
      <Header onLoginClick={handleOpenModal} />
      
      {/* THÊM THẺ MAIN ĐỂ BAO BỌC NỘI DUNG CHÍNH */}
      <main> 
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/about" element={<AboutUs />} />
            <Route path="/battery" element={<Battery />} /> 
            <Route path="/battery-pin" element={<BatteryPin />} />
            <Route path="/polices" element={<Polices />} />
          </Routes>
      </main>

      <Footer />
      <LoginModal 
        isOpen={isLoginModalOpen} 
        onClose={handleCloseModal} 
      />
    </Router>
  )
}

export default App