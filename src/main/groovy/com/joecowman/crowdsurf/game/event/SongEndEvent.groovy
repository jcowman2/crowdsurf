package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.Constants
import com.joecowman.crowdsurf.game.model.GameInstance

class SongEndEvent extends GameEvent {
    String reason

    private int songNumber
    private int crowdHype
    private String bandName

    SongEndEvent(String reason = "") {
        this.reason = reason
    }

    @Override
    protected void onExecute(GameInstance game) {
        game.state.currentSong = null
        songNumber = game.state.songNumber
        crowdHype = game.state.crowdHype
        bandName = game.state.bandName

        String filename = game.state.songs[game.state.songNumber - 1]
        game.doNext(new ClientActionRequiredEvent(action: Constants.STOP_SONG, args: ['filename': filename ]))

        if (songNumber == 3) {
            game.enqueue(new GameEndEvent(reason: "The show is over."))
        }
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        if (!reason) {
            reason = "That's the end of song #$songNumber!"
        }
        output << OutputLine.normal(reason)

        if (songNumber < 3) {
            String summary
            String songWord
            if (songNumber == 1) {
                songWord = "next"
                if (crowdHype <= 25) {
                    summary = "Yikes. This started really poorly. Bring it back for the second song!"
                } else if (crowdHype <= 50) {
                    summary = "The crowd isn't as receptive as you'd hoped. Maybe this next song will be better."
                } else if (crowdHype <= 75) {
                    summary = "You're doing great! Keep the energy going!"
                } else {
                    summary = "This is insane! You should always have Spider knock you out before a show!"
                }
            } else {
                songWord = "last"
                if (crowdHype <= 25) {
                    summary = "This is not your night. Try and finish strong!"
                } else if (crowdHype <= 50) {
                    summary = "The crowd is losing interest. You need to rock this finale."
                } else if (crowdHype <= 75) {
                    summary = "Awesome energy! See if you can take it to another level for the finale!"
                } else {
                    summary = "This place is unreal! Keep the fire going, don't let them down!"
                }
            }

            output << OutputLine.normal(summary)
            output << OutputLine.normal("Type \"next\" to start the $songWord song.")

        } else {
            String summary
            if (crowdHype <= 25) {
                summary = "Hey, at least it's over."
            } else if (crowdHype <= 50) {
                summary = "The show could've been better. It definitely could've been worse, too."
            } else if (crowdHype <= 75) {
                summary = "You put on a great show. Nice job!"
            } else {
                summary = "What an incredible performance. No one will forget $bandName!"
            }
            output << OutputLine.normal(summary)
        }

        return output
    }

}
