# DressYourself (DY) - Documentación Técnica 👗✨

Bienvenido a **DY (DressYourself)**, una aplicación vanguardista construida sobre **Kotlin Multiplatform (KMP)** que fusiona la moda digital con la inteligencia artificial y el renderizado 3D local. La arquitectura ha sido reestructurada en una serie de actualizaciones denominadas "AA Fashion App Update".

---

## 🏛️ Arquitectura del Sistema

El proyecto sigue una arquitectura modular en **KMP**, dividida entre la interfaz unificada (`shared/src/commonMain`) y las plataformas nativas (`androidMain`, `iosMain`, `desktopMain`).

### 1. Motor 3D Híbrido (Avatar On-Device)
Para permitir que los usuarios prueben su ropa real sobre avatares en 3D (estilo Roblox) sin la sobrecarga de migrar a Unity, DY utiliza un puente **KMP <-> Babylon.js**:
- **`BabylonWebView.kt`**: Componente de Compose que carga un entorno HTML local (`babylon_scene.html`).
- **Pipeline Gráfico**: Se carga un modelo base en formato `.glb`. Mediante un puente JavaScript (interoperabilidad de WebView), las texturas extraídas de la ropa real se envían como Base64 desde Kotlin y se aplican como *Albedo Textures* en las mallas correspondientes del modelo 3D (Torso, Piernas).

### 2. Procesamiento de Visión On-Device (Texturizado UV Local)
En lugar de depender de APIs de pago en la nube (Tripo3D/Meshy), utilizamos procesamiento local en el dispositivo del usuario:
- **Segmentación (`GarmentSegmenter.kt`)**: Utiliza ML Kit (Android) y Vision Framework (iOS) para recortar el sujeto de la prenda, eliminando el fondo (Background Removal).
- **Proyección a Textura**: Un algoritmo matemático deforma (warping) la imagen 2D obtenida para ajustarla a los mapas UV preestablecidos del modelo `.glb` del Avatar, creando la ilusión de que el avatar "lleva puesta" la prenda física.

### 3. Red Social y Algoritmo de Interés (Interest Graph)
El apartado social (Feed vertical) ha sido diseñado como un *Scroll Pager* nativo que emula a TikTok/Reels, pero centrado en la moda.
- **Backend Híbrido (Ktor + PostgreSQL)**: Implementado bajo Arquitectura Hexagonal (Ports & Adapters). Los casos de uso de la red social no conocen la base de datos; esto permite que, si el aplicativo escala, el repositorio pueda intercambiarse por Firebase o Supabase sin afectar el código principal.
- **Algoritmo de Interés (`InterestGraph.kt`)**: La visibilidad de las publicaciones ("Outfits") se clasifica en base a un sistema paramétrico de pesos.
  - **Fórmula de Ranking**: `Score = (WatchTime * 0.4) + (Likes * 0.3) + (Saves * 0.2) + (TagAffinity * 0.1)`
  - Esto garantiza que el feed recompense el contenido de calidad que retiene al usuario, emparejándolo con sus etiquetas de estilo preferidas (Afinidad Y2K, Minimalista, Cyberpunk).

### 4. UI/UX: Identidad "Comfortable Disruption"
La interfaz fue refactorizada a un tema Oscuro "Onyx Black" (`#0F0F13`) con paneles estilo *Glassmorphism* (cristal opaco) y acentos Neón y Plateados.
- Se ha integrado **Spring Physics** para las interacciones y animaciones de entrada, eliminando por completo los *spinners* de carga para reemplazarlos por *Shimmer Effects* corporativos.
- El isotipo geométrico unifica los conceptos del mundo material (un maniquí/gancho) y la era digital (un vértice de datos 3D).

---

## 🚀 Despliegue y Orquestación

### Frontend KMP (App Móvil)
1. Abrir en Android Studio (con el plugin Kotlin Multiplatform).
2. Asegurar que `JAVA_HOME` apunta al JDK empaquetado (jbr).
3. Compilar para Android: `./gradlew assembleDebug`

### Backend Ktor (Red Social)
El código de Ktor (cuando se externalice del prototipo actual) debe orquestarse en contenedores Docker:
1. Compilar imagen de Ktor: `./gradlew installDist` y luego construir el `Dockerfile`.
2. Orquestar junto a PostgreSQL usando `docker-compose up -d`.

## 👩‍💻 Contribuidores y Agentes
Esta versión ha sido orquestada en colaboración con Agentes de Inteligencia Artificial (Generalist, Codebase Investigator) simulando un entorno de Coworking para acelerar el desarrollo AA.
