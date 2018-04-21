package com.joecowman.crowdsurf.game.model

class LyricLine {
    private String text
    private List<String> words
    private List<String> bigrams

    void setText(String text) {
        this.text = text
        words = text.split("[\\p{Punct}\\s+]").toList()
        words.removeAll{ !it }
        bigrams = words.collate(2, false).collect { it.join(" ") }
        bigrams += words.subList(1, words.size()).collate(2, false).collect { it.join(" ") }
    }

    String getText() {
        return text
    }

    List<String> getWords() {
        return words
    }

    List<String> getBigrams() {
        return bigrams
    }
}
