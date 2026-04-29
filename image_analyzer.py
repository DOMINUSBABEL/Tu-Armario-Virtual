import cv2
import numpy as np
import json
import argparse
from sklearn.cluster import KMeans
from collections import Counter

# MOCK DYNAMIC DATABASE
wardrobe_db = [
    {"id": "item1", "category": "pants", "color": "khaki", "style": "casual", "shape": "slim"},
    {"id": "item2", "category": "shoes", "color": "white", "style": "casual", "shape": "sneakers"},
    {"id": "item3", "category": "jacket", "color": "black", "style": "formal", "shape": "blazer"},
]

def get_dominant_color(image, k=4):
    """
    Extracts the dominant color using K-Means clustering.
    """
    # Convert to RGB
    image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    
    # Reshape the image to be a list of pixels
    pixels = image.reshape((image.shape[0] * image.shape[1], 3))
    
    # Cluster the pixels
    clt = KMeans(n_clusters=k)
    clt.fit(pixels)
    
    # Count labels to find the most popular
    counts = Counter(clt.labels_)
    dominant_color = clt.cluster_centers_[counts.most_common(1)[0][0]]
    
    return [int(c) for c in dominant_color]

def rgb_to_color_name(rgb):
    """
    Mock mapping from RGB to color name.
    In a real app, use a KD-Tree with CSS3 colors.
    """
    r, g, b = rgb
    if r > 200 and g < 100 and b < 100: return "red"
    if r < 100 and g > 200 and b < 100: return "green"
    if r < 100 and g < 100 and b > 200: return "blue"
    if r > 200 and g > 200 and b > 200: return "white"
    if r < 50 and g < 50 and b < 50: return "black"
    return "khaki" # Fallback mock

def detect_garment_features(image):
    """
    Mock ML pipeline for shape, size, and style.
    A real implementation would use a CNN (e.g. ResNet50 or YOLOv8) 
    trained on DeepFashion dataset.
    """
    # Preprocessing (Edge detection as a placeholder for "shape" analysis)
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    edges = cv2.Canny(gray, 50, 150)
    contour_area = np.sum(edges > 0)
    
    size_category = "large" if contour_area > 10000 else "medium"
    
    # Mocking classification
    style = "casual"
    shape = "shirt"
    category = "top"
    
    return {
        "category": category,
        "shape": shape,
        "size": size_category,
        "style": style
    }

def generate_outfit(garment_features, dom_color_name):
    """
    Algorithmic outfit generation based on rules and dynamic DB.
    """
    outfit = []
    outfit.append(f"Scanned Item ({dom_color_name} {garment_features['shape']})")
    
    # Rule 1: Match style
    matching_style = [item for item in wardrobe_db if item['style'] == garment_features['style']]
    
    # Rule 2: Color harmony (Mock: if scanned is dark, pair with light)
    for item in matching_style:
        if item['category'] != garment_features['category']:
            outfit.append(f"{item['color'].capitalize()} {item['shape']} ({item['category']})")
            
    return outfit

def analyze_image(image_path):
    print(f"Loading image from {image_path}...")
    try:
        # Generate a dummy image if path doesn't exist for demo purposes
        image = cv2.imread(image_path)
        if image is None:
            image = np.zeros((500, 500, 3), dtype=np.uint8)
            image[:] = (200, 100, 50) # Blueish dummy image
            print("Using dummy image for analysis.")
            
        print("Extracting dominant colors...")
        dom_color = get_dominant_color(image)
        color_name = rgb_to_color_name(dom_color)
        
        print("Detecting features (Shape, Size, Style)...")
        features = detect_garment_features(image)
        features["color"] = color_name
        
        print("Generating Outfit...")
        outfit = generate_outfit(features, color_name)
        
        result = {
            "status": "success",
            "garment_detected": features,
            "suggested_outfit": outfit
        }
        
        print(json.dumps(result, indent=4))
        return result
        
    except Exception as e:
        print(f"Error analyzing image: {str(e)}")
        return {"status": "error", "message": str(e)}

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="DressYourself Image Analyzer Algorithm")
    parser.add_argument("--image", type=str, required=True, help="Path to the image to analyze")
    args = parser.parse_args()
    
    analyze_image(args.image)