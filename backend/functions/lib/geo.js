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
exports.getRegionalStores = void 0;
const functions = __importStar(require("firebase-functions"));
// Geo-based Store Resolution Algorithm
// This function determines the relevant stores based on the user's IP or provided GPS coordinates.
const regionalStores = {
    "CO": [
        { brand: "Vélez", category: "Cuero", website: "https://www.velez.com.co" },
        { brand: "Arturo Calle", category: "Formal", website: "https://www.arturocalle.com" },
        { brand: "Gef", category: "Casual", website: "https://www.gef.co" }
    ],
    "ES": [
        { brand: "Zara", category: "Fast Fashion", website: "https://www.zara.com/es/" },
        { brand: "Mango", category: "Casual", website: "https://shop.mango.com/es" },
        { brand: "El Corte Inglés", category: "Department Store", website: "https://www.elcorteingles.es" }
    ],
    "US": [
        { brand: "Macy's", category: "Department Store", website: "https://www.macys.com" },
        { brand: "Nike", category: "Sportswear", website: "https://www.nike.com" }
    ],
    "DEFAULT": [
        { brand: "Global Fashion", category: "General", website: "https://global.fashion" }
    ]
};
exports.getRegionalStores = functions.https.onCall(async (data, context) => {
    var _a;
    try {
        // 1. Detect Country Code
        let countryCode = "DEFAULT";
        if (data.countryCode) {
            countryCode = data.countryCode.toUpperCase();
        }
        else if ((_a = context.rawRequest) === null || _a === void 0 ? void 0 : _a.headers["x-appengine-country"]) {
            // App Engine headers automatically append the country code based on IP
            countryCode = context.rawRequest.headers["x-appengine-country"].toUpperCase();
        }
        // 2. Fetch specific regional catalog or default
        const stores = regionalStores[countryCode] || regionalStores["DEFAULT"];
        return {
            status: "success",
            countryCode: countryCode,
            stores: stores,
            message: `Loaded ${stores.length} regional stores.`
        };
    }
    catch (error) {
        return { status: "error", error };
    }
});
//# sourceMappingURL=geo.js.map