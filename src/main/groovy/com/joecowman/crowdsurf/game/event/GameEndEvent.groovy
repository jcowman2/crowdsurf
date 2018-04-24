package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

class GameEndEvent extends GameEvent {
    String reason

    private int finalScore

    @Override
    protected void onExecute(GameInstance game) {
        game.state = null
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output << OutputLine.normal("")

        if (reason) {
            output << OutputLine.normal(reason)
        }

        output << OutputLine.normal("You ended with a score of $finalScore")
        output << OutputLine.normal("Thank you for playing!")
    }
}
