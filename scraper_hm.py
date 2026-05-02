import urllib.request
import re
import json
import os

url = "https://co.hm.com/mujer"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'})

try:
    html = urllib.request.urlopen(req).read().decode('utf-8')
    
    # Try to find product images (H&M often uses hm.com/product/ or vtex structures)
    # Generic jpg catch
    img_urls = re.findall(r'(https://[a-zA-Z0-9_.-]+/arquivos/ids/[0-9]+[^"\'\s]+\.jpg)', html)
    if not img_urls:
        img_urls = re.findall(r'(https://[^"\'\s]+\.jpg)', html)
        
    img_urls = list(set([u for u in img_urls if "hm" in u.lower() or "vtex" in u.lower() or "product" in u.lower()]))
    
    # Load existing
    catalog_path = 'full_catalog.json'
    items = []
    if os.path.exists(catalog_path):
        with open(catalog_path, 'r', encoding='utf-8') as f:
            items = json.load(f)
            
    start_id = len(items)
    new_items = []
    
    for i, img in enumerate(img_urls[:20]):  # Get 20 items
        new_items.append({
            "id": f"hm_{start_id + i}",
            "name": f"H&M Collection Item {i+1}",
            "price": 120000.0 + (i * 10000),
            "store": "H&M",
            "image": img,
            "url": "https://co.hm.com/"
        })
        
    items.extend(new_items)
    
    with open(catalog_path, 'w', encoding='utf-8') as f:
        json.dump(items, f)
        
    print(f"Added {len(new_items)} H&M products to catalog.")

    # Generate Kotlin Code and Download Images
    output_dir = 'androidApp/src/main/assets/garments'
    os.makedirs(output_dir, exist_ok=True)

    kt_code = '''package com.myapplication.common.data\n\nimport com.myapplication.common.ui.ShopItem\n\nobject CatalogData {\n    val fullCatalog = listOf(\n'''

    for item in items:
        id_val = item['id']
        name = item['name'].replace('"', '\\"')
        store = item['store']
        price = item['price']
        url = item['url']
        
        img_url = item['image']
        local_filename = f"{id_val}.jpg"
        local_path = os.path.join(output_dir, local_filename)
        
        if not os.path.exists(local_path):
            try:
                rq = urllib.request.Request(img_url, headers={'User-Agent': 'Mozilla/5.0'})
                with urllib.request.urlopen(rq) as response, open(local_path, 'wb') as out_file:
                    out_file.write(response.read())
                print(f"Downloaded {local_filename}")
            except Exception as e:
                print(f"Failed to download {img_url}: {e}")
        
        local_asset_url = f"file:///android_asset/garments/{local_filename}"
        kt_code += f'        ShopItem("{id_val}", "{name}", "{store}", {price}, "{local_asset_url}", "{url}"),\n'

    kt_code += '''    )\n}\n'''

    with open('shared/src/commonMain/kotlin/com/myapplication/common/data/CatalogData.kt', 'w', encoding='utf-8') as f:
        f.write(kt_code)

    print("Updated CatalogData.kt with H&M!")

except Exception as e:
    print(f"Failed: {e}")
