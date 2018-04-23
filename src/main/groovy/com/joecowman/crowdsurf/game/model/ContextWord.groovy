package com.joecowman.crowdsurf.game.model

class ContextWord {
    String word
    List<String> topicWords = new ArrayList<>()

    ContextWord() {}

    ContextWord(String word) {
        this.word = word
    }

    ContextWord(String word, List<String> topicWords) {
        this.word = word
        this.topicWords = topicWords
    }

    String getTopicString() {
        return topicWords.join(',')
    }
}
