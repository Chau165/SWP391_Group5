import React, { useRef, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './LoginModal.css';


/**
 * Component LoginModal
 * @param {boolean} isOpen - Trạng thái hiển thị modal
 * @param {function} onClose - Hàm đóng modal
 */
export default function LoginModal({ isOpen, onClose }) {
  const modalRef = useRef();
  const navigate = useNavigate();

  //State để lưu trữ Email và Mật khẩu
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  //State để quản lý lỗi và trạng thái tải
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  // Hàm xử lý thay đổi input
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.id]: e.target.value });
  };

  // Logic đóng modal khi click ra ngoài (backdrop)
  useEffect(() => {
    if (!isOpen) return;

    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        onClose();
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isOpen, onClose]);

  if (!isOpen) return null;

  // BỔ SUNG 3: Hàm gọi API Đăng nhập
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setIsLoading(true);

    try {
      // Sử dụng endpoint backend ngrok
      const endpoint = 'https://a7ad0398bc12.ngrok-free.app/webAPI/api/login';

      console.log('API Endpoint:', endpoint);
      console.log('Request data:', formData);

      const res = await fetch(endpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'ngrok-skip-browser-warning': 'true',
        },
        credentials: 'include',
        body: JSON.stringify(formData),
      });

      console.log('Response status:', res.status);
      console.log('Response headers:', [...res.headers.entries()]);

      // Try to parse JSON safely
      let data;
      try {
        data = await res.json();
      } catch (jsonErr) {
        data = null;
      }
      console.log('API response data:', data);

      if (res.ok) {
        if (data && data.token) {
          localStorage.setItem('authToken', data.token);
        }
        if (data && data.user) {
          localStorage.setItem('user', JSON.stringify(data.user));
        }
        onClose();
        // Điều hướng tự động theo role
        if (data && data.user && data.user.role) {
          const role = String(data.user.role).toLowerCase();
          if (role === 'admin') {
            navigate('/dashboard/admin');
          } else if (role === 'staff') {
            navigate('/dashboard/staff');
          } else if (role === 'driver') {
            navigate('/dashboard/driver');
          } else {
            setError('Tài khoản không có quyền truy cập dashboard phù hợp!');
            navigate('/');
          }
        } else {
          setError('API không trả về user.role. Vui lòng kiểm tra lại backend!');
          navigate('/');
        }
      } else {
        // Prefer message from JSON response, fallback to status text
        const msg = data?.message || data?.error || res.statusText || 'Email hoặc mật khẩu không đúng.';
        setError(msg);
      }
    } catch (err) {
      console.error('Login error:', err);
      setError('Lỗi kết nối mạng. Vui lòng thử lại.');
    } finally {
      setIsLoading(false);
    }
  };


  return (
    <div className="modal-backdrop">
      <div className="login-modal" ref={modalRef}>
        <button className="close-btn" onClick={onClose} aria-label="Đóng">
          &times;
        </button>

        <h2 className="modal-title">Đăng nhập</h2>
        <p className="modal-subtitle">Chào mừng trở lại. Vui lòng nhập thông tin của bạn.</p>

        <form className="login-form" onSubmit={handleSubmit}>

          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              placeholder="Nhập email của bạn"
              required
              value={formData.email} // Gắn giá trị state
              onChange={handleChange} // Xử lý thay đổi
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Mật khẩu</label>
            <input
              type="password"
              id="password"
              placeholder="Nhập mật khẩu"
              required
              value={formData.password} // Gắn giá trị state
              onChange={handleChange} // Xử lý thay đổi
            />
          </div>

          {/* BỔ SUNG 4: Hiển thị lỗi */}
          {error && <p className="error-message">{error}</p>}

          <div className="form-options">
            <a href="#" className="forgot-password">Quên mật khẩu?</a>
          </div>

          <button type="submit" className="login-button" disabled={isLoading}>
            {isLoading ? 'Đang đăng nhập...' : 'Đăng nhập'}
          </button>
        </form>

        <p className="signup-link">
          Chưa có tài khoản? <a href="#">Đăng ký ngay</a>
        </p>
      </div>
    </div>
  );
}