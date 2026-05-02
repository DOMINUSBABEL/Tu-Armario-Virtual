import sys
import base64
import cv2
import numpy as np
import argparse

# Try to import rembg for background removal, fallback if not installed
try:
    from rembg import remove
    REMBG_AVAILABLE = True
except ImportError:
    REMBG_AVAILABLE = False
    print("WARNING: 'rembg' library not installed. Background removal will use naive placeholder.")

def remove_background(image_np):
    """
    Uses rembg (U2Net AI model) to precisely segment the clothing item 
    from the mannequin/background.
    """
    if REMBG_AVAILABLE:
        # rembg expects PIL Image or bytes, we can pass numpy array indirectly or use bytes
        _, buffer = cv2.imencode('.png', image_np)
        result_bytes = remove(buffer.tobytes())
        result_np = cv2.imdecode(np.frombuffer(result_bytes, np.uint8), cv2.IMREAD_UNCHANGED)
        return result_np
    else:
        # Fallback: Naive thresholding (will not work well on real images)
        gray = cv2.cvtColor(image_np, cv2.COLOR_BGR2GRAY)
        _, mask = cv2.threshold(gray, 240, 255, cv2.THRESH_BINARY_INV)
        result = cv2.bitwise_and(image_np, image_np, mask=mask)
        # Add alpha channel
        b, g, r = cv2.split(result)
        rgba = [b, g, r, mask]
        return cv2.merge(rgba)

def warp_to_uv_map(image_np, target_size=(1024, 1024)):
    """
    Algorithm to deform the flat 2D clothing image to wrap around a 3D UV map.
    This simulates standard texture projection.
    """
    # 1. Resize to target texture size keeping aspect ratio
    h, w = image_np.shape[:2]
    scale = min(target_size[0]/w, target_size[1]/h)
    new_w, new_h = int(w * scale), int(h * scale)
    resized = cv2.resize(image_np, (new_w, new_h), interpolation=cv2.INTER_AREA)

    # 2. Create a blank transparent canvas (the UV map size)
    canvas = np.zeros((target_size[1], target_size[0], 4), dtype=np.uint8)
    
    # 3. Center the garment on the canvas (Assuming torso UVs are centered)
    x_offset = (target_size[0] - new_w) // 2
    y_offset = (target_size[1] - new_h) // 2
    
    canvas[y_offset:y_offset+new_h, x_offset:x_offset+new_w] = resized
    
    return canvas

def process_garment(image_path, output_mode="base64"):
    """
    Pipeline: Load Image -> Remove Background (AI) -> Warp to UV -> Output Base64 for Unity
    """
    print(f"Processing garment: {image_path}...")
    
    image = cv2.imread(image_path)
    if image is None:
        raise ValueError(f"Could not load image at {image_path}")

    # Step 1: Isolate the clothing item
    print("Segmenting background...")
    segmented_rgba = remove_background(image)

    # Step 2: Map to 3D texture dimensions
    print("Formatting for 3D UV Map...")
    texture_ready = warp_to_uv_map(segmented_rgba)

    # Step 3: Encode to Base64 (to be sent via UnityBridge in Kotlin)
    if output_mode == "base64":
        _, buffer = cv2.imencode('.png', texture_ready)
        b64_str = base64.b64encode(buffer).decode('utf-8')
        print("Success! Generated Base64 payload.")
        return b64_str
    else:
        # Save to file for debugging
        out_path = "output_texture.png"
        cv2.imwrite(out_path, texture_ready)
        print(f"Saved texture to {out_path}")
        return out_path

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="DY Asset Generation Algorithm (2D to 3D Texture)")
    parser.add_argument("--image", type=str, required=True, help="Path to the Temu/Shein/User image")
    parser.add_argument("--save", action="store_true", help="Save as PNG instead of printing Base64")
    
    args = parser.parse_args()
    
    mode = "file" if args.save else "base64"
    try:
        output = process_garment(args.image, mode)
        if mode == "base64":
            # Print a snippet so we don't flood the console
            print(f"Base64 String Snippet: {output[:100]}...{output[-20:]}")
    except Exception as e:
        print(f"Algorithm failed: {e}")
