package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.Constants
import com.joecowman.crowdsurf.game.model.GameInstance

import java.time.Duration
import java.time.LocalDateTime

import static com.joecowman.crowdsurf.game.util.StrUtil.pl

class SongDurationEvent extends GameEvent {
    int timeoutInterval = 15

    private int secondsPassed
    private int secondsRemaining

    @Override
    protected void onExecute(GameInstance game) {
        LocalDateTime now = LocalDateTime.now()

        if (game.state.lastRequestTimestamp) {
            secondsPassed = Duration.between(game.state.lastRequestTimestamp, now).seconds
            game.state.currentSong.timeRemaining -= secondsPassed
        }

        game.state.lastRequestTimestamp = now

        secondsRemaining = game.state.currentSong.timeRemaining

        if (secondsRemaining <= 0) {
            secondsRemaining = 0
            game.doNext(new SongEndEvent())
        } else {
            int thisTimeoutInterval
            String command

            if (timeoutInterval >= secondsRemaining) {
                thisTimeoutInterval = secondsRemaining
                command = Constants.SONG_DURATION_OVER
            } else {
                thisTimeoutInterval = timeoutInterval
                command = Constants.SONG_TIMEOUT_COMMAND
            }

            game.responseRequirements.with {
                it.autoCallbackAfterTimeout = true
                it.timeoutSeconds = thisTimeoutInterval
                it.timeoutCommand = command
            }
        }
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.debug("$secondsRemaining second${pl(secondsRemaining)} remain${pl(secondsRemaining, '', 's')}.")]
    }

}
