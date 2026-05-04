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
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: "Error contacting Purchase Agent" });
    }
});

app.listen(port, () => {
    console.log(`Star Topology Hub ejecutándose en el puerto ${port}`);
});