using System;
using UnityEngine;

namespace DY.Vitrina
{
    /// <summary>
    /// This script should be attached to the Avatar GameObject in Unity.
    /// It receives Base64 texture data from Kotlin Multiplatform (KMP) and applies it to the Avatar's meshes.
    /// </summary>
    public class AvatarController : MonoBehaviour
    {
        [Header("Target Renderers (Drag and Drop in Inspector)")]
        [Tooltip("The MeshRenderer or SkinnedMeshRenderer for the Top/Torso")]
        public Renderer topRenderer;
        
        [Tooltip("The MeshRenderer or SkinnedMeshRenderer for the Bottoms/Legs")]
        public Renderer bottomRenderer;

        [Header("Material Indexes")]
        public int topMaterialIndex = 0;
        public int bottomMaterialIndex = 0;

        /// <summary>
        /// Called from KMP using UnitySendMessage("AvatarGameObject", "ApplyTopTexture", "base64_string")
        /// </summary>
        public void ApplyTopTexture(string base64String)
        {
            ApplyTextureToRenderer(topRenderer, topMaterialIndex, base64String);
        }

        /// <summary>
        /// Called from KMP using UnitySendMessage("AvatarGameObject", "ApplyBottomTexture", "base64_string")
        /// </summary>
        public void ApplyBottomTexture(string base64String)
        {
            ApplyTextureToRenderer(bottomRenderer, bottomMaterialIndex, base64String);
        }

        /// <summary>
        /// Helper to convert Base64 to Texture2D and apply it to the main Albedo map.
        /// </summary>
        private void ApplyTextureToRenderer(Renderer targetRenderer, int materialIndex, string base64)
        {
            if (targetRenderer == null)
            {
                Debug.LogError("AvatarController: Target Renderer is missing!");
                return;
            }

            try
            {
                // Decode Base64 string
                byte[] imageBytes = Convert.FromBase64String(base64);

                // Create a temporary texture (dimensions will be overwritten by LoadImage)
                Texture2D tex = new Texture2D(2, 2, TextureFormat.RGBA32, false);
                tex.LoadImage(imageBytes);
                tex.Apply(); // Apply changes

                // Get the specific material instance to avoid modifying shared materials
                Material[] materials = targetRenderer.materials;
                if (materialIndex >= 0 && materialIndex < materials.Length)
                {
                    Material mat = materials[materialIndex];
                    
                    // Assign texture to the standard Universal Render Pipeline (URP) or Standard shader property
                    mat.SetTexture("_BaseMap", tex); 
                    mat.SetTexture("_MainTex", tex); // Fallback for legacy standard shader
                    
                    Debug.Log($"Successfully applied texture to {targetRenderer.name}");
                }
                else
                {
                    Debug.LogError("AvatarController: Invalid material index.");
                }
            }
            catch (Exception e)
            {
                Debug.LogError($"AvatarController: Failed to parse and apply texture. Error: {e.Message}");
            }
        }
    }
}
