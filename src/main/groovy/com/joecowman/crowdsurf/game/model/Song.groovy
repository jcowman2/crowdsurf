package com.joecowman.crowdsurf.game.model

class Song {
    SongGenre genre
    List<ContextWord> contextWords = new ArrayList<>()
    List<LyricLine> lyrics = new ArrayList<>()
    List<String> rhymes = new ArrayList<>()
}
