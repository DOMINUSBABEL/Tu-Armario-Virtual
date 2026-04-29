import React, { useState } from 'react';
import { View, Text, StyleSheet, FlatList, Image, TouchableOpacity, SafeAreaView, Dimensions } from 'react-native';
import mockData from '../data/mockData.json';

const { width } = Dimensions.get('window');
const CARD_WIDTH = (width - 48) / 2; // 2 columns with padding

const WardrobeScreen = () => {
  const [filter, setFilter] = useState('All');
  const filters = ['All', 'Tops', 'Bottoms', 'Shoes'];

  const filteredWardrobe = filter === 'All' 
    ? mockData.defaultWardrobe 
    : mockData.defaultWardrobe.filter(item => item.category === filter);

  const renderFilterItem = ({ item }) => (
    <TouchableOpacity 
      style={[styles.filterButton, filter === item && styles.filterButtonActive]}
      onPress={() => setFilter(item)}
    >
      <Text style={[styles.filterText, filter === item && styles.filterTextActive]}>{item}</Text>
    </TouchableOpacity>
  );

  const renderWardrobeItem = ({ item }) => (
    <View style={styles.card}>
      <Image source={{ uri: item.imageUrl }} style={styles.cardImage} />
      <View style={styles.cardContent}>
        <Text style={styles.itemName} numberOfLines={1}>{item.name}</Text>
        <Text style={styles.itemCategory}>{item.category}</Text>
      </View>
    </View>
  );

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>My Wardrobe</Text>
      </View>
      
      <View style={styles.filterContainer}>
        <FlatList
          data={filters}
          renderItem={renderFilterItem}
          keyExtractor={item => item}
          horizontal
          showsHorizontalScrollIndicator={false}
          contentContainerStyle={styles.filterList}
        />
      </View>

      <FlatList
        data={filteredWardrobe}
        renderItem={renderWardrobeItem}
        keyExtractor={item => item.id}
        numColumns={2}
        columnWrapperStyle={styles.grid}
        contentContainerStyle={styles.gridContainer}
        showsVerticalScrollIndicator={false}
      />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FAFAFA', // Off-white clean background
  },
  header: {
    paddingHorizontal: 24,
    paddingTop: 20,
    paddingBottom: 10,
  },
  title: {
    fontFamily: 'serif', // Fallback for Playfair Display/Merriweather
    fontSize: 28,
    fontWeight: 'bold',
    color: '#2A1B38', // Dark purple
  },
  filterContainer: {
    marginBottom: 20,
  },
  filterList: {
    paddingHorizontal: 24,
    gap: 12,
  },
  filterButton: {
    paddingVertical: 8,
    paddingHorizontal: 16,
    borderRadius: 20,
    backgroundColor: '#FFFFFF',
    borderWidth: 1,
    borderColor: '#E0E0E0',
  },
  filterButtonActive: {
    backgroundColor: '#2A1B38',
    borderColor: '#2A1B38',
  },
  filterText: {
    fontFamily: 'sans-serif', // Fallback for Inter/Roboto
    fontSize: 14,
    color: '#2A1B38',
  },
  filterTextActive: {
    color: '#FFFFFF',
    fontWeight: '600',
  },
  gridContainer: {
    paddingHorizontal: 24,
    paddingBottom: 24,
  },
  grid: {
    justifyContent: 'space-between',
    marginBottom: 16,
  },
  card: {
    width: CARD_WIDTH,
    backgroundColor: '#FFFFFF',
    borderRadius: 16,
    marginBottom: 16,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.05,
    shadowRadius: 12,
    elevation: 2,
    overflow: 'hidden',
  },
  cardImage: {
    width: '100%',
    height: CARD_WIDTH * 1.2,
    resizeMode: 'cover',
  },
  cardContent: {
    padding: 12,
  },
  itemName: {
    fontFamily: 'serif',
    fontSize: 14,
    fontWeight: '600',
    color: '#2A1B38',
    marginBottom: 4,
  },
  itemCategory: {
    fontFamily: 'sans-serif',
    fontSize: 12,
    color: '#888',
  }
});

export default WardrobeScreen;