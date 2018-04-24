package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.GameState

import static com.joecowman.crowdsurf.game.util.StrUtil.signed

class GameEndEvent extends GameEvent {
    String reason

    private int finalScore
    private String letterGrade

    private String bestScoringLine
    private int bestLineScore
    private String worstScoringLine
    private int worstLineScore

    @Override
    protected void onExecute(GameInstance game) {
        GameState state = game.state

        finalScore = state.score
        letterGrade = calcLetterGrade(finalScore)

        bestScoringLine = state.bestScoringLine
        bestLineScore = state.bestLineScore
        worstScoringLine = state.worstScoringLine
        worstLineScore = state.worstLineScore

        game.state = null
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output << OutputLine.normal("")

        if (reason) {
            output << OutputLine.normal(reason)
        }

        output.addAll([
                OutputLine.normal("You ended with a score of $finalScore, which earned you a grade of $letterGrade!"),
                OutputLine.normal(""),
                OutputLine.normal("Your greatest lyric was \"$bestScoringLine\" (${signed(bestLineScore)} Points)"),
                OutputLine.normal("Your worst lyric was \"$worstScoringLine\" (${signed(worstLineScore)} Points)"),
                OutputLine.normal("Thank you for playing!")
        ])

        return output
    }

    private String calcLetterGrade(int score) {
        String letterGrade

        if (score < 20) {
            letterGrade = "F"

            if (score < 0) {
                int mins = Math.ceil((0 - score) / 20)
                letterGrade += '-' * mins
            }
        } else if (score < 40) {
            letterGrade = "D"
        } else if (score < 60) {
            letterGrade = "C"
        } else if (score < 80) {
            letterGrade = "B"
        } else {
            letterGrade = "A"

            if (score >= 100) {
                int pls = Math.ceil((score - 100 + 1) / 20)
                letterGrade += '+' * pls
            }
        }

        return letterGrade
    }
}
