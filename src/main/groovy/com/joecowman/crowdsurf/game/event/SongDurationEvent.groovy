package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

import java.time.Duration
import java.time.LocalDateTime

import static com.joecowman.crowdsurf.game.util.StrUtil.pl

class SongDurationEvent extends GameEvent {

    private int secondsPassed
    private int secondsRemaining

    @Override
    protected void onExecute(GameInstance game) {
        LocalDateTime now = LocalDateTime.now()

        if (game.state.lastRequestTimestamp) {
            secondsPassed = Duration.between(game.state.lastRequestTimestamp, now).seconds
            game.state.currentSong.timeRemaining -= secondsPassed
        }

        secondsRemaining = game.state.currentSong.timeRemaining

        game.state.lastRequestTimestamp = now
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.debug("$secondsRemaining second${pl(secondsRemaining)} remain.")]
    }

}
