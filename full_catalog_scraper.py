import json
import requests
import re
from pathlib import Path

def fetch_vtex(url, store_name, limit=50):
    items = []
    try:
        headers = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
            "Accept": "application/json"
        }
        res = requests.get(f"{url}?_from=0&_to={limit-1}", headers=headers, timeout=15)
        res.raise_for_status()
        data = res.json()
        for prod in data:
            if len(items) >= limit: break
            prod_id = str(prod.get("productId", ""))
            name = prod.get("productName", "")
            price = 0.0
            image_url = ""
            url_link = prod.get("link", "")
            if not url_link and "linkText" in prod:
                base = url.split("/api")[0]
                url_link = f"{base}/{prod['linkText']}/p"
            
            if "items" in prod and len(prod["items"]) > 0:
                item = prod["items"][0]
                if "images" in item and len(item["images"]) > 0:
                    image_url = item["images"][0].get("imageUrl", "")
                if "sellers" in item and len(item["sellers"]) > 0:
                    comm_offer = item["sellers"][0].get("commertialOffer", {})
                    price = float(comm_offer.get("Price", 0.0))
            
            if name and price > 0:
                items.append({
                    "id": prod_id,
                    "name": name,
                    "store": store_name,
                    "price": price,
                    "image": image_url,
                    "url": url_link
                })
    except Exception as e:
        print(f"Error fetching VTEX {store_name}: {e}")
    return items

def fetch_shopify(url, store_name, limit=50):
    items = []
    try:
        headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"}
        res = requests.get(url, headers=headers, timeout=15)
        res.raise_for_status()
        data = res.json()
        products = data.get("products", [])
        for prod in products:
            if len(items) >= limit: break
            prod_id = str(prod.get("id", ""))
            name = prod.get("title", "")
            url_link = "https://trueshop.co/products/" + prod.get("handle", "")
            
            price = 0.0
            if "variants" in prod and len(prod["variants"]) > 0:
                try:
                    price = float(prod["variants"][0].get("price", 0.0))
                except:
                    pass
                    
            image_url = ""
            if "images" in prod and len(prod["images"]) > 0:
                image_url = prod["images"][0].get("src", "")
                
            if name and price > 0:
                items.append({
                    "id": prod_id,
                    "name": name,
                    "store": store_name,
                    "price": price,
                    "image": image_url,
                    "url": url_link
                })
    except Exception as e:
        print(f"Error fetching Shopify {store_name}: {e}")
    return items

def main():
    print("Scraping Velez...")
    velez_items = fetch_vtex("https://www.velez.com.co/api/catalog_system/pub/products/search", "Vélez", 50)
    
    print("Scraping Arturo Calle...")
    ac_items = fetch_vtex("https://www.arturocalle.com/api/catalog_system/pub/products/search", "Arturo Calle", 50)
    
    print("Scraping TRUE...")
    true_items = fetch_shopify("https://trueshop.co/products.json?limit=50", "TRUE", 50)
    
    all_items = velez_items + ac_items + true_items
    
    print(f"Total scraped items: {len(all_items)}")
    
    with open("full_catalog.json", "w", encoding="utf-8") as f:
        json.dump(all_items, f, ensure_ascii=False, indent=2)
    
    # Generate Kotlin file
    kt_path = Path("shared/src/commonMain/kotlin/com/myapplication/common/data/CatalogData.kt")
    kt_path.parent.mkdir(parents=True, exist_ok=True)
    
    kt_content = "package com.myapplication.common.data\n\n"
    kt_content += "import com.myapplication.common.ui.ShopItem\n\n"
    kt_content += "object CatalogData {\n"
    kt_content += "    val fullCatalog = listOf(\n"
    
    for item in all_items:
        name = item["name"].replace('"', '\\"')
        store = item["store"]
        url = item["url"].replace('"', '\\"')
        # image = item["image"].replace('"', '\\"')
        # ShopItem data class only has: id, name, store, price, affiliateLink
        # I'll just map url to affiliateLink
        kt_content += f'        ShopItem("{item["id"]}", "{name}", "{store}", {item["price"]}, "{url}"),\n'
        
    kt_content += "    )\n"
    kt_content += "}\n"
    
    kt_path.write_text(kt_content, encoding="utf-8")
    print("Kotlin data file generated successfully.")

if __name__ == "__main__":
    main()
