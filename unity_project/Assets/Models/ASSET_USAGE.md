# Uso de Assets Open-Source (GLB) para el Algoritmo DY

Acabo de descargar el modelo **CesiumMan** (un modelo oficial Open-Source de *KhronosGroup* con rigging o huesos) en esta carpeta. Este es el modelo estándar de la industria para pruebas de avatares 3D.

## ¿Cómo aprender del modelo para crear tus propios Assets?

Para que nuestro aplicativo funcione de maravilla (Vitrina Orgánica) recibiendo ropa del mercado (Temu/Shein), necesitamos entender cómo el algoritmo pega la ropa sobre el avatar. 

### 1. El Mapa UV (El "Molde" de la Ropa)
Cualquier modelo 3D (como el `CesiumMan.glb` que acabo de descargar) viene con un **Mapa UV**. El Mapa UV es como desdoblar la piel o la camiseta del personaje y ponerla plana sobre un papel cuadrado (por lo general de 1024x1024 o 2048x2048 píxeles).

Si abres Unity e inspeccionas los Materiales del `CesiumMan`, verás que su textura es una sola imagen plana que tiene pintada la cara, la chaqueta y los pantalones en zonas específicas.

### 2. ¿Qué hace nuestro Algoritmo (`garment_processor.py`)?
1. Toma una foto de una prenda de internet (ej. una foto frontal de una chaqueta de Temu).
2. Usa Inteligencia Artificial (`rembg`) para recortar todo el fondo y dejar solo la chaqueta.
3. La función matemática (`warp_to_uv_map`) redimensiona la chaqueta y **la pega exactamente en las coordenadas X,Y donde el modelo 3D espera encontrar el torso**.

### 3. Prueba con el Modelo Base
1. En Unity, abre la escena y arrastra el archivo `CesiumMan.glb` desde esta carpeta (`Assets/Models`) al centro del entorno.
2. Añádele a este modelo el script `AvatarController.cs` que diseñamos.
3. Si le pasamos a este script la cadena en Base64 generada por nuestro Python desde KMP, el script tomará la textura de la ropa de Temu y la inyectará en la zona del Mapa UV.

### 4. Cómo diseñar tus propios Assets (Ropa Nativa)
Para evolucionar de solo "pintar" la piel a tener modelos de ropa reales (geometría 3D):
1. **Crear prendas modulares:** En lugar de enviar la textura a la piel del avatar, envía la textura a un modelo 3D de una "Camiseta Base Genérica" que esté usando el Avatar.
2. **Standard UVs:** Al diseñar o descargar camisetas base de internet (ej. *Quaternius* o *PolygonalMind*), asegúrate de que el pecho o pecho/espalda ocupen la mayoría del Mapa UV cuadrado. Así, cuando `garment_processor.py` centre la foto de la camisa de Temu, encajará perfectamente en el modelo 3D sin verse estirada.

¡Utiliza este modelo base en tu escena de Unity para depurar la aplicación de la ropa antes de exportar el `.aar` final!
