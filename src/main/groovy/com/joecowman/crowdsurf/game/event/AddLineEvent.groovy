package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.api.model.OutputLineType
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.LyricScorecard
import com.joecowman.crowdsurf.game.util.RhymeUtil
import com.joecowman.crowdsurf.game.model.LyricLine
import com.joecowman.crowdsurf.game.model.Song
import com.joecowman.crowdsurf.game.util.SimilarUtil

class AddLineEvent extends GameEvent {
    LyricLine newLine

    private boolean isFirst
    private boolean isRhyme
    private int rhymeLine
    private int contextScore

    @Override
    protected void onExecute(GameInstance game) {
        Song song = game.state.currentSong

        if (song.lyrics.size() == 0) {
            isFirst = true
        } else {
            rhymeLine = RhymeUtil.recentRhyme(newLine, song)

            if (rhymeLine >= 0) {
                isRhyme = true
            }
        }

        contextScore = SimilarUtil.testSimilar(newLine, song.contextWords)

        song.lyrics.add(newLine)

        LyricScorecard scorecard = new LyricScorecard(didRhyme: isRhyme, contextScore: contextScore)
        game.doNext(new ScoreLineEvent(scorecard: scorecard))
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output << OutputLine.normal("You sing \"$newLine.text\".")

        if (!isFirst) {
            if (isRhyme) {
                output << OutputLine.debug("That rhymed with line $rhymeLine!")
            } else {
                output << OutputLine.debug("That didn't rhyme.")
            }
        }

        String plural = (contextScore == 1) ? "" : "s"
        output << OutputLine.debug("That line had $contextScore keyword$plural.")

        return output
    }
}
