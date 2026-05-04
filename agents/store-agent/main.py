from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List
import random

app = FastAPI(title="Store & Scraper Agent")

class MatchRequest(BaseModel):
    tags: List[str]

@app.post("/find-matches")
async def find_matches(request: MatchRequest):
    print(f"Store Agent scanning for tags: {request.tags}")
    try:
        # En un escenario real, aquí se usaría BeautifulSoup o apis de Google Shopping.
        # Simulamos resultados exactos y similares
        
        simulated_results = {
            "exact_match": None,
            "similar_options": []
        }
        
        if "Chaqueta de Cuero" in request.tags or "Biker" in request.tags:
            simulated_results["exact_match"] = {
                "id": "item_123",
                "name": "Chaqueta Biker Onyx",
                "store": "Vélez",
                "price": 250.00,
                "url": "https://www.velez.co/chaqueta",
                "confidence": 0.95
            }
            simulated_results["similar_options"] = [
                {
                    "id": "item_124",
                    "name": "Chaqueta Sintética Y2K",
                    "store": "H&M",
                    "price": 89.99,
                    "url": "https://www.hm.com/chaqueta",
                    "confidence": 0.75
                },
                {
                    "id": "item_125",
                    "name": "Biker Streetwear",
                    "store": "TRUE",
                    "price": 120.00,
                    "url": "https://www.trueshop.com/biker",
                    "confidence": 0.88
                }
            ]
            
        return {
            "status": "success",
            "matches": simulated_results,
            "agent_message": "He buscado en la web y encontré una coincidencia exacta en Vélez y dos opciones más accesibles."
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8002)