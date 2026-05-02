import json

with open('full_catalog.json', 'r', encoding='utf-8') as f:
    items = json.load(f)

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
    image = item['image']
    url = item['url']
    kt_code += f'        ShopItem("{id_val}", "{name}", "{store}", {price}, "{image}", "{url}"),\n'

kt_code += '''    )
}
'''

with open('shared/src/commonMain/kotlin/com/myapplication/common/data/CatalogData.kt', 'w', encoding='utf-8') as f:
    f.write(kt_code)

print("Fixed CatalogData.kt with images!")
