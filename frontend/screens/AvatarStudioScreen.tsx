import React, { useState } from 'react';
import { View, Text, StyleSheet, Image, ScrollView, TouchableOpacity, Dimensions, SafeAreaView, Alert } from 'react-native';
import mockData from '../data/mockData.json';

const { width, height } = Dimensions.get('window');

const AvatarStudioScreen = () => {
  const [coins, setCoins] = useState(1200); // Initial mock coins
  const [avatarItem, setAvatarItem] = useState<any>(null); // State for item currently 'tried on'

  const handleTryOn = (item: any) => {
    setAvatarItem(item);
    Alert.alert("Tried On!", `${item.brandName} ${item.itemName} applied to your avatar.`);
  };

  const handleUnlockDiscount = (item: any) => {
    if (coins >= item.costInCoins) {
      setCoins(coins - item.costInCoins);
      Alert.alert("Discount Unlocked!", `You unlocked: ${item.realBenefit}`);
    } else {
      Alert.alert("Not enough coins", `You need ${item.costInCoins - coins} more DY Coins.`);
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      {/* Top Half: Avatar Visualizer */}
      <View style={styles.avatarSection}>
        <View style={styles.coinBadge}>
          <Text style={styles.coinText}>🪙 {coins} DY Coins</Text>
        </View>
        <Image 
          source={{ uri: 'https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80' }} 
          style={styles.avatarImage} 
        />
        {/* Overlay showing what the avatar is wearing, if any */}
        {avatarItem && (
          <View style={styles.wearingBadge}>
            <Text style={styles.wearingText}>Wearing: {avatarItem.brandName} {avatarItem.category}</Text>
          </View>
        )}
      </View>

      {/* Bottom Half: Brand Carousel */}
      <View style={styles.bottomSection}>
        <View style={styles.header}>
          <Text style={styles.title}>Brand Marketplace</Text>
          <Text style={styles.subtitle}>Discover exclusive offers for your avatar</Text>
        </View>
        
        <ScrollView 
          horizontal 
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={styles.carouselContainer}
        >
          {mockData.brandOffers.map((offer) => (
            <View key={offer.id} style={styles.offerCard}>
              <Image source={{ uri: offer.imageUrl }} style={styles.offerImage} />
              
              <View style={styles.offerDetails}>
                <Text style={styles.brandName}>{offer.brandName}</Text>
                <Text style={styles.itemName} numberOfLines={1}>{offer.itemName}</Text>
                <Text style={styles.benefitText}>{offer.realBenefit}</Text>
                
                <View style={styles.actionButtons}>
                  <TouchableOpacity 
                    style={styles.tryOnButton}
                    onPress={() => handleTryOn(offer)}
                  >
                    <Text style={styles.tryOnButtonText}>Try on Avatar</Text>
                  </TouchableOpacity>
                  
                  <TouchableOpacity 
                    style={styles.unlockButton}
                    onPress={() => handleUnlockDiscount(offer)}
                  >
                    <Text style={styles.unlockButtonText}>Unlock • {offer.costInCoins} 🪙</Text>
                  </TouchableOpacity>
                </View>
              </View>
            </View>
          ))}
        </ScrollView>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FAFAFA', // Clean Editorial background
  },
  avatarSection: {
    flex: 1,
    position: 'relative',
    backgroundColor: '#EAEAEA',
    borderBottomLeftRadius: 32,
    borderBottomRightRadius: 32,
    overflow: 'hidden',
  },
  coinBadge: {
    position: 'absolute',
    top: 20,
    right: 20,
    backgroundColor: '#2A1B38',
    paddingVertical: 8,
    paddingHorizontal: 16,
    borderRadius: 20,
    zIndex: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  coinText: {
    color: '#FFFFFF',
    fontFamily: 'sans-serif',
    fontWeight: 'bold',
    fontSize: 14,
  },
  avatarImage: {
    width: '100%',
    height: '100%',
    resizeMode: 'cover',
    opacity: 0.9,
  },
  wearingBadge: {
    position: 'absolute',
    bottom: 20,
    alignSelf: 'center',
    backgroundColor: 'rgba(255, 255, 255, 0.9)',
    paddingVertical: 8,
    paddingHorizontal: 16,
    borderRadius: 20,
  },
  wearingText: {
    fontFamily: 'sans-serif',
    color: '#2A1B38',
    fontWeight: '600',
  },
  bottomSection: {
    height: height * 0.45,
    paddingTop: 24,
  },
  header: {
    paddingHorizontal: 24,
    marginBottom: 16,
  },
  title: {
    fontFamily: 'serif',
    fontSize: 24,
    fontWeight: 'bold',
    color: '#2A1B38', // Dark purple
  },
  subtitle: {
    fontFamily: 'sans-serif',
    fontSize: 14,
    color: '#666',
    marginTop: 4,
  },
  carouselContainer: {
    paddingHorizontal: 16,
    paddingBottom: 24,
    gap: 16,
  },
  offerCard: {
    width: width * 0.75,
    backgroundColor: '#FFFFFF',
    borderRadius: 16,
    marginHorizontal: 8,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 8 },
    shadowOpacity: 0.08,
    shadowRadius: 16,
    elevation: 4,
    overflow: 'hidden',
  },
  offerImage: {
    width: '100%',
    height: 180,
    resizeMode: 'cover',
  },
  offerDetails: {
    padding: 16,
  },
  brandName: {
    fontFamily: 'serif',
    fontSize: 18,
    fontWeight: 'bold',
    color: '#2A1B38',
  },
  itemName: {
    fontFamily: 'sans-serif',
    fontSize: 14,
    color: '#444',
    marginVertical: 4,
  },
  benefitText: {
    fontFamily: 'sans-serif',
    fontSize: 13,
    color: '#D81B60', // Magenta Call to Action color
    fontWeight: '700',
    marginBottom: 16,
  },
  actionButtons: {
    gap: 8,
  },
  tryOnButton: {
    backgroundColor: '#FAFAFA',
    borderWidth: 1,
    borderColor: '#2A1B38',
    paddingVertical: 10,
    borderRadius: 8,
    alignItems: 'center',
  },
  tryOnButtonText: {
    color: '#2A1B38',
    fontFamily: 'sans-serif',
    fontWeight: '600',
  },
  unlockButton: {
    backgroundColor: '#D81B60', // Magenta Call to Action
    paddingVertical: 10,
    borderRadius: 8,
    alignItems: 'center',
    shadowColor: '#D81B60',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  unlockButtonText: {
    color: '#FFFFFF',
    fontFamily: 'sans-serif',
    fontWeight: 'bold',
  }
});

export default AvatarStudioScreen;