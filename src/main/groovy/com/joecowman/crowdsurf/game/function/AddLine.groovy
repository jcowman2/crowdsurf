package com.joecowman.crowdsurf.game.function

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.LyricLine
import com.joecowman.crowdsurf.game.model.Lyrics

class AddLine extends GameEvent {
    LyricLine newLine
    Lyrics lyrics

    private boolean isRhyme
    private int rhymeLine

    void execute() {
        super.execute()

        if (lyrics.lines.size() > 0 && LyricEval.lyricsRhyme(newLine, lyrics.lines.last())) {
            isRhyme = true
            rhymeLine = lyrics.lines.size() - 1
        }

        lyrics.lines.add(newLine)
    }

    List<OutputLine> getOutput() {
        super.getOutput()

        List<OutputLine> output = []
        if (isRhyme) {
            output << new OutputLine("That rhymed with line $rhymeLine!")
        } else {
            output << new OutputLine("That didn't rhyme.")
        }

        return output
    }
}
