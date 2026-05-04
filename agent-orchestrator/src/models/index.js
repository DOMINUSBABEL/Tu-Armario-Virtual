const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema({
    username: { type: String, required: true },
    stylePreferences: [String],
    loyaltyPoints: { type: Number, default: 0 }
});

const PrendaSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    imageUrl: String,
    tags: [String],
    category: String,
    brand: String
});

const FavoritoSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    storeUrl: String,
    price: Number,
    matchConfidence: Number
});

const CompraSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    favoritoId: { type: mongoose.Schema.Types.ObjectId, ref: 'Favorito' },
    status: { type: String, enum: ['PENDING', 'PURCHASED', 'SHIPPED'], default: 'PENDING' },
    orderDate: { type: Date, default: Date.now }
});

module.exports = {
    User: mongoose.model('User', UserSchema),
    Prenda: mongoose.model('Prenda', PrendaSchema),
    Favorito: mongoose.model('Favorito', FavoritoSchema),
    Compra: mongoose.model('Compra', CompraSchema)
};