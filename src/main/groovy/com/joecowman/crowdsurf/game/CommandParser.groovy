package com.joecowman.crowdsurf.game

import com.joecowman.crowdsurf.game.event.*
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.GameState
import com.joecowman.crowdsurf.game.model.LyricLine

class CommandParser {

    static GameInstance parse(String command, GameState state) {
        GameEvent event

        if (!state) {
            if (command.startsWith("start") || command.startsWith("new") || command.startsWith("play")) {
                state = new GameState()
                state.commandNumber++
                event = new NewSongEvent()
            } else {
                event = new IllegalCommandEvent("You can't do that yet! Type \"start\" to start the game.")
            }
        } else {
            state.commandNumber++

            if (state.currentSong) {

                if (command.startsWith("*stop")) {
                    event = new SongEndEvent("You cut the band off. They stop playing and glare at you.")
                } else if (command.startsWith("*key")) {
                    event = new PrintKeywordsEvent()
                } else if (command == Constants.SONG_TIMEOUT_COMMAND) {
                    event = new LyricTimeoutEvent()
                } else{
                    event = new AddLineEvent(new LyricLine(text: command))
                }

            } else if (command.startsWith("next")) {
                event = new NewSongEvent()
            } else {
                event = new IllegalCommandEvent("You can't do that now! Type \"next\" to play the next song.")
            }
        }

        GameInstance game = new GameInstance(state)
        game.doNext(event)

        return game
    }

}
