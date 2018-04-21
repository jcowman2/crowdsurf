package com.joecowman.crowdsurf.game.function

import com.joecowman.crowdsurf.api.model.OutputLine

abstract class GameEvent {

    private boolean hasExecuted = false

    void execute() {
        hasExecuted = true
    }

    List<OutputLine> getOutput() {
        if (!hasExecuted) {
            throw new EventNotYetExecutedException()
        }

        return null
    }

}
