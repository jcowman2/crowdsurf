package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.exception.EventNotYetExecutedException
import com.joecowman.crowdsurf.game.model.GameInstance

abstract class GameEvent {

    private boolean hasExecuted = false

    void execute(GameInstance game) {
        hasExecuted = true
        onExecute(game)
    }

    List<OutputLine> getOutput() {
        if (!hasExecuted) {
            throw new EventNotYetExecutedException()
        }

        return generateOutput()
    }

    protected abstract void onExecute(GameInstance game)

    protected abstract List<OutputLine> generateOutput()
}