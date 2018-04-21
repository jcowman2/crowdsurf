package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.LyricScorecard


class ScoreLineEvent extends GameEvent {
    int rhymeMod = 3
    LyricScorecard scorecard

    private int lyricPoints
    private int totalScore

    @Override
    protected void onExecute(GameInstance game) {
        lyricPoints = 1 + scorecard.contextScore
        lyricPoints *= scorecard.isRhyme ? rhymeMod : 1

        game.state.score += lyricPoints

        totalScore = game.state.score
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        String plural = (scorecard.contextScore == 1) ? "" : "s"
        output << OutputLine.debug("That line had $scorecard.contextScore keyword$plural. (+${1 + scorecard.contextScore})")

        if (!scorecard.isFirst) {
            if (scorecard.isRhyme) {
                output << OutputLine.debug("That rhymed with line $scorecard.rhymeLine! (x$rhymeMod)")
            } else {
                output << OutputLine.debug("That didn't rhyme. (x1)")
            }
        }

        output << OutputLine.normal("Points received: +$lyricPoints. Total Score: $totalScore")
    }
}
