package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.LyricScorecard


class ScoreLineEvent extends GameEvent {
    LyricScorecard scorecard

    private int lyricPoints
    private int totalScore

    @Override
    protected void onExecute(GameInstance game) {
        lyricPoints = 1 + scorecard.contextScore
        lyricPoints *= scorecard.didRhyme ? 3 : 1

        game.state.score += lyricPoints

        totalScore = game.state.score
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output << new OutputLine("Points received: +$lyricPoints. Total Score: $totalScore")
    }
}
