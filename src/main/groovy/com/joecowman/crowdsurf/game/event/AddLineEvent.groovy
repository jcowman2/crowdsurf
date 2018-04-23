package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.*
import com.joecowman.crowdsurf.game.util.RhymeUtil
import com.joecowman.crowdsurf.game.util.SimilarUtil

class AddLineEvent extends GameEvent {
    LyricLine newLine

    AddLineEvent(LyricLine line) {
        newLine = line
    }

    @Override
    protected void onExecute(GameInstance game) {
        Song song = game.state.currentSong

        boolean isFirst = (song.lyrics.size() == 0)

        boolean isRhyme = false
        int rhymeLine = 0
        int rhymeRepeats = 0

        if (!isFirst) {
            rhymeLine = RhymeUtil.recentRhyme(newLine, song)

            if (rhymeLine >= 0) {
                isRhyme = true
                String lastWord = newLine.words.last()
                rhymeRepeats = song.rhymes.count(lastWord)
                song.rhymes.add(lastWord)
            }
        }

        SimilarityResult simRes = SimilarUtil.testSimilar(newLine, song.contextWords)

        song.lyrics.add(newLine)
        int duplicates =  song.duplicateKeywords(simRes.matchingKeywords)

        LyricScorecard scorecard = new LyricScorecard(
                isFirst: isFirst,
                isRhyme: isRhyme,
                rhymeLine: rhymeLine,
                rhymeRepeats: rhymeRepeats,
                contextScore: simRes.numMatches,
                duplicates: duplicates
        )
        game.doNext(new ScoreLineEvent(scorecard: scorecard))
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.normal("You sing \"$newLine.text\".")]
    }
}
