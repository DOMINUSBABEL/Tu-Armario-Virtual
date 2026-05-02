# Unity as a Library (UaaL) Setup

This directory is reserved for the Unity 3D project that acts as the "Vitrina Orgánica".

## Instructions for the User:
1. Open Unity Hub and create a new 3D project in this folder (`unity_project`).
2. Import your 3D Avatar (e.g., Roblox style `.glb` or `.fbx`).
3. Set up the scene with studio lighting.
4. Export the project as a library for Android:
   - Go to `File -> Build Settings`.
   - Select Android platform.
   - Check `Export Project`.
   - Click Export and select a temporary folder.
5. Copy the generated `.aar` file (usually inside the exported `unityLibrary/build/outputs/aar/`) to `androidApp/libs/` in the KMP project.
6. We will configure the KMP `build.gradle.kts` to depend on this `.aar`.
