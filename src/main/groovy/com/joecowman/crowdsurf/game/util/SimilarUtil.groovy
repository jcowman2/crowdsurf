package com.joecowman.crowdsurf.game.util

import com.joecowman.crowdsurf.accessor.DatamuseClient
import com.joecowman.crowdsurf.game.model.ContextWord
import com.joecowman.crowdsurf.game.model.LyricLine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SimilarUtil {

    static int SIMILAR_REQUEST_LENGTH = 1000

    private static DatamuseClient datamuseClient

    @Autowired
    SimilarUtil(DatamuseClient datamuseClient) {
        SimilarUtil.datamuseClient = datamuseClient
    }

    static int testSimilar(LyricLine line, List<ContextWord> keywords) {
        int matches = 0
        List<String> tests = line.words + line.bigrams

        keywords.each { word ->
            List<String> similars = datamuseClient.similar(word.word, word.topicString, SIMILAR_REQUEST_LENGTH).collect{it.word}
            matches += similars.intersect(tests).size()
        }

        return matches
    }
}
