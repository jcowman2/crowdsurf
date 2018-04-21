package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.exception.EventNotYetExecutedException

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
