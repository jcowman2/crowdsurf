package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

class NewGameEvent extends GameEvent {
    int numSongs = 3
    List<String> songOptions

    private List<String> chosenSongs = []

    @Override
    protected void onExecute(GameInstance game) {
        Collections.shuffle(songOptions)
        numSongs.times {
            chosenSongs.add(songOptions.pop())
        }

        game.state.songs = chosenSongs
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.normal("New game started. Titles chosen: $chosenSongs")]
    }
}
