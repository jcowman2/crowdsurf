package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.Constants
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.GameState

class CrowdsurfEvent extends GameEvent {

    private boolean success

    @Override
    protected void onExecute(GameInstance game) {
        GameState state = game.state

        if (state.songNumber == 3 && state.crowdHype == 100 && state.currentSong.timeRemaining <= 30) {
            success = true
            state.score *= 2

            String filename = game.state.songs[game.state.songNumber - 1]

            game.doNext(new ClientActionRequiredEvent(action: Constants.STOP_SONG, args: ['filename': filename ]))
            game.enqueue(new GameEndEvent(reason: "You won the game."))
        }
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        if (success) {
            output.addAll([
                    OutputLine.normal("You toss your microphone behind you and dive face first into the crowd."),
                    OutputLine.normal("The moshpit of screaming fans hoists you high into the air."),
                    OutputLine.normal("On the stage, the microphone smacks Spider in the face. (Total Points x2)")
            ])
        } else {
            output << OutputLine.debug("Something tells you it's not the right time for that.")
        }

        return output
    }

}
