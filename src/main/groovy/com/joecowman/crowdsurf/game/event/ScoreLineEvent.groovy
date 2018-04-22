package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.LyricScorecard

import static com.joecowman.crowdsurf.game.util.StrUtil.*

class ScoreLineEvent extends GameEvent {
    int rhymeMod = 3
    int contextMod = 2
    LyricScorecard scorecard

    private int ctxScore
    private int rhymeScore

    private int lyricPoints
    private int totalScore

    @Override
    protected void onExecute(GameInstance game) {
        ctxScore = scorecard.contextScore * contextMod

        if (scorecard.isRhyme) {
            rhymeScore = (scorecard.rhymeRepeats > 0) ? -scorecard.rhymeRepeats : rhymeMod
        }

        lyricPoints = ctxScore + rhymeScore
        game.state.score += lyricPoints
        totalScore = game.state.score
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output << OutputLine.debug("That line had $scorecard.contextScore keyword${pl(scorecard.contextScore)}. (${signed(ctxScore)})")

        if (!scorecard.isFirst) {
            if (scorecard.isRhyme) {
                if (scorecard.rhymeRepeats > 0) {
                    output << OutputLine.debug("You've used that rhyme $scorecard.rhymeRepeats time${pl(scorecard.rhymeRepeats)} already! (${signed(rhymeScore)})")
                } else {
                    output << OutputLine.debug("That rhymed with line $scorecard.rhymeLine! (${signed(rhymeScore)})")
                }
            } else {
                output << OutputLine.debug("That didn't rhyme. (+0)")
            }
        }

        output << OutputLine.normal("Points received: ${signed(lyricPoints)}. Total Score: $totalScore")
    }
}
