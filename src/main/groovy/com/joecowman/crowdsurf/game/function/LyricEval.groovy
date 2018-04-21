package com.joecowman.crowdsurf.game.function

import com.joecowman.crowdsurf.accessor.DatamuseClient
import com.joecowman.crowdsurf.accessor.model.DmWord
import com.joecowman.crowdsurf.game.model.LyricLine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LyricEval {

    private static DatamuseClient datamuseClient

    @Autowired
    LyricEval(DatamuseClient datamuseClient) {
        LyricEval.datamuseClient = datamuseClient
    }

    static boolean lyricsRhyme(LyricLine newLine, LyricLine oldLine) {
        String lastWord = newLine.text.split(" ").last()
        List<DmWord> rhymes = datamuseClient.rhymes(lastWord)
        return rhymes.any { oldLine.text.endsWith(it.word) }
    }

}
