import React, { useState } from 'react';
import './polices.css';

export default function Polices() {
  const [selectedBatteries, setSelectedBatteries] = useState('two'); // 'two' or 'one'

  return (
    <div className="polices-page">
      {/* Hero Section */}
      <section className="hero-polices">
        <div className="hero-content-polices">
          <h1 className="hero-title-animated">
            Flexible Plans for Every Journey
          </h1>
          <p className="hero-subtitle">Say goodbye to charging. Start swapping batteries!</p>
        </div>
      </section>

      {/* Main Content */}
      <section className="plans-section">
        <div className="plans-container">
          <h2 className="section-title">Battery Service Plans</h2>
          <p className="section-subtitle">Updated: October 10, 2025</p>

          {/* Battery Selector */}
          <div className="battery-selector">
            <button 
              className={`selector-btn ${selectedBatteries === 'two' ? 'active' : ''}`}
              onClick={() => setSelectedBatteries('two')}
            >
              Two Batteries
            </button>
            <button 
              className={`selector-btn ${selectedBatteries === 'one' ? 'active' : ''}`}
              onClick={() => setSelectedBatteries('one')}
            >
              Single Battery
            </button>
          </div>

          {/* Pricing Cards Grid */}
          <div className="pricing-grid">
            {/* Plan 1: $0 Monthly */}
            <div className="pricing-card featured">
              <div className="card-header">
                <div className="plan-badge">Most Flexible</div>
                <h3 className="plan-title">$0 Monthly Plan</h3>
                <div className="plan-subtitle">Pay as you go</div>
              </div>
              
              <div className="card-body">
                <div className="price-section">
                  <div className="price">$0</div>
                  <div className="price-period">/month</div>
                </div>

                <div className="plan-features">
                  <div className="feature-item">
                    <div className="feature-icon">🔋</div>
                    <div className="feature-text">
                      <strong>Flexible riding needs</strong>
                      <p>No fixed route</p>
                    </div>
                  </div>

                  <div className="feature-item">
                    <div className="feature-icon">💰</div>
                    <div className="feature-text">
                      <strong>~$1 per km*</strong>
                      <p>($1.1 per Ah)</p>
                    </div>
                  </div>

                  <div className="feature-item">
                    <div className="feature-icon">⚡</div>
                    <div className="feature-text">
                      <strong>Free Performance Upgrade</strong>
                      <p>Unlock faster acceleration</p>
                    </div>
                  </div>

                  <div className="feature-item">
                    <div className="feature-icon">🎯</div>
                    <div className="feature-text">
                      <strong>Setup Fee: ${selectedBatteries === 'two' ? '9,000' : '6,000'}</strong>
                      <p>Special promotions may apply</p>
                    </div>
                  </div>
                </div>

                <div className="contract-info">
                  <span className="contract-badge">24-month contract</span>
                </div>
              </div>

              <div className="card-footer">
                <button className="select-plan-btn">Select Plan</button>
              </div>
            </div>

            {/* Plan 2: $888 All You Can Ride */}
            <div className="pricing-card">
              <div className="card-header">
                <div className="plan-badge best-value">Best Value</div>
                <h3 className="plan-title">$888 All You Can Ride</h3>
                <div className="plan-subtitle">Long commute & weekend trips</div>
              </div>
              
              <div className="card-body">
                <div className="price-section">
                  <div className="price">$888</div>
                  <div className="price-period">/month</div>
                </div>

                <div className="plan-features">
                  <div className="feature-item">
                    <div className="feature-icon">🚗</div>
                    <div className="feature-text">
                      <strong>Daily commute ~25km+</strong>
                      <p>Perfect for long distance</p>
                    </div>
                  </div>

                  <div className="feature-item">
                    <div className="feature-icon">🕐</div>
                    <div className="feature-text">
                      <strong>Free swap in off-peak hours</strong>
                      <p>10:00-16:00, 22:00-06:00</p>
                    </div>
                  </div>

                  <div className="feature-item">
                    <div className="feature-icon">💵</div>
                    <div className="feature-text">
                      <strong>Peak hours: $2.3/Ah</strong>
                      <p>Dynamic discounts available</p>
                    </div>
                  </div>

                  <div className="feature-item">
                    <div className="feature-icon">🎁</div>
                    <div className="feature-text">
                      <strong>Up to 20% discount</strong>
                      <p>With green station swaps</p>
                    </div>
                  </div>
                </div>

                <div className="contract-info">
                  <span className="contract-badge">24-month contract</span>
                </div>
              </div>

              <div className="card-footer">
                <button className="select-plan-btn">Select Plan</button>
              </div>
            </div>

            {/* Plan 3: Flex Plan */}
            <div className="pricing-card">
              <div className="card-header">
                <div className="plan-badge">Popular</div>
                <h3 className="plan-title">Flex Saver Plan</h3>
                <div className="plan-subtitle">Short to medium commute</div>
              </div>
              
              <div className="card-body">
                <div className="price-options">
                  <div className="price-option">
                    <div className="price">$319</div>
                    <div className="included">Includes $200 credit</div>
                  </div>
                  <div className="price-option">
                    <div className="price">$519</div>
                    <div className="included">Includes $600 credit</div>
                  </div>
                  <div className="price-option">
                    <div className="price">$819</div>
                    <div className="included">Includes $1,200 credit</div>
                  </div>
                </div>

                <div className="plan-features">
                  <div className="feature-item">
                    <div className="feature-icon">📊</div>
                    <div className="feature-text">
                      <strong>Daily commute 5-30km</strong>
                      <p>Choose your usage level</p>
                    </div>
                  </div>

                  <div className="feature-item">
                    <div className="feature-icon">🔄</div>
                    <div className="feature-text">
                      <strong>Rollover unused credits</strong>
                      <p>Save for next month</p>
                    </div>
                  </div>

                  <div className="feature-item">
                    <div className="feature-icon">⚡</div>
                    <div className="feature-text">
                      <strong>Free Performance Upgrade</strong>
                      <p>Enhanced acceleration</p>
                    </div>
                  </div>

                  <div className="feature-item">
                    <div className="feature-icon">🌱</div>
                    <div className="feature-text">
                      <strong>Dynamic discounts</strong>
                      <p>Up to 20% at green stations</p>
                    </div>
                  </div>
                </div>

                <div className="contract-info">
                  <span className="contract-badge">No contract</span>
                </div>
              </div>

              <div className="card-footer">
                <button className="select-plan-btn">Select Plan</button>
              </div>
            </div>
          </div>

          {/* Additional Info Section */}
          <div className="additional-info">
            <div className="info-card">
              <h3>🚀 Performance Upgrade Add-on</h3>
              <p>Unlock maximum acceleration and power</p>
              <div className="addon-pricing">
                <span>Single Battery: <strong>$99/month</strong></span>
                <span>Two Batteries: <strong>$249/month</strong></span>
              </div>
            </div>

            <div className="info-card">
              <h3>🎒 Ride More Add-on Plans</h3>
              <p>Perfect for weekend adventures and long trips</p>
              <div className="addon-options">
                <div className="addon-option">
                  <strong>1-Day Pass</strong>
                  <p>500 Ah (~550km*)</p>
                  <span className="addon-price">$249</span>
                </div>
                <div className="addon-option">
                  <strong>2-Day Pass</strong>
                  <p>1,000 Ah (~1,100km*)</p>
                  <span className="addon-price">$449</span>
                </div>
                <div className="addon-option">
                  <strong>5-Day Pass</strong>
                  <p>2,500 Ah (~2,750km*)</p>
                  <span className="addon-price">$949</span>
                </div>
              </div>
            </div>
          </div>

          {/* Important Notes */}
          <div className="notes-section">
            <h3>📋 Important Information</h3>
            <ul className="notes-list">
              <li>* Estimated distance based on average user consumption. Actual distance may vary based on vehicle model, riding style, and conditions.</li>
              <li>Early termination fees apply for contracted plans</li>
              <li>Battery protection plan included in all plans</li>
              <li>Prices shown are for reference and subject to change</li>
              <li>Performance upgrade service automatically enabled for applicable plans</li>
              <li>Dynamic pricing available at selected stations during off-peak hours</li>
            </ul>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="cta-section">
        <div className="cta-content">
          <h2>Ready to Start Your EV Journey?</h2>
          <p>Join thousands of riders enjoying the freedom of battery swapping</p>
          <button className="cta-button">Get Started Today</button>
        </div>
      </section>
    </div>
  );
}
