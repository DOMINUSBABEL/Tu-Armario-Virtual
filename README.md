# Generative AI and Serverless Architectures in Cross-Platform Virtual Try-On Systems: A Case Study on DressYourself

**Abstract**
The intersection of Generative Artificial Intelligence (GenAI), serverless cloud computing, and cross-platform mobile frameworks has created novel paradigms for e-commerce and digital fashion. This paper explores the architectural evolution of *DressYourself (DY)*, previously known as *Tu-Armario-Virtual*, transitioning from a monolithic, localized Kotlin Multiplatform (KMP) architecture with Ktor, towards a highly scalable, serverless ecosystem powered by Firebase Cloud Functions, NoSQL databases, and Generative AI pipelines. Furthermore, we examine the integration of Diffusion-based Virtual Try-On (VTO) technologies, mirroring the capabilities of Google Try-On, to synthesize realistic garment adaptation across diverse body typologies and poses using cloud-based AI orchestration. 

## 1. Introduction
The digital transformation of the fashion industry demands highly interactive, personalized, and visually accurate representations of garments. Traditional 3D rendering approaches require extensive manual modeling (.glb/.gltf) and complex mapping. Recent advancements in Diffusion Models (e.g., Stable Diffusion, Google's Try-On models) have demonstrated that image-based generation can synthesize highly realistic try-on results by preserving garment details (texture, wrinkles, logos) while warping them to fit varied human poses and body shapes. This paper details the structural overhaul of DY to support these capabilities while minimizing client-side computational load and optimizing API token usage.

## 2. Architectural Metamorphosis: From Ktor to Serverless
The initial iteration of DY relied on a traditional hexagonal architecture utilizing Ktor and PostgreSQL. While robust, this approach necessitated persistent compute resources, increasing operational overhead.

### 2.1 The Firebase Transition
The migration to Firebase abstracts infrastructure management. 
- **Data Layer:** Firestore (NoSQL) replaces PostgreSQL. The schema is denormalized to optimize read operations for user wardrobes and regional store catalogs.
- **Compute Layer:** Firebase Cloud Functions (Node.js/TypeScript) replace Ktor endpoints. This enables an event-driven paradigm where functions react to Firestore triggers, Storage uploads, or Pub/Sub events (e.g., scheduled catalog scraping).
- **Authentication:** Firebase Auth unifies Google, Apple, and Email authentication, generating secure JWTs validated implicitly by Firestore Security Rules.

### 2.2 Regional Scraping and Catalog Orchestration
To function as a centralized portal, the system requires continuous ingestion of regional brand data. Cloud Functions utilize headless browsers (Puppeteer/Cheerio) via Pub/Sub CRON jobs. The extracted HTML/JSON is processed, normalizing sizes and currencies, and indexed in Firestore.

## 3. Generative AI Orchestration and Token Optimization
Deploying LLMs and Vision APIs (like Gemini) in production requires strict cost control. DY implements an *Agent Orchestrator* pattern.
1. **Vision Pipeline:** When a new catalog item is ingested, the system removes the background using Cloud Vision APIs.
2. **Semantic Tagging:** A Gemini API agent analyzes the clean image, extracting a structured JSON containing attributes (color, pattern, style, seasonality).
3. **Caching Strategy:** The resulting metadata and refined images are cached in Firestore and Firebase Storage. Subsequent requests for the same item bypass the AI layer entirely, reducing token consumption asymptotically towards zero over time.

## 4. Virtual Try-On (VTO): Integrating Diffusion Models
Inspired by Google Try-On, DY incorporates a 2D image-based VTO pipeline alongside its existing Babylon.js 3D engine. Google's methodology utilizes an image-based diffusion model trained on millions of image pairs to warp clothing onto users.

### 4.1 VTO Pipeline Architecture
- **Input Processing:** The client app captures the user's photo. Local ML Kit (Android) or Vision Framework (iOS) performs initial pose estimation and face obfuscation for privacy.
- **Cloud Execution:** The obfuscated image and the selected catalog garment image are sent to a dedicated Cloud Function.
- **Diffusion Inference:** The function interfaces with a VTO Diffusion API. The model conditions the generation on the user's pose (via DensePose or similar embeddings) and the garment's visual features, utilizing cross-attention mechanisms to preserve fabric textures.
- **Delivery and UX:** The synthesized image is returned to the client and cached using Kamel/Coil in the Compose UI. An interactive slider allows the user to compare the original and synthesized images.

### 4.2 Fallback Mechanism: Spring Physics and Babylon.js
If the diffusion API is unavailable or for rapid offline preview, DY falls back to its hybrid 3D engine. Using Kotlin and Javascript interoperability, 2D images are projected onto a base 3D avatar using dynamic UV mapping governed by Spring Physics to simulate fabric draping.

## 5. Mobile Frontend: Compose Multiplatform
The user interface is entirely constructed using Compose Multiplatform. This declarative UI framework allows sharing >85% of the codebase across Android, iOS, and Desktop. 
Features like the TikTok-style vertical social feed and interactive item cards are implemented using generic Composable functions, while platform-specific hardware interactions (like camera access for VTO) are abstracted through expect/actual paradigms.

## 6. Conclusion and Future Work
The evolution of DY represents a paradigm shift from localized monoliths to AI-driven, serverless architectures. By orchestrating Generative AI for data ingestion and Diffusion models for VTO, the platform offers a highly scalable, economically viable, and user-centric fashion ecosystem. Future iterations will focus on real-time video VTO and deeper integration with affiliate marketing APIs.

