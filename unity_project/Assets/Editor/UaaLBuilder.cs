using UnityEditor;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEditor.SceneManagement;
using System.IO;

namespace DY.Editor
{
    public class UaaLBuilder
    {
        [MenuItem("DY / 1. Build and Export UaaL (Android)")]
        public static void BuildAndroidLibrary()
        {
            Debug.Log("Starting UaaL Export for Android...");

            // 1. Ensure Android Build Target
            if (EditorUserBuildSettings.activeBuildTarget != BuildTarget.Android)
            {
                Debug.LogWarning("Switching build target to Android...");
                EditorUserBuildSettings.SwitchActiveBuildTarget(BuildTargetGroup.Android, BuildTarget.Android);
            }

            // 2. Configure for UaaL (Export Project)
            EditorUserBuildSettings.exportAsGoogleAndroidProject = true;

            // 3. Define output path (temporary folder inside the unity project)
            string exportPath = Path.Combine(Application.dataPath, "../Builds/Android");
            if (!Directory.Exists(exportPath))
            {
                Directory.CreateDirectory(exportPath);
            }

            // 4. Get active scenes, if none, create and save a default one
            string[] scenes;
            if (EditorBuildSettings.scenes.Length == 0)
            {
                Debug.LogWarning("No scenes in Build Settings. Creating and adding a default scene.");
                Scene defaultScene = EditorSceneManager.NewScene(NewSceneSetup.DefaultGameObjects, NewSceneMode.Single);
                
                // Add a dummy avatar object to attach the script
                GameObject avatar = GameObject.CreatePrimitive(PrimitiveType.Capsule);
                avatar.name = "AvatarGameObject";
                avatar.AddComponent<DY.Vitrina.AvatarController>();

                string scenePath = "Assets/MainScene.unity";
                EditorSceneManager.SaveScene(defaultScene, scenePath);
                
                EditorBuildSettingsScene[] buildScenes = new EditorBuildSettingsScene[1];
                buildScenes[0] = new EditorBuildSettingsScene(scenePath, true);
                EditorBuildSettings.scenes = buildScenes;
                
                scenes = new string[] { scenePath };
            }
            else
            {
                scenes = new string[EditorBuildSettings.scenes.Length];
                for (int i = 0; i < scenes.Length; i++)
                {
                    scenes[i] = EditorBuildSettings.scenes[i].path;
                }
            }

            // 5. Build the project
            BuildPlayerOptions buildPlayerOptions = new BuildPlayerOptions();
            buildPlayerOptions.scenes = scenes;
            buildPlayerOptions.locationPathName = exportPath;
            buildPlayerOptions.target = BuildTarget.Android;
            buildPlayerOptions.options = BuildOptions.AcceptExternalModificationsToPlayer; // Used for export
            
            BuildPipeline.BuildPlayer(buildPlayerOptions);

            Debug.Log("Export finished. Copying .aar to KMP project...");
            Debug.Log("UaaL Export completed! Unity has generated the Android Studio project in: " + exportPath);
            Debug.Log("IMPORTANT: To generate the final .aar, open that exported folder in Android Studio and build it, or run 'gradlew assembleRelease' inside it.");
        }
    }
}
