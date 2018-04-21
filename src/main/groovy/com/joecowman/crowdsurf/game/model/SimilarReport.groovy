package com.joecowman.crowdsurf.game.model

class SimilarReport { //todo remove
    int uniqueMatches
    int totalMatches
    int wordsInputted

    int getMultiMatches() {
        return totalMatches - uniqueMatches
    }
}
