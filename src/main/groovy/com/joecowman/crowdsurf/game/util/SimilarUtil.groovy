package com.joecowman.crowdsurf.game.util

import com.joecowman.crowdsurf.accessor.DatamuseClient
import com.joecowman.crowdsurf.game.model.ContextWord
import com.joecowman.crowdsurf.game.model.LyricLine
import com.joecowman.crowdsurf.game.model.SimilarityResult
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

    static SimilarityResult testSimilar(LyricLine line, List<ContextWord> keywords) {
        int matches = 0
        List<String> tests = (line.words + line.bigrams).unique()
        Set<String> matchingKeywords = new HashSet<>()


        keywords.each { keyWord ->
            Set<String> similars = datamuseClient.similar(keyWord.word, keyWord.topicString, SIMILAR_REQUEST_LENGTH).collect{it.word}
            similars.add(keyWord.word)
            Set<String> intersect = similars.intersect(tests)
            matchingKeywords.addAll(intersect)
            matches += intersect.size()
        }

        return new SimilarityResult(numMatches: matches, matchingKeywords: matchingKeywords)
    }
}
