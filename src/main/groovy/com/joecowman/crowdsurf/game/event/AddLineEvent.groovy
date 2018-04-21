package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.LyricScorecard
import com.joecowman.crowdsurf.game.util.RhymeUtil
import com.joecowman.crowdsurf.game.model.LyricLine
import com.joecowman.crowdsurf.game.model.Song
import com.joecowman.crowdsurf.game.util.SimilarUtil

class AddLineEvent extends GameEvent {
    LyricLine newLine

    @Override
    protected void onExecute(GameInstance game) {
        Song song = game.state.currentSong

        boolean isFirst = (song.lyrics.size() == 0)
        int rhymeLine = 0
        boolean isRhyme = false

        if (!isFirst) {
            rhymeLine = RhymeUtil.recentRhyme(newLine, song)

            if (rhymeLine >= 0) {
                isRhyme = true
            }
        }

        int contextScore = SimilarUtil.testSimilar(newLine, song.contextWords)

        song.lyrics.add(newLine)

        LyricScorecard scorecard = new LyricScorecard(
                isFirst: isFirst,
                isRhyme: isRhyme,
                rhymeLine: rhymeLine,
                contextScore: contextScore
        )
        game.doNext(new ScoreLineEvent(scorecard: scorecard))
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.normal("You sing \"$newLine.text\".")]
    }
}
