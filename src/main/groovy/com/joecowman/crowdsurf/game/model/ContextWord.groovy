package com.joecowman.crowdsurf.game.model

class ContextWord {
    String word
    List<String> topicWords

    String getTopicString() {
        return topicWords.join(',')
    }
}
