import React, { useState } from 'react';
import './BatteryPin.css';

export default function BatteryPin() {
  const [activeTab, setActiveTab] = useState('basic');

  return (
    <div className="battery-pin-page">
      {/* Hero Section - Giới thiệu sản phẩm */}
      <section className="battery-hero">
        <div className="hero-content">
          <h1>Pin Xe Máy Điện Thông Minh</h1>
          <p>Công nghệ pin lithium-ion tiên tiến với khả năng sạc nhanh, tuổi thọ cao và độ an toàn tuyệt đối. Trải nghiệm di chuyển xanh, sạch và bền vững.</p>
        </div>
      </section>

      {/* Product Introduction Section */}
      <section className="product-intro">
        <div className="container">
          <h2 className="section-title">Giới Thiệu Sản Phẩm Pin</h2>
          <div className="product-showcase">
            <div className="product-images">
              <div className="main-image">
                <img src="/batterypin3.jpg" alt="Pin xe máy điện thông minh" />
              </div>
              <div className="secondary-image">
                <img src="/batterypin.jpg" alt="Pin xe máy điện cao cấp" />
              </div>
            </div>
            <div className="product-details">
              <h3>Pin Lithium-ion Thế Hệ Mới</h3>
              <div className="specs-grid">
                <div className="spec-item">
                  <span className="spec-label">Dung lượng:</span>
                  <span className="spec-value">48V - 20Ah</span>
                </div>
                <div className="spec-item">
                  <span className="spec-label">Thời gian sạc:</span>
                  <span className="spec-value">3-4 giờ</span>
                </div>
                <div className="spec-item">
                  <span className="spec-label">Quãng đường:</span>
                  <span className="spec-value">80-120 km</span>
                </div>
                <div className="spec-item">
                  <span className="spec-label">Tuổi thọ:</span>
                  <span className="spec-value">1000+ chu kỳ</span>
                </div>
              </div>
              <div className="features-list">
                <h4>Tính Năng Nổi Bật:</h4>
                <ul>
                  <li>Công nghệ BMS (Battery Management System) thông minh</li>
                  <li>Chống nước IP67, an toàn tuyệt đối</li>
                  <li>Sạc nhanh với công nghệ Quick Charge</li>
                  <li>Thiết kế nhỏ gọn, dễ dàng tháo lắp</li>
                  <li>Hệ thống giám sát từ xa qua GPS</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Services Section */}
      <section className="services-section">
        <div className="container">
          <h2 className="section-title">Dịch Vụ & Chính Sách Thuê Pin</h2>
          
          {/* Service Plans */}
          <div className="pricing-section">
            <div className="pricing-tabs">
              <button 
                className={`tab-button ${activeTab === 'basic' ? 'active' : ''}`}
                onClick={() => setActiveTab('basic')}
              >
                Gói Cơ Bản
              </button>
              <button 
                className={`tab-button ${activeTab === 'premium' ? 'active' : ''}`}
                onClick={() => setActiveTab('premium')}
              >
                Gói Cao Cấp
              </button>
              <button 
                className={`tab-button ${activeTab === 'vip' ? 'active' : ''}`}
                onClick={() => setActiveTab('vip')}
              >
                Gói VIP
              </button>
            </div>

            <div className="pricing-content">
              {activeTab === 'basic' && (
                <div className="pricing-plan">
                  <h3>Gói Cơ Bản</h3>
                  <div className="price">1.500.000 VNĐ<span>/tháng</span></div>
                  <div className="plan-features">
                    <p>✓ Thuê pin 48V - 20Ah</p>
                    <p>✓ Bảo trì, sửa chữa miễn phí</p>
                    <p>✓ Hỗ trợ kỹ thuật 24/7</p>
                    <p>✓ Thay thế pin hỏng trong 24h</p>
                    <p>✓ Không giới hạn quãng đường</p>
                  </div>
                </div>
              )}
              
              {activeTab === 'premium' && (
                <div className="pricing-plan featured">
                  <h3>Gói Cao Cấp</h3>
                  <div className="price">2.200.000 VNĐ<span>/tháng</span></div>
                  <div className="plan-features">
                    <p>✓ Thuê pin 48V - 30Ah (dung lượng cao)</p>
                    <p>✓ Tất cả tính năng gói cơ bản</p>
                    <p>✓ Ưu tiên hỗ trợ kỹ thuật</p>
                    <p>✓ Pin dự phòng miễn phí</p>
                    <p>✓ Bảo hiểm toàn diện</p>
                    <p>✓ Ứng dụng theo dõi pin thông minh</p>
                  </div>
                </div>
              )}
              
              {activeTab === 'vip' && (
                <div className="pricing-plan">
                  <h3>Gói VIP</h3>
                  <div className="price">3.500.000 VNĐ<span>/tháng</span></div>
                  <div className="plan-features">
                    <p>✓ Thuê pin 60V - 40Ah (cao cấp nhất)</p>
                    <p>✓ Tất cả tính năng gói cao cấp</p>
                    <p>✓ Dịch vụ giao nhận pin tận nơi</p>
                    <p>✓ Ưu tiên cao nhất mọi dịch vụ</p>
                    <p>✓ Quà tặng và ưu đãi đặc biệt</p>
                    <p>✓ Hệ thống giám sát GPS cao cấp</p>
                  </div>
                </div>
              )}
            </div>
          </div>

          {/* Service Benefits */}
          <div className="service-benefits">
            <h3>Lợi Ích Dịch Vụ Thuê Pin</h3>
            <div className="benefits-grid">
              <div className="benefit-card">
                <div className="benefit-icon">�</div>
                <h4>Tiết Kiệm Chi Phí</h4>
                <p>Không cần đầu tư ban đầu cho pin, giảm 50% chi phí so với mua pin mới.</p>
              </div>
              <div className="benefit-card">
                <div className="benefit-icon">🔄</div>
                <h4>Luôn Mới</h4>
                <p>Pin được thay thế định kỳ, đảm bảo hiệu suất luôn ở mức tối ưu.</p>
              </div>
              <div className="benefit-card">
                <div className="benefit-icon">�️</div>
                <h4>Bảo Trì Toàn Diện</h4>
                <p>Dịch vụ bảo trì, sửa chữa chuyên nghiệp với đội ngũ kỹ thuật viên giàu kinh nghiệm.</p>
              </div>
              <div className="benefit-card">
                <div className="benefit-icon">📱</div>
                <h4>Theo Dõi Thông Minh</h4>
                <p>Ứng dụng di động giúp theo dõi tình trạng pin, lịch sử sử dụng và thông báo bảo trì.</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Regulations Section */}
      <section className="regulations-section">
        <div className="container">
          <h2 className="section-title">Quy Định Về Pin</h2>
          
          <div className="regulations-content">
            <div className="regulation-category">
              <h3>Quy Định An Toàn</h3>
              <ul>
                <li>Không được tự ý tháo rời, sửa chữa pin khi chưa có sự cho phép của kỹ thuật viên</li>
                <li>Tránh để pin tiếp xúc với nước, độ ẩm cao hoặc nhiệt độ quá nóng (trên 60°C)</li>
                <li>Sử dụng đúng bộ sạc chính hãng, không sạc pin quá 8 tiếng liên tục</li>
                <li>Báo ngay cho trung tâm khi phát hiện pin có hiện tượng sưng, nóng bất thường</li>
                <li>Không để pin cạn hoàn toàn (dưới 20%) trong thời gian dài</li>
              </ul>
            </div>

            <div className="regulation-category">
              <h3>Quy Định Sử Dụng</h3>
              <ul>
                <li>Khách hàng có trách nhiệm bảo quản pin cẩn thận, tránh va đập mạnh</li>
                <li>Thông báo trước 24h khi muốn tạm ngừng dịch vụ hoặc chuyển đổi gói</li>
                <li>Pin chỉ được sử dụng cho xe máy điện tương thích, không sử dụng cho mục đích khác</li>
                <li>Tuân thủ lịch bảo trì định kỳ theo khuyến nghị của kỹ thuật viên</li>
                <li>Cài đặt và sử dụng ứng dụng theo dõi pin theo hướng dẫn</li>
              </ul>
            </div>

            <div className="regulation-category">
              <h3>Quy Định Bồi Thường</h3>
              <ul>
                <li>Trường hợp pin bị hư hỏng do lỗi của khách hàng: Bồi thường 70% giá trị pin</li>
                <li>Pin bị mất cắp: Bồi thường 100% giá trị pin tại thời điểm thuê</li>
                <li>Sử dụng pin không đúng mục đích: Phạt 5.000.000 VNĐ và chấm dứt hợp đồng</li>
                <li>Chậm báo cáo sự cố: Phạt 500.000 VNĐ/ngày chậm báo cáo</li>
                <li>Vi phạm quy định an toàn: Phạt từ 1.000.000 - 3.000.000 VNĐ tùy mức độ</li>
              </ul>
            </div>

            <div className="regulation-category">
              <h3>Quy Định Bảo Hành</h3>
              <ul>
                <li>Pin được bảo hành toàn diện trong suốt thời gian thuê</li>
                <li>Thay thế pin miễn phí khi dung lượng giảm xuống dưới 80% so với ban đầu</li>
                <li>Hỗ trợ kỹ thuật 24/7 qua hotline: 1900-xxxx</li>
                <li>Thời gian phản hồi: Trong vòng 2 giờ kể từ khi nhận được yêu cầu</li>
                <li>Dịch vụ giao pin thay thế tại nhà trong vòng 24 giờ</li>
              </ul>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="cta-section">
        <div className="container">
          <div className="cta-content">
            <h2>Bắt Đầu Trải Nghiệm Ngay Hôm Nay</h2>
            <p>Đăng ký dịch vụ thuê pin và nhận ưu đãi đặc biệt cho khách hàng mới</p>
            <div className="cta-buttons">
              <button className="btn-primary">Đăng Ký Ngay</button>
              <button className="btn-secondary">Tư Vấn Miễn Phí</button>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
