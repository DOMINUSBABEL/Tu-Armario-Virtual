import React from 'react';
import { View, Text, StyleSheet, FlatList, Image, SafeAreaView, TouchableOpacity } from 'react-native';

const MOCK_POSTS = [
  {
    id: 'p1',
    user: 'Sofia V.',
    handle: '@sofia_styles',
    userAvatar: 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?ixlib=rb-1.2.1&auto=format&fit=crop&w=150&q=80',
    image: 'https://images.unsplash.com/photo-1496747611176-843222e1e57c?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
    caption: 'Cyberpunk meets vintage! Loving this combination of Zara trench coat with classic Adidas Sambas. #Y2K #OOTD',
    likes: 342,
    time: '2h ago'
  },
  {
    id: 'p2',
    user: 'Alex T.',
    handle: '@alextrends',
    userAvatar: 'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&auto=format&fit=crop&w=150&q=80',
    image: 'https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80',
    caption: 'Ready for the Business Gala event on DressYourself! Rate my fit ⭐',
    likes: 891,
    time: '5h ago'
  }
];

const BlogFeedScreen = () => {

  const renderPost = ({ item }) => (
    <View style={styles.postContainer}>
      {/* Header */}
      <View style={styles.postHeader}>
        <Image source={{ uri: item.userAvatar }} style={styles.avatar} />
        <View>
          <Text style={styles.userName}>{item.user}</Text>
          <Text style={styles.userHandle}>{item.handle}</Text>
        </View>
      </View>

      {/* Image */}
      <Image source={{ uri: item.image }} style={styles.postImage} />

      {/* Footer / Actions */}
      <View style={styles.postFooter}>
        <View style={styles.actionRow}>
          <TouchableOpacity style={styles.actionButton}>
            <Text style={styles.actionIcon}>🤍</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.actionButton}>
            <Text style={styles.actionIcon}>💬</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.actionButton}>
            <Text style={styles.actionIcon}>↗️</Text>
          </TouchableOpacity>
        </View>
        <Text style={styles.likesText}>{item.likes} likes</Text>
        <Text style={styles.captionText}>
          <Text style={styles.captionUser}>{item.user} </Text>
          {item.caption}
        </Text>
        <Text style={styles.timeText}>{item.time}</Text>
      </View>
    </View>
  );

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Runway Feed</Text>
      </View>
      <FlatList
        data={MOCK_POSTS}
        renderItem={renderPost}
        keyExtractor={item => item.id}
        showsVerticalScrollIndicator={false}
      />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FAFAFA', // Clean Editorial background
  },
  header: {
    paddingHorizontal: 24,
    paddingTop: 20,
    paddingBottom: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#EAEAEA',
  },
  title: {
    fontFamily: 'serif',
    fontSize: 28,
    fontWeight: 'bold',
    color: '#2A1B38',
  },
  postContainer: {
    marginBottom: 24,
    backgroundColor: '#FFFFFF',
  },
  postHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 12,
  },
  avatar: {
    width: 40,
    height: 40,
    borderRadius: 20,
    marginRight: 12,
  },
  userName: {
    fontFamily: 'sans-serif',
    fontWeight: 'bold',
    fontSize: 14,
    color: '#2A1B38',
  },
  userHandle: {
    fontFamily: 'sans-serif',
    fontSize: 12,
    color: '#888',
  },
  postImage: {
    width: '100%',
    height: 400,
    resizeMode: 'cover',
  },
  postFooter: {
    padding: 16,
  },
  actionRow: {
    flexDirection: 'row',
    marginBottom: 10,
    gap: 16,
  },
  actionButton: {},
  actionIcon: {
    fontSize: 24,
  },
  likesText: {
    fontFamily: 'sans-serif',
    fontWeight: 'bold',
    fontSize: 14,
    color: '#2A1B38',
    marginBottom: 6,
  },
  captionText: {
    fontFamily: 'sans-serif',
    fontSize: 14,
    color: '#444',
    lineHeight: 20,
  },
  captionUser: {
    fontWeight: 'bold',
    color: '#2A1B38',
  },
  timeText: {
    fontFamily: 'sans-serif',
    fontSize: 12,
    color: '#888',
    marginTop: 6,
  }
});

export default BlogFeedScreen;