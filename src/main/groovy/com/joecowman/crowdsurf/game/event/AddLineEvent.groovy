package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
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
        super.execute(game)

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
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output << new OutputLine("You sing \"$newLine.text\".")

        if (!isFirst) {
            if (isRhyme) {
                output << new OutputLine("That rhymed with line $rhymeLine!")
            } else {
                output << new OutputLine("That didn't rhyme.")
            }
        }

        output << new OutputLine("That line had a score of $contextScore.")

        return output
    }
}
