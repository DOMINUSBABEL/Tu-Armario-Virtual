import { useState } from 'react';
import './App.css';
import { Home, Shirt, Sparkles, Plane } from 'lucide-react';
import Dashboard from './components/Dashboard';
import Closet from './components/Closet';
import Recommender from './components/Recommender';
import Packer from './components/Packer';

function App() {
  const [activeTab, setActiveTab] = useState('dashboard');

  const renderContent = () => {
    switch (activeTab) {
      case 'dashboard': return <Dashboard />;
      case 'closet': return <Closet />;
      case 'recommender': return <Recommender />;
      case 'packer': return <Packer />;
      default: return <Dashboard />;
    }
  };

  return (
    <div className="app-container">
      <nav className="sidebar">
        <h1>Tu Armario Virtual</h1>
        
        <button 
          className={`nav-item ${activeTab === 'dashboard' ? 'active' : ''}`}
          onClick={() => setActiveTab('dashboard')}
        >
          <Home size={20} /> Inicio
        </button>
        
        <button 
          className={`nav-item ${activeTab === 'closet' ? 'active' : ''}`}
          onClick={() => setActiveTab('closet')}
        >
          <Shirt size={20} /> Mi clóset
        </button>

        <button 
          className={`nav-item ${activeTab === 'recommender' ? 'active' : ''}`}
          onClick={() => setActiveTab('recommender')}
        >
          <Sparkles size={20} /> ¿Qué me pongo?
        </button>

        <button 
          className={`nav-item ${activeTab === 'packer' ? 'active' : ''}`}
          onClick={() => setActiveTab('packer')}
        >
          <Plane size={20} /> Empacar
        </button>
      </nav>

      <main className="main-content">
        {renderContent()}
      </main>
    </div>
  );
}

export default App;
