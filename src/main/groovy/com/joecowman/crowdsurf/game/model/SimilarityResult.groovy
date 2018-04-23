package com.joecowman.crowdsurf.game.model

class SimilarityResult {
    Set<String> matchingKeywords = new HashSet<>()
    int numMatches //counted multiple times for multi-matches
}
