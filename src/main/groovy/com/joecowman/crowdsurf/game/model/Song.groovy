package com.joecowman.crowdsurf.game.model

class Song {
    /* Config */
    int keywordLinesToReserve = 3

    /* Init Properties */
    int duration
    SongGenre genre
    List<ContextWord> contextWords = []

    /* Changed by game */
    int timeRemaining
    List<LyricLine> lyrics = []
    List<String> rhymes = []
    List<Set<String>> recentlyUsedKeywords = []

    void setDuration(int duration) {
        this.duration = duration
        timeRemaining = duration
    }

    /*
     * Returns the number of appearances of newWords in recentlyUsedKeywords (including duplicates),
     * then removes the oldest line and adds this one.
     */
    int duplicateKeywords(Set<String> newWords) {
        int matches = 0
        List oldWords = recentlyUsedKeywords.flatten()

        newWords.each {
            matches += oldWords.count(it)
        }

        if (recentlyUsedKeywords.size() >= keywordLinesToReserve) {
            recentlyUsedKeywords.pop()
        }
        recentlyUsedKeywords.add(0, newWords)

        return matches
    }
}
