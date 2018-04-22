package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.exception.EventNotYetExecutedException
import com.joecowman.crowdsurf.game.model.GameInstance

abstract class GameEvent {

    private boolean hasExecuted = false
    private List<OutputLine> output

    void execute(GameInstance game) {
        hasExecuted = true
        onExecute(game)
        output = generateOutput() //Ensure output is generated at event runtime
    }

    List<OutputLine> getOutput() {
        if (!hasExecuted) {
            throw new EventNotYetExecutedException()
        }

        return output
    }

    protected abstract void onExecute(GameInstance game)

    protected abstract List<OutputLine> generateOutput()
}
