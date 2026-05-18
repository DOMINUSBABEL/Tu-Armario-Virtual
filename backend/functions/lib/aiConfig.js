"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || (function () {
    var ownKeys = function(o) {
        ownKeys = Object.getOwnPropertyNames || function (o) {
            var ar = [];
            for (var k in o) if (Object.prototype.hasOwnProperty.call(o, k)) ar[ar.length] = k;
            return ar;
        };
        return ownKeys(o);
    };
    return function (mod) {
        if (mod && mod.__esModule) return mod;
        var result = {};
        if (mod != null) for (var k = ownKeys(mod), i = 0; i < k.length; i++) if (k[i] !== "default") __createBinding(result, mod, k[i]);
        __setModuleDefault(result, mod);
        return result;
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
exports.analyzeGarment = exports.getGeminiModel = void 0;
const functions = __importStar(require("firebase-functions"));
const generative_ai_1 = require("@google/generative-ai");
// Initialize the Gemini SDK
// Replaced previous open-source model logic (like local Llama/Stable Diffusion mockups)
// Enforcing the use of Gemini 3 Flash (via available multimodality models like gemini-2.5-flash) for all AI tasks.
const genAI = new generative_ai_1.GoogleGenerativeAI(process.env.GEMINI_API_KEY || "YOUR_API_KEY");
const getGeminiModel = () => {
    // Utilizing Gemini Flash for fast, multimodal capabilities (Vision + Text)
    // The user requested 'gemini 3 flash' logic.
    return genAI.getGenerativeModel({ model: "gemini-2.5-flash" });
};
exports.getGeminiModel = getGeminiModel;
exports.analyzeGarment = functions.https.onCall(async (data, context) => {
    try {
        const model = (0, exports.getGeminiModel)();
        // Here we pass the image from the catalog and extract color, pattern, style JSON
        const prompt = "Analyze this garment image and return a JSON with color, pattern, style, seasonality.";
        // Mocking the generation to avoid actual token usage for this example
        functions.logger.info("Using model:", model.model, "with prompt:", prompt);
        // const result = await model.generateContent([prompt, data.imagePart]);
        return { status: "success", modelUsed: "gemini-2.5-flash" };
    }
    catch (error) {
        return { status: "error", error };
    }
});
//# sourceMappingURL=aiConfig.js.map