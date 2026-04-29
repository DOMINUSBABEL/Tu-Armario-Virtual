import random
import json

class ProceduralStylist:
    def __init__(self, wardrobe_data_path):
        self.wardrobe_data_path = wardrobe_data_path
        self.load_data()
        
    def load_data(self):
        try:
            with open(self.wardrobe_data_path, 'r') as f:
                data = json.load(f)
                self.wardrobe = data.get('defaultWardrobe', [])
                self.brand_offers = data.get('brandOffers', [])
        except Exception as e:
            print(f"Error loading data: {e}")
            self.wardrobe = []
            self.brand_offers = []

    def get_items_by_category(self, items, category):
        return [item for item in items if item['category'] == category]

    def generate_outfit(self, theme="Casual"):
        """
        Procedurally generates an outfit by picking a top, bottom, and shoes.
        Mixes user's wardrobe with 1 sponsored brand offer.
        """
        print(f"Generating outfit for theme: {theme}")
        
        user_tops = self.get_items_by_category(self.wardrobe, 'Tops')
        user_bottoms = self.get_items_by_category(self.wardrobe, 'Bottoms')
        user_shoes = self.get_items_by_category(self.wardrobe, 'Shoes')
        
        brand_tops = self.get_items_by_category(self.brand_offers, 'Tops')
        brand_bottoms = self.get_items_by_category(self.brand_offers, 'Bottoms')
        brand_shoes = self.get_items_by_category(self.brand_offers, 'Shoes')
        
        # Randomly decide which category will be a sponsored item (brand offer)
        sponsored_category = random.choice(['Tops', 'Bottoms', 'Shoes'])
        
        outfit = {
            "Top": None,
            "Bottom": None,
            "Shoes": None,
            "SponsoredItem": None
        }
        
        # Select Top
        if sponsored_category == 'Tops' and brand_tops:
            outfit['Top'] = random.choice(brand_tops)
            outfit['SponsoredItem'] = "Top"
        elif user_tops:
            outfit['Top'] = random.choice(user_tops)
            
        # Select Bottom
        if sponsored_category == 'Bottoms' and brand_bottoms:
            outfit['Bottom'] = random.choice(brand_bottoms)
            outfit['SponsoredItem'] = "Bottom"
        elif user_bottoms:
            outfit['Bottom'] = random.choice(user_bottoms)
            
        # Select Shoes
        if sponsored_category == 'Shoes' and brand_shoes:
            outfit['Shoes'] = random.choice(brand_shoes)
            outfit['SponsoredItem'] = "Shoes"
        elif user_shoes:
            outfit['Shoes'] = random.choice(user_shoes)
            
        return outfit

if __name__ == "__main__":
    # Test the procedural stylist
    # Assuming path relative to backend folder
    data_path = "../frontend/data/mockData.json"
    stylist = ProceduralStylist(data_path)
    
    suggested_outfit = stylist.generate_outfit(theme="Editorial Gala")
    
    print("\n--- Stylist Recommendation ---")
    if suggested_outfit['Top']:
        print(f"Top: {suggested_outfit['Top'].get('name', suggested_outfit['Top'].get('itemName'))} " 
              f"{'(SPONSORED by ' + suggested_outfit['Top'].get('brandName') + ')' if suggested_outfit['SponsoredItem'] == 'Top' else ''}")
              
    if suggested_outfit['Bottom']:
        print(f"Bottom: {suggested_outfit['Bottom'].get('name', suggested_outfit['Bottom'].get('itemName'))} " 
              f"{'(SPONSORED by ' + suggested_outfit['Bottom'].get('brandName') + ')' if suggested_outfit['SponsoredItem'] == 'Bottom' else ''}")
              
    if suggested_outfit['Shoes']:
        print(f"Shoes: {suggested_outfit['Shoes'].get('name', suggested_outfit['Shoes'].get('itemName'))} " 
              f"{'(SPONSORED by ' + suggested_outfit['Shoes'].get('brandName') + ')' if suggested_outfit['SponsoredItem'] == 'Shoes' else ''}")
    
    print("------------------------------")