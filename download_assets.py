import json
import urllib.request
import os

with open('full_catalog.json', 'r', encoding='utf-8') as f:
    items = json.load(f)

output_dir = 'androidApp/src/main/assets/garments'

kt_code = '''package com.myapplication.common.data

import com.myapplication.common.ui.ShopItem

object CatalogData {
    val fullCatalog = listOf(
'''

for item in items:
    id_val = item['id']
    name = item['name'].replace('"', '\\"')
    store = item['store']
    price = item['price']
    url = item['url']
    
    # Download the image
    img_url = item['image']
    local_filename = f"{id_val}.jpg"
    local_path = os.path.join(output_dir, local_filename)
    
    if not os.path.exists(local_path):
        try:
            req = urllib.request.Request(img_url, headers={'User-Agent': 'Mozilla/5.0'})
            with urllib.request.urlopen(req) as response, open(local_path, 'wb') as out_file:
                out_file.write(response.read())
            print(f"Downloaded {local_filename}")
        except Exception as e:
            print(f"Failed to download {img_url}: {e}")
    
    # Update to local asset path
    local_asset_url = f"file:///android_asset/garments/{local_filename}"
    
    kt_code += f'        ShopItem("{id_val}", "{name}", "{store}", {price}, "{local_asset_url}", "{url}"),\n'

kt_code += '''    )
}
'''

with open('shared/src/commonMain/kotlin/com/myapplication/common/data/CatalogData.kt', 'w', encoding='utf-8') as f:
    f.write(kt_code)

print("Downloaded assets and updated CatalogData.kt with local paths!")
