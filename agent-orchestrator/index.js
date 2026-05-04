const express = require('express');
const cors = require('cors');
const axios = require('axios');

const app = express();
const port = process.env.PORT || 3001;

app.use(cors());
app.use(express.json());

// Agentes Microservicios (Python Nodos)
const VISION_AGENT_URL = process.env.VISION_AGENT_URL || 'http://localhost:8001';
const STORE_AGENT_URL = process.env.STORE_AGENT_URL || 'http://localhost:8002';
const PURCHASE_AGENT_URL = process.env.PURCHASE_AGENT_URL || 'http://localhost:8003';

// Gateway Routing

// 1. Visión e Identificación
app.post('/api/hub/identify', async (req, res) => {
    try {
        const response = await axios.post(`${VISION_AGENT_URL}/analyze`, req.body);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: "Error contacting Vision Agent" });
    }
});

// Endpoint: Cross-User Tap
app.post('/api/hub/cross-user-tap', async (req, res) => {
    try {
        const { targetUserId, imageBase64 } = req.body;
        console.log(`Cross-user tap detected on user ${targetUserId}`);
        const response = await axios.post(`${VISION_AGENT_URL}/analyze`, { image: imageBase64 });
        res.json({
            message: "Prenda de otra usuaria identificada",
            visionData: response.data
        });
    } catch (error) {
        res.status(500).json({ error: "Error contacting Vision Agent for cross-user tap" });
    }
});

// 2. Scraping y Tiendas
app.post('/api/hub/match', async (req, res) => {
    try {
        const response = await axios.post(`${STORE_AGENT_URL}/find-matches`, req.body);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: "Error contacting Store Agent" });
    }
});

// 3. Compras
app.post('/api/hub/purchase', async (req, res) => {
    try {
        const response = await axios.post(`${PURCHASE_AGENT_URL}/checkout`, req.body);
        
        // Commit 15: Notificaciones de Estado
        console.log("Notifying user: Tu pedido ha sido confirmado por el agente.");
        
        // Commit 18: Metrics & Reporting (Compras de afiliado)
        console.log(`[METRICS] Compra delegada registrada para el item: ${req.body.itemId}`);
        
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: "Error contacting Purchase Agent" });
    }
});

// Commit 16: Ad Engine en el Hub
app.get('/api/hub/sponsored-recommendations', (req, res) => {
    const { userStyle } = req.query;
    
    // Inyecta catálogo de marcas premium si coinciden con el estilo
    let ads = [];
    if (userStyle && userStyle.includes("Minimalist")) {
        ads.push({ brand: "Vélez", item: "Chaqueta de Cuero Premium", isSponsored: true, ctr_value: 0.15 });
    } else {
        ads.push({ brand: "Arturo Calle", item: "Blazer Casual", isSponsored: true, ctr_value: 0.10 });
    }
    
    // Commit 18: Métricas de Impresiones
    console.log(`[METRICS] Impresión de Ad Engine registrada para estilo: ${userStyle}`);
    
    res.json({ ads });
});

app.listen(port, () => {
    console.log(`Star Topology Hub ejecutándose en el puerto ${port}`);
});