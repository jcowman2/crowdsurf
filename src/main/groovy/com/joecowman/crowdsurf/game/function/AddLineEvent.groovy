package com.joecowman.crowdsurf.game.function

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.LyricLine
import com.joecowman.crowdsurf.game.model.Lyrics

class AddLineEvent extends GameEvent {
    LyricLine newLine
    Lyrics lyrics

    private boolean isFirst
    private boolean isRhyme
    private int rhymeLine

    void execute() {
        super.execute()

        if (lyrics.lines.size() == 0) {
            isFirst = true
        } else if (LyricEval.lyricsRhyme(newLine, lyrics.lines.last())) {
            isRhyme = true
            rhymeLine = lyrics.lines.size() - 1
        }

        lyrics.lines.add(newLine)
    }

    List<OutputLine> getOutput() {
        super.getOutput()

        List<OutputLine> output = []

        output << new OutputLine("You sing \"$newLine.text\".")

        if (!isFirst) {
            if (isRhyme) {
                output << new OutputLine("That rhymed with line $rhymeLine!")
            } else {
                output << new OutputLine("That didn't rhyme.")
            }
        }

        return output
    }
}
