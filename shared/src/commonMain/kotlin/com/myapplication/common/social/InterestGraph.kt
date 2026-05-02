package com.myapplication.common.social

import kotlin.math.max

/**
 * InterestGraph handles the algorithmic logic for sorting and ranking the social feed.
 * It uses a weighted formula: (WatchTime * 0.4) + (Likes * 0.3) + (Saves * 0.2) + (TagAffinity * 0.1)
 */
object InterestGraph {

    data class PostMetrics(
        val postId: String,
        val watchTimeSeconds: Int,
        val likes: Int,
        val saves: Int,
        val tags: List<String>
    )

    data class UserProfile(
        val userId: String,
        val tagAffinities: Map<String, Double> // Tag -> Affinity (0.0 to 1.0)
    )

    /**
     * Calculates the rank score for a given post based on user affinities and post metrics.
     */
    fun calculateScore(post: PostMetrics, user: UserProfile): Double {
        // Normalize watch time (assuming 60 seconds is a "full" watch for a short video/post)
        val normalizedWatchTime = (post.watchTimeSeconds / 60.0).coerceIn(0.0, 1.0)
        
        // Normalize likes and saves (example: log scale to prevent viral posts from dominating completely)
        val normalizedLikes = if (post.likes > 0) Math.log10(post.likes.toDouble()) / 5.0 else 0.0
        val normalizedSaves = if (post.saves > 0) Math.log10(post.saves.toDouble()) / 4.0 else 0.0

        // Calculate tag affinity score
        var totalAffinity = 0.0
        for (tag in post.tags) {
            totalAffinity += user.tagAffinities[tag] ?: 0.0
        }
        val avgAffinity = if (post.tags.isNotEmpty()) totalAffinity / post.tags.size else 0.0

        return (normalizedWatchTime * 0.4) + 
               (normalizedLikes.coerceIn(0.0, 1.0) * 0.3) + 
               (normalizedSaves.coerceIn(0.0, 1.0) * 0.2) + 
               (avgAffinity * 0.1)
    }

    /**
     * Ranks a list of posts for a specific user.
     */
    fun rankFeed(posts: List<PostMetrics>, user: UserProfile): List<PostMetrics> {
        return posts.sortedByDescending { calculateScore(it, user) }
    }
}
