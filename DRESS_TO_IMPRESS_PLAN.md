# 👗✨ Plan de Mejoras: "Dress to Impress" para DY (DressYourself)

## 1. Visión y Bucle Principal (Core Loop)
Transformar DY de un simple sugeridor de ropa a una **plataforma gamificada de expresión personal**. El objetivo es que cada usuario se sienta como un auténtico "Fashionista" o "Top Model" utilizando **sus propias prendas reales**, compitiendo en pasarelas temáticas, recibiendo votos y escalando rangos, tal como en la experiencia de Roblox *Dress to Impress*, pero aplicado a la vida real mediante IA.

**El Bucle (Core Loop):**
1. **Subir/Digitalizar:** El usuario sube fotos de sus prendas reales al "Armario Virtual".
2. **Desafío (Runway):** Se presenta un tema global diario o de tiempo limitado (ej. *Y2K*, *Cyberpunk*, *Gala de Negocios*).
3. **Creación (con IA):** El usuario usa la IA (Ollama) para generar el mejor outfit con su ropa que se ajuste al tema.
4. **Pasarela y Votación (Showcase):** El usuario publica su outfit. La comunidad (o la IA en modo local/un solo jugador) califica el atuendo de 1 a 5 estrellas.
5. **Recompensas:** Se ganan *Style Points (SP)* y *DY Coins (Moneda Virtual)*.
6. **Progresión:** Se sube de rango (Newbie -> Top Model) y se desbloquean efectos visuales, fondos y poses para la UI.

---

## 2. Mecánicas de Gamificación (Game Mechanics)

### A. Sistema de Votación (1-5 Estrellas)
- Implementar una UI de estrellas flotantes.
- En la "Pasarela", los usuarios ven outfits generados por otros (o generados proceduralmente) y otorgan estrellas.
- Obtener 5 estrellas desencadena animaciones de celebración (confeti, destellos de neón).

### B. Progresión de Rangos (Rank Progression)
Actualizar el sistema de niveles actual a rangos icónicos:
- 🥉 **New Model** (0 - 499 SP)
- 🥈 **Trendsetter** (500 - 1499 SP)
- 🥇 **Runway Queen / King** (1500 - 2999 SP)
- 💎 **Fashion Icon** (3000 - 4999 SP)
- 👑 **Top Model** (5000+ SP)
*Cada rango desbloquea paletas de colores exclusivas para la App (ej. Dorado, Diamante, Holográfico).*

### C. Pasarelas Temáticas (Themed Runways)
- Pantalla dedicada ("Runway") con un temporizador en cuenta regresiva (ej. "Tiempo restante: 02:45").
- Temas rotativos que obligan a la IA a priorizar ciertas paletas de colores o estilos al sugerir outfits.

### D. Moneda Virtual (DY Coins) y Tienda
- Ganar monedas por entrar diariamente (Streak) y por recibir buenas votaciones.
- Usar monedas para comprar "Fondos de Pasarela", "Marcos de Avatar", o "Efectos de Partículas" para cuando el usuario muestre su perfil en la Leaderboard.

---

## 3. Estética UI/UX (Neon Cyber Glam & Animations)

### A. Dirección de Arte
- **Colores:** Fondos oscuros profundos (`#1A1A2E`), acentos vibrantes en Rosa Neón (`#xFFFF1493`), Púrpura Profundo (`#FF4B0082`) y toques de Cian/Dorado para elementos premium.
- **Tipografía:** Fuentes en negrita (ExtraBold) para títulos, con sombras caídas (Drop Shadows) para dar efecto de "Glow" o neón.

### B. Animaciones de Alta Gama (60fps)
- Transiciones fluidas entre pestañas (`fadeIn` / `fadeOut` con `slideInVertically`).
- Animación de "Subida de Nivel" a pantalla completa que interrumpe la UI con un destello brillante y un sonido de celebración (haptic feedback).
- El botón de "Obtener Sugerencia de Outfit" debe tener un pulso (pulsing effect) cuando está cargando.

### C. Modo Escaparate (Runway Showcase Mode)
- Cuando el usuario finaliza su outfit y lo "envía" a la pasarela, la pantalla debe cambiar a un "Modo Showcase".
- La foto del outfit se muestra en grande, centrada, con un fondo estilizado (comprado en la tienda), partículas cayendo (estrellas o destellos de Compose) y música/efectos de sonido imaginarios.

---

## 4. Personalización e Inteligencia Artificial
- **El Estilista Personal:** La IA no solo sugiere, sino que "critica" de forma divertida y profesional. Si el atuendo no coincide con el tema de la pasarela, la IA puede restar puntos estilísticos (SP) o dar un consejo mordaz pero amistoso.
- **Memoria del Armario:** Guardar los metadatos de las prendas en SQLite (vía SqlDelight) para que el usuario no tenga que subir la misma foto dos veces. El usuario selecciona prendas de su "Inventario" y la IA genera la combinación.

---

## 5. Plan de Ejecución (Fases de Implementación)

### Fase 1: Core de Base de Datos y Armario Real (Semana 1)
- Implementar **SqlDelight** para guardar las prendas digitalizadas (ID, Categoría, Color, Ruta de Imagen local).
- Rediseñar `WardrobeScreen` para que no sea un placeholder, sino que muestre las prendas reales guardadas en la BD en una grilla.

### Fase 2: Pasarela y IA Temática (Semana 2)
- Modificar la `MainScreen` y `RunwayScreen`.
- Crear el sistema de **Temas Diarios**.
- Modificar el `OllamaClient` para que reciba el "Tema de la Pasarela" como parámetro estricto para la generación del outfit.

### Fase 3: Votación, Monedas y Showcase (Semana 3)
- Crear el componente `VotingDialog` (1-5 Estrellas).
- Implementar la Lógica de **DY Coins** en `GameState`.
- Crear el `RunwayShowcaseScreen` con animaciones de partículas (Canvas de Compose) y fondos de neón.

### Fase 4: Pulido de UI/UX y Rangos (Semana 4)
- Actualizar `StyleStatsBar` para reflejar los rangos de *Dress to Impress*.
- Añadir sombras de neón, haptic feedback (vibración) y refinar las animaciones de KMP.
- Pruebas en Android y preparación para iOS.