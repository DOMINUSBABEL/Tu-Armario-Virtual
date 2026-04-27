import React, { useState, useEffect } from 'react';

const messages = [
  "¡Te ves increíble hoy, no importa lo que elijas!",
  "La ropa no te define, tú defines tu estilo con confianza.",
  "La comodidad y tu sonrisa son tus mejores accesorios.",
  "No hay reglas, solo ponte lo que te haga sentir tú.",
  "Tu cuerpo es perfecto tal y como es. ¡A vestirlo con alegría!"
];

const Dashboard: React.FC = () => {
  const [msg, setMsg] = useState("");

  useEffect(() => {
    setMsg(messages[Math.floor(Math.random() * messages.length)]);
  }, []);

  return (
    <div>
      <div className="dashboard-header">
        <h2>Bienvenid@ a Tu Armario Virtual</h2>
        <p className="nice-message">✨ {msg}</p>
      </div>

      <div className="grid">
        <div className="card">
          <h3>Rotación Inteligente</h3>
          <p>Hemos notado que no usas tu "Chaqueta de Jean" desde hace 3 meses. ¡Es un buen día para darle vida!</p>
        </div>
        <div className="card">
          <h3>Outfit del Día Recomendado</h3>
          <p>Basado en tu última puntuación de 5 estrellas, te sugerimos algo cómodo y casual.</p>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
