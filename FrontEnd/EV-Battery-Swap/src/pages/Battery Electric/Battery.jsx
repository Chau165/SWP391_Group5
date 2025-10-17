import React, { useEffect, useRef } from "react";
import "./Battery.css";

function Battery() {
  const videoRef = useRef(null);
  const hasPlayedRef = useRef(false);

  useEffect(() => {
    const v = videoRef.current;
    if (!v) return;

    // đảm bảo video muted để browser cho autoplay
    v.muted = true;
    // playsInline cho mobile
    v.playsInline = true;

    const obs = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          // nếu lần đầu tiên nhìn thấy >=50% thì play 1 lần
          if (entry.isIntersecting && entry.intersectionRatio >= 0.5 && !hasPlayedRef.current) {
            v.play().catch(() => {});
            hasPlayedRef.current = true;
          }
          // khi không hiển thị thì pause luôn
          if (!entry.isIntersecting) {
            v.pause();
          }
        });
      },
      { threshold: [0.5] }
    );

    obs.observe(v);
    return () => obs.disconnect();
  }, []);

  // Scroll đến đúng project-card theo index
  const scrollToProjectCard = (idx) => {
    const el = document.getElementById(`project-card-${idx}`);
    if (el) {
      const rect = el.getBoundingClientRect();
      const absoluteY = window.scrollY + rect.top;
      const centerY = absoluteY - (window.innerHeight / 2) + (rect.height / 2);
      window.scrollTo({ top: centerY, behavior: 'smooth' });
    }
  };

  // Card component
  const IntroCard = ({ img, title, desc, onClick }) => (
    <div className="card" onClick={onClick} style={{ cursor: 'pointer' }}>
      <img src={img} alt={title} />
      <h3>{title}</h3>
      <p>{desc}</p>
    </div>
  );

  return (
    <div className="battery-page">
      {/* HERO SECTION */}

      <div className="hero" style={{ marginBottom: 40 }}>
        <img src="/img-battery.jpg" alt="Battery Swap" className="hero-image" />
        <div className="hero-content">
          <h1>EV Battery Solutions</h1>
        </div>
      </div>

      {/* Video minh họa quy trình swap pin */}
      <div style={{ width: '100%', display: 'flex', justifyContent: 'center', background: '#fff', margin: '40px auto 48px auto' }}>
        <video
          style={{ width: '100%', maxWidth: 1550, aspectRatio: '16/9', background: '#000', borderRadius: 0, boxShadow: 'none', display: 'block' }}
          autoPlay
          muted
          loop
          playsInline
          poster="/img-network-hero.jpg"
        >
          <source src="/video-gn-swap.mp4" type="video/mp4" />
          Your browser does not support the video tag.
        </video>
      </div>

      {/* GIỚI THIỆU VỀ GIẢI PHÁP */}
      <section className="intro-section">
        <h2>ABOUT THE SOLUTION</h2>
        <div className="intro-cards">
          <IntroCard
            img="/img-battery3.jpg"
            title="Giải pháp di động thông minh"
            desc="Hệ sinh thái pin đổi nhanh giúp mọi hành trình xe điện trở nên liền mạch, không còn lo lắng về thời gian sạc. Đổi pin chỉ trong vài giây, sẵn sàng di chuyển mọi lúc, mọi nơi."
            onClick={() => scrollToProjectCard(1)}
          />
          <IntroCard
            img="/img-battery4.jpg"
            title="Trạm lưu trữ & đổi pin tự động"
            desc="Trạm đổi pin tự động hiện đại, lưu trữ năng lượng an toàn, tối ưu hóa vận hành và tiết kiệm chi phí. Đáp ứng tiêu chuẩn quốc tế, thân thiện với môi trường."
            onClick={() => scrollToProjectCard(2)}
          />
          <IntroCard
            img="/img-battery2.jpg"
            title="Công nghệ & thiết kế vượt trội"
            desc="Pin và trạm được thiết kế tối ưu: bền bỉ, thẩm mỹ, dễ sử dụng, tích hợp công nghệ AI và IoT cho trải nghiệm thông minh, an toàn vượt trội."
            onClick={() => scrollToProjectCard(3)}
          />
        </div>
      </section>


      {/* PROJECT SECTION - BỐ CỤC ẢNH 50% - TEXT 50% */}
  <section className="project-section-single" id="project-section-single">
        <h2>THE MOST ADVANCED ENERGY PLATFORM FOR ELECTRIC VEHICLE</h2>

        <div className="project-card-gogoro">
          {/* THẺ DỰ ÁN 1: ẢNH TRÁI - TEXT PHẢI (Giống Gogoro Smart Batteries) */}
          <div className="card-item" id="project-card-1">
            <div className="card-image-wrapper">
              <img src="img-network-feature-1.jpg" alt="Smart Battery Swapping" />
            </div>
            <div className="card-text-content">
              <h4>Pin thông minh - Đổi pin siêu tốc</h4>
              <h3>Chỉ vài giây, tiếp năng lượng cho hành trình mới.</h3>
              <p>Không còn chờ đợi sạc. Đổi pin nhanh, sạch, tiện lợi. Công nghệ pin hiện đại giúp bạn luôn sẵn sàng di chuyển, tiết kiệm thời gian và bảo vệ môi trường.</p>
            </div>
          </div>

          {/* THẺ DỰ ÁN 2: ẢNH TRÁI - TEXT PHẢI */}
          <div className="card-item" id="project-card-2">
            <div className="card-image-wrapper">
              <img src="/img-network-feature-2.jpg" alt="Energy Storage Application" />
            </div>
            <div className="card-text-content">
              <h4>Trạm đổi pin tự động</h4>
              <h3>Tiết kiệm thời gian, tối ưu chi phí.</h3>
              <p>Trạm đổi pin thông minh vận hành 24/7, lưu trữ năng lượng an toàn, hỗ trợ nhiều loại xe điện. Đảm bảo nguồn pin luôn sẵn sàng, giảm thiểu chi phí bảo trì và vận hành.</p>
            </div>
          </div>

          {/* THẺ DỰ ÁN 3: ẢNH TRÁI - TEXT PHẢI */}
          <div className="card-item" id="project-card-3">
            <div className="card-image-wrapper">
              <img src="/img-network-feature-3.jpg" alt="Design and Technology" />
            </div>
            <div className="card-text-content">
              <h4>Ứng dụng quản lý & kết nối</h4>
              <h3>Kiểm soát pin, hành trình và thanh toán chỉ với một chạm.</h3>
              <p>Kết nối hệ sinh thái pin đổi nhanh, tra cứu trạm gần nhất, quản lý lịch sử sử dụng, thanh toán không tiền mặt, nhận thông báo bảo trì và hỗ trợ 24/7. Trải nghiệm di chuyển thông minh, an toàn, tiện lợi.</p>
            </div>
          </div>
        </div>
      </section>

      {/* TẠI SAO CHỌN GIẢI PHÁP CỦA CHÚNG TÔI */}
      <section className="intro-section">
        <h2>WHY CHOOSE OUR SOLUTION?</h2>
        <div className="intro-cards-sumary">
          <div className="card-sumary">
            <img src="/img-batte.jpg" alt="Pin cho xe điện" />
          </div>

          <div className="sumary-text-chung">
            <div className="sumary-text-1">
              <h3 className="text">CONVENIENCE</h3>
              <p>
                GoStation Sites use less space than a parking spot and are quick and easy to install in a wide range of sites. A single location can serve hundreds of swaps a day without waiting.
              </p>
            </div>

            <div className="sumary-text-1">
              <h3 className="text">Easy to Operate</h3>
              <p>
                Weather proof. Tamper proof. Low maintenance. 24hr monitoring, remote updates and automatic safety measures deliver 99% uptime.
              </p>
            </div>

            <div className="sumary-text-1">
              <h3 className="text">Built to Last</h3>
              <p>
                Rugged design, future-proof technology and continual updates. Engineered for reliability in the most demanding urban environments. Swap after swap, year after year.
              </p>
            </div>
          </div>
        </div>
      </section>

      
      {/* VIDEO SECTION: play/pause on scroll */}
      <section className="video-section video-hero">
        <video
          ref={videoRef}
          src="/video-gn-think-ahead.mp4"
          poster="/img-network-hero.jpg"
          // muted và playsInline đã set trong useEffect
          // không để controls để giống hero
          className="video-cover"
        />
        <div className="video-overlay">
          <div className="eyebrow">SMART &amp; CONNECTED</div>
          <h2 className="video-title">Thinking ahead.</h2>
          <p className="video-desc">
            SmartGEN is our cloud‑connected nerve center. Its AI is constantly learning from riding patterns and swap behavior to anticipate when and where full batteries will be needed. By intelligently distributing energy we keep hundreds of thousands of riders moving swiftly on their way.
          </p>
        </div>
      </section>


    </div>
  );
}

export default Battery;