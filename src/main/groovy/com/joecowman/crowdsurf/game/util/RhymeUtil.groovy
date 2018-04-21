package com.joecowman.crowdsurf.game.util

import com.joecowman.crowdsurf.accessor.DatamuseClient
import com.joecowman.crowdsurf.accessor.model.DmWord
import com.joecowman.crowdsurf.game.model.LyricLine
import com.joecowman.crowdsurf.game.model.Song
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RhymeUtil {

    static int PREVIOUS_LINES_TO_CHECK_FOR_RHYME = 3

    private static DatamuseClient datamuseClient

    @Autowired
    RhymeUtil(DatamuseClient datamuseClient) {
        RhymeUtil.datamuseClient = datamuseClient
    }

    //Returns -1 if no match, otherwise index of first match
    static int lyricsRhyme(LyricLine newLine, List<LyricLine> oldLines) {
        String lastWord = newLine.text.split(" ").last()
        List<DmWord> rhymes = datamuseClient.rhymes(lastWord)
        return oldLines.findIndexOf { line ->
            rhymes.any { line.text.endsWith(it.word) }
        }
    }

    //Returns -1 if no match, otherwise index of MOST RECENT match
    static int recentRhyme(LyricLine newLine, Song song, int num = PREVIOUS_LINES_TO_CHECK_FOR_RHYME) {
        List<LyricLine> lines = song.lyrics.takeRight(num).reverse()

        int localIndex = lyricsRhyme(newLine, lines)

        if (localIndex >= 0) {
            return song.lyrics.size() - 1 - localIndex
        } else {
            return -1
        }
    }

}
