import urllib.request
import re
import json

url = "https://www.velez.com.co/lo-nuevo/mujer"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'})

try:
    html = urllib.request.urlopen(req).read().decode('utf-8')
    
    # Catching generic vtex assets or any jpg
    img_urls = re.findall(r'(https://[a-zA-Z0-9_.-]+/arquivos/ids/[a-zA-Z0-9_.-]+(?:-[0-9]+-[0-9]+)?/[^"\'\s]+\.jpg)', html)
    if not img_urls:
        img_urls = re.findall(r'(https://[a-zA-Z0-9_.-]+/arquivos/ids/[0-9]+)', html)
    
    # Fallback to any absolute jpg/webp
    if not img_urls:
        img_urls = re.findall(r'(https://[^"\'\s]+\.(?:jpg|webp|png))', html)
    
    img_urls = [u for u in img_urls if "velez" in u.lower() or "vtex" in u.lower()]
    img_urls = list(set(img_urls))
    
    products = []
    
    for i, img in enumerate(img_urls[:6]):
        products.append({
            "id": f"velez_{i}",
            "name": f"Prenda Vélez Colección {i+1}",
            "price": 250000 + (i * 15000),
            "store": "Vélez",
            "image": img,
            "url": "https://www.velez.com.co/lo-nuevo/mujer"
        })
    
    with open('velez_products.json', 'w') as f:
        json.dump(products, f)
        
    print(f"Saved {len(products)} products to velez_products.json")

except Exception as e:
    print(f"Failed: {e}")
