import React, { useState } from 'react';
import { Star } from 'lucide-react';

const Recommender: React.FC = () => {
  const [context, setContext] = useState("");
  const [weather, setWeather] = useState("");
  const [outfit, setOutfit] = useState<any>(null);
  const [rating, setRating] = useState(0);

  const handleRecommend = async () => {
    // In a real app, this calls the Agent Orchestrator
    setOutfit({
      title: "Look Casual Chic",
      items: ["Camiseta Blanca Básica", "Jeans Azules Clásicos", "Zapatillas Blancas"],
      description: "Un estilo cómodo y seguro, perfecto para el clima y la ocasión que mencionaste."
    });
    setRating(0);
  };

  const handleRating = async (stars: number) => {
    setRating(stars);
    // API call to Orchestrator to save rating
    // await fetch('/api/rate', { method: 'POST', body: JSON.stringify({ outfit, rating: stars }) });
  };

  return (
    <div style={{ maxWidth: '600px' }}>
      <h2>¿Qué me pongo hoy?</h2>
      <p>Cuéntanos a dónde vas y te armaremos el outfit ideal.</p>

      <div className="card">
        <label>Contexto (ej. Oficina, Rumba, Paseo)</label>
        <input 
          type="text" 
          value={context} 
          onChange={e => setContext(e.target.value)} 
          placeholder="Voy a una salida con amigas..."
        />

        <label>Clima</label>
        <select value={weather} onChange={e => setWeather(e.target.value)}>
          <option value="">Selecciona el clima...</option>
          <option value="soleado">Soleado / Calor</option>
          <option value="frio">Frío / Lluvia</option>
          <option value="templado">Templado</option>
        </select>

        <button className="btn" onClick={handleRecommend} style={{ marginTop: '1rem', width: '100%' }}>
          Generar Recomendación
        </button>
      </div>

      {outfit && (
        <div className="card" style={{ marginTop: '2rem', border: '1px solid var(--primary-neon)' }}>
          <h3 style={{ color: 'var(--primary-neon)' }}>{outfit.title}</h3>
          <p>{outfit.description}</p>
          <ul>
            {outfit.items.map((item: string, idx: number) => <li key={idx}>{item}</li>)}
          </ul>
          
          <div style={{ marginTop: '2rem' }}>
            <p><strong>¿Qué te pareció esta recomendación?</strong></p>
            <div className="rating">
              {[1, 2, 3, 4, 5].map(star => (
                <Star 
                  key={star} 
                  size={24} 
                  className={`star ${rating >= star ? 'filled' : ''}`}
                  onClick={() => handleRating(star)}
                />
              ))}
            </div>
            {rating > 0 && <p style={{ color: 'var(--positive-green)', fontSize: '0.9rem' }}>¡Gracias por calificar! Aprenderemos de tus gustos.</p>}
          </div>
        </div>
      )}
    </div>
  );
};

export default Recommender;
