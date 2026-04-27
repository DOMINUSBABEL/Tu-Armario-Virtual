import React, { useState } from 'react';

const Packer: React.FC = () => {
  const [destination, setDestination] = useState('');
  const [days, setDays] = useState(3);
  const [list, setList] = useState<string[]>([]);

  const handleGenerate = () => {
    // Call agent orchestrator
    setList([
      "3x Camisetas",
      "1x Jeans",
      "1x Pantalón corto",
      "1x Chaqueta ligera",
      "2x Zapatos cómodos",
      "Ropa interior para 4 días"
    ]);
  };

  return (
    <div style={{ maxWidth: '600px' }}>
      <h2>Empacar para Vacaciones</h2>
      <p>Dinos a dónde vas y te ayudaremos a llevar exactamente lo que necesitas, maximizando combinaciones.</p>

      <div className="card">
        <label>Destino o Clima</label>
        <input 
          type="text" 
          value={destination} 
          onChange={e => setDestination(e.target.value)} 
          placeholder="Ej. Playa en Cartagena"
        />

        <label>Número de días</label>
        <input 
          type="number" 
          min="1" 
          value={days} 
          onChange={e => setDays(Number(e.target.value))} 
        />

        <button className="btn" onClick={handleGenerate} style={{ marginTop: '1rem', width: '100%' }}>
          Generar Lista de Empaque
        </button>
      </div>

      {list.length > 0 && (
        <div className="card">
          <h3>Tu Maleta Optimizada</h3>
          <p style={{ fontSize: '0.9rem', color: 'rgba(255,255,255,0.7)' }}>
            Las prendas se pueden combinar entre sí para que no lleves peso extra.
          </p>
          <ul style={{ lineHeight: '1.8' }}>
            {list.map((item, idx) => (
              <li key={idx}>
                <input type="checkbox" style={{ width: 'auto', marginRight: '0.5rem', display: 'inline-block' }} /> 
                {item}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default Packer;
