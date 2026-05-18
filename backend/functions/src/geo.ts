import * as functions from "firebase-functions";

// Geo-based Store Resolution Algorithm
// This function determines the relevant stores based on the user's IP or provided GPS coordinates.

const regionalStores: Record<string, any[]> = {
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

export const getRegionalStores = functions.https.onCall(async (data, context) => {
    try {
        // 1. Detect Country Code
        let countryCode = "DEFAULT";
        
        if (data.countryCode) {
            countryCode = data.countryCode.toUpperCase();
        } else if (context.rawRequest?.headers["x-appengine-country"]) {
            // App Engine headers automatically append the country code based on IP
            countryCode = (context.rawRequest.headers["x-appengine-country"] as string).toUpperCase();
        }

        // 2. Fetch specific regional catalog or default
        const stores = regionalStores[countryCode] || regionalStores["DEFAULT"];

        return {
            status: "success",
            countryCode: countryCode,
            stores: stores,
            message: `Loaded ${stores.length} regional stores.`
        };
    } catch (error) {
        return { status: "error", error };
    }
});
