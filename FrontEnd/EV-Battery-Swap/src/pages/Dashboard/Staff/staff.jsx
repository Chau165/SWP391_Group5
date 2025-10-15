
import React from 'react';
import Header from '../../../components/Header/Header';

export default function StaffDashboard({ user, onLoginClick }) {
  return (
    <>
      <Header user={user} onLoginClick={onLoginClick} />
      <div style={{padding: '60px 20px'}}>
        <h1>Staff Dashboard</h1>
        <p>Welcome, staff! This is your dashboard.</p>
      </div>
    </>
  );
}
