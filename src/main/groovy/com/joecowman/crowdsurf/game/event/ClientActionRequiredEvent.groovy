package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.ClientAction
import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

class ClientActionRequiredEvent extends GameEvent {
    String action
    Map args

    @Override
    protected void onExecute(GameInstance game) {
        game.responseRequirements.clientAction = new ClientAction(action: action, args: args)
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.empty()]
    }
}
