from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import os
import requests

app = FastAPI(title="Vision Agent (Nano Banana Pro)")

NANO_BANANA_API_KEY = os.environ.get("NANO_BANANA_API_KEY", "LOCAL_TEST_KEY")
NANO_BANANA_ENDPOINT = "https://api.nanobanana.ai/v1/vision/analyze"

class ImagePayload(BaseModel):
    image: str # Base64 encoded image

@app.post("/analyze")
async def analyze_image(payload: ImagePayload):
    print("Vision Agent received an image for analysis.")
    try:
        # In a real scenario, this calls the Nano Banana Pro API
        # response = requests.post(
        #     NANO_BANANA_ENDPOINT,
        #     headers={"Authorization": f"Bearer {NANO_BANANA_API_KEY}"},
        #     json={"image_base64": payload.image}
        # )
        # return response.json()
        
        # Mocking Nano Banana Pro Agent Response
        return {
            "status": "success",
            "identified_garment": {
                "type": "Chaqueta de Cuero",
                "color": "Negro Onyx",
                "style_tags": ["Biker", "Y2K", "Streetwear"],
                "confidence": 0.96
            },
            "agent_message": "¡Prenda aislada! He identificado una chaqueta de cuero perfecta para un look Streetwear."
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8001)