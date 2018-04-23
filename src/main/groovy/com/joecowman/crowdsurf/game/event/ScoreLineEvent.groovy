package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.LyricScorecard
import com.joecowman.crowdsurf.game.model.ReactionTrigger

import static com.joecowman.crowdsurf.game.util.StrUtil.pl
import static com.joecowman.crowdsurf.game.util.StrUtil.signed

class ScoreLineEvent extends GameEvent {
    int contextMod = 2
    int rhymeMod = 3
    int duplicatesMod = 2

    LyricScorecard scorecard

    private int ctxScore
    private int duplicatesScore
    private int rhymeScore

    private int lyricPoints
    private int totalScore

    @Override
    protected void onExecute(GameInstance game) {
        if (scorecard.contextScore == 0) {
            ctxScore = -contextMod
        } else {
            ctxScore = scorecard.contextScore * contextMod
        }

        duplicatesScore = -(scorecard.duplicates * duplicatesMod)

        if (scorecard.isRhyme) {
            rhymeScore = (scorecard.rhymeRepeats > 0) ? -scorecard.rhymeRepeats : rhymeMod
        } else if (!scorecard.isFirst) {
            rhymeScore = -rhymeMod
        }

        lyricPoints = ctxScore + rhymeScore + duplicatesScore
        game.state.score += lyricPoints
        totalScore = game.state.score

        game.doNext(new CrowdReactionEvent(ReactionTrigger.LYRIC_LINE, lyricPoints))
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output << OutputLine.debug("That line had $scorecard.contextScore keyword match${pl(scorecard.contextScore, 'es')}. (${signed(ctxScore)})")

        if (duplicatesScore < 0) {
            output << OutputLine.debug("However, you just repeated yourself $scorecard.duplicates time${pl(scorecard.duplicates)}! ($duplicatesScore)")
        }

        if (!scorecard.isFirst) {
            if (scorecard.isRhyme) {
                if (scorecard.rhymeRepeats > 0) {
                    output << OutputLine.debug("You've used that rhyme $scorecard.rhymeRepeats time${pl(scorecard.rhymeRepeats)} already! (${signed(rhymeScore)})")
                } else {
                    output << OutputLine.debug("That rhymed with line $scorecard.rhymeLine! (${signed(rhymeScore)})")
                }
            } else {
                output << OutputLine.debug("That didn't rhyme. (${signed(rhymeScore)})")
            }
        }

        output << OutputLine.normal("Points received: ${signed(lyricPoints)}. Total Score: $totalScore")
    }
}
