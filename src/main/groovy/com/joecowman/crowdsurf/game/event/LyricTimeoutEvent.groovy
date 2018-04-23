package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.ReactionTrigger

class LyricTimeoutEvent extends GameEvent {
    int penaltyPerTimeout = 5

    private int scoreChange
    @Override
    protected void onExecute(GameInstance game) {
        scoreChange = -penaltyPerTimeout //will eventually be multiplied by timeouts

        game.state.score += scoreChange

        game.doNext(new CrowdReactionEvent(ReactionTrigger.TIMEOUT, scoreChange))
        game.enqueue(new SongDurationEvent())
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.normal("It's been a while since you've said anything. ($scoreChange Points)")]
    }

}
