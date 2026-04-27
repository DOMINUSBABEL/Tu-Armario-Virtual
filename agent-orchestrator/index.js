const express = require('express');
const cors = require('cors');

const app = express();
const port = process.env.PORT || 3001;

app.use(cors());
app.use(express.json());

// Simulating an agent orchestrator behavior
let outfitRatings = [];

// Recommender Agent
app.post('/api/recommend', (req, res) => {
    const { context, weather } = req.body;
    
    // Simulate agent logic based on context and weather, and previous ratings
    console.log(`Agent processing request for context: ${context}, weather: ${weather}`);
    
    const recommendation = {
        title: `Look ideal para ${context || 'tu día'}`,
        items: [
            "Camiseta Básica",
            weather === 'frio' ? "Chaqueta de Cuero" : "Gafas de sol",
            "Jeans o Pantalón cómodo",
            "Zapatos según ocasión"
        ],
        description: "Recomendación generada por el agente basada en tu historial y el clima actual."
    };
    
    res.json(recommendation);
});

// Feedback Agent
app.post('/api/rate', (req, res) => {
    const { outfit, rating } = req.body;
    
    console.log(`Agent received feedback: ${rating} stars for outfit ${outfit.title}`);
    outfitRatings.push({ outfit, rating, date: new Date() });
    
    // In a real app, the agent would update the user's profile and model fine-tuning here
    res.json({ success: true, message: "Feedback recibido. El agente ha ajustado tus preferencias." });
});

// Packing Agent
app.post('/api/pack', (req, res) => {
    const { destination, days } = req.body;
    
    console.log(`Agent planning packing for ${destination} for ${days} days`);
    
    res.json({
        list: [
            `${Math.ceil(days * 1.2)}x Tops/Camisetas (para rotar)`,
            `${Math.ceil(days / 2)}x Bottoms (Jeans/Pantalones)`,
            "1x Chaqueta o abrigo versátil",
            "2x Opciones de calzado",
            `Ropa interior para ${days + 1} días`
        ]
    });
});

app.listen(port, () => {
    console.log(`Orquestador de agentes ejecutándose en el puerto ${port}`);
});