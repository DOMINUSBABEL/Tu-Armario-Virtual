# 🎮 Guía de Instalación y Exportación de Unity 3D (UaaL)

Dado que la instalación de Unity y la exportación de librerías `.aar` requiere autenticación visual (Unity ID / Licencia Personal) y el uso de su interfaz gráfica, como asistente IA CLI no puedo automatizar este paso de forma desatendida. 

Por favor, sigue estas instrucciones paso a paso para completar la integración:

## Paso 1: Instalar Unity Hub
1. Descarga Unity Hub desde el sitio oficial: [**Descargar Unity Hub para Windows**](https://unity.com/download)
2. Instala el ejecutable `UnityHubSetup.exe` y ábrelo.
3. Inicia sesión con tu **Unity ID** (o crea uno gratuito).
4. Acepta la licencia personal gratuita.

## Paso 2: Instalar el Editor de Unity
1. Ve a la pestaña **Installs** en Unity Hub.
2. Haz clic en **Install Editor**.
3. Selecciona la versión **LTS más reciente** (ej. `2022.3.x` o `2023.x`).
4. ⚠️ **MUY IMPORTANTE:** En los módulos a añadir, asegúrate de marcar **Android Build Support**, expandirlo y marcar también **OpenJDK** y **Android SDK & NDK Tools**.

## Paso 3: Configurar el Proyecto "Vitrina Orgánica"
1. En Unity Hub, ve a **Projects** -> **New project**.
2. Selecciona la plantilla **3D Core** (o URP si deseas mejores gráficos).
3. Nombra el proyecto `unity_project` y guárdalo dentro de la carpeta: `C:\Users\jegom\Tu-Armario-Virtual\`
4. Una vez abra Unity, arrastra a la escena tu Avatar en formato `.glb` o `.fbx`.
5. Ajusta luces y cámara para enfocar el Avatar.

## Paso 4: Exportar como Librería de Android (UaaL)
1. En Unity, ve a `File` -> `Build Settings`.
2. En *Platform*, selecciona **Android** y haz clic en **Switch Platform**.
3. Marca la casilla **Export Project**.
4. Haz clic en **Export** y selecciona una carpeta temporal en tu escritorio (ej. `C:\Users\jegom\Desktop\UnityExport`).
5. Cuando termine, ve a esa carpeta temporal y busca dentro de `unityLibrary\build\outputs\aar\` el archivo `.aar`.
6. Copia ese archivo `.aar` y pégalo en el proyecto KMP en la ruta: `C:\Users\jegom\Tu-Armario-Virtual\androidApp\libs\` (crea la carpeta `libs` si no existe).

---
*Una vez hayas completado estos pasos y tengas el archivo `.aar` en la carpeta `libs`, avísame para configurar KMP (`build.gradle.kts`) y conectar finalmente el motor 3D con Compose para nuestra versión final 0.3.*
