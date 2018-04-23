package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

class SongEndEvent extends GameEvent {
    String reason

    private int songNumber

    SongEndEvent(String reason = "") {
        this.reason = reason
    }

    @Override
    protected void onExecute(GameInstance game) {
        game.state.currentSong = null
        songNumber = game.state.songNumber
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        if (!reason) {
            reason = "That's the end of song $songNumber!"
        }

        output << OutputLine.normal(reason)
        output << OutputLine.normal("Type \"next\" to start the next song.")
    }

}