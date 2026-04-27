import React, { useState } from 'react';
import { Search } from 'lucide-react';

const mockItems = [
  { id: 1, name: "Camiseta Blanca Básica", category: "Tops" },
  { id: 2, name: "Jeans Azules Clásicos", category: "Bottoms" },
  { id: 3, name: "Chaqueta de Cuero", category: "Outerwear" },
  { id: 4, name: "Vestido Floral", category: "Dresses" },
  { id: 5, name: "Zapatillas Blancas", category: "Shoes" },
];

const Closet: React.FC = () => {
  const [search, setSearch] = useState("");

  const filteredItems = mockItems.filter(item => 
    item.name.toLowerCase().includes(search.toLowerCase()) || 
    item.category.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div>
      <h2>Mi Clóset</h2>
      <p>Aquí puedes buscar y explorar todas tus prendas.</p>

      <div style={{ display: 'flex', gap: '1rem', alignItems: 'center', marginBottom: '2rem' }}>
        <Search size={20} color="var(--primary-neon)" />
        <input 
          type="text" 
          placeholder="Buscar por color, tipo, ocasión..." 
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          style={{ margin: 0 }}
        />
      </div>

      <div className="grid">
        {filteredItems.map(item => (
          <div className="card" key={item.id}>
            <div className="item-image" style={{ backgroundColor: '#4a4a6a' }}></div>
            <h3>{item.name}</h3>
            <p style={{ color: 'rgba(255,255,255,0.6)' }}>{item.category}</p>
            <button className="btn btn-secondary" style={{ width: '100%', marginTop: '1rem' }}>
              ¿Con qué lo combino?
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Closet;
