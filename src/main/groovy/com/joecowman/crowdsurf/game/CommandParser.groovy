package com.joecowman.crowdsurf.game

import com.joecowman.crowdsurf.game.event.*
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.GameState
import com.joecowman.crowdsurf.game.model.LyricLine

class CommandParser {

    static GameInstance parse(String command, GameState state, Object payload) {
        GameEvent event

        if (command == "help") {
            event = new HelpEvent()
        } else {
            //Game not yet started
            if (!state) {
                if (command.startsWith("start") || command.startsWith("new") || command.startsWith("play")) {
                    event = new ClientActionRequiredEvent(action: Constants.POST_SONG_NAMES)
                } else if (command == Constants.POST_SONG_NAMES) {
                    state = new GameState()
                    event = new NewGameEvent(songOptions: payload as List<String>)
                } else {
                    event = new IllegalCommandEvent("You can't do that yet! Type \"start\" to start the game.")
                }
                //Current Game
            } else {
                state.commandNumber++

                if (command.isAllWhitespace()) {
                    event = new IllegalCommandEvent("Please enter a command.")

                    //Song Commands
                } else if (state.currentSong) {

                    if (command.startsWith("*stop")) {
                        event = new SongEndEvent("You cut the band off. They stop playing and glare at you.")
                    } else if (command.startsWith("*key")) {
                        event = new PrintKeywordsEvent()
                    } else if (command == Constants.SONG_TIMEOUT_COMMAND) {
                        event = new LyricTimeoutEvent()
                    } else if (command == Constants.SONG_DURATION_OVER) {
                        event = new SongEndEvent()
                    } else {
                        event = new AddLineEvent(new LyricLine(text: command))
                    }

                } else if (command.startsWith("next") || command.startsWith("start")) {
                    event = new NewSongEvent()
                } else if (command == Constants.SONG_DURATION_OVER || command == Constants.SONG_TIMEOUT_COMMAND) {
                    event = new NoActionEvent() //Delayed song over automated event
                } else {
                    event = new IllegalCommandEvent("You can't do that now! Type \"next\" to play the next song.")
                }
            }
        }

        GameInstance game = new GameInstance(state)
        game.doNext(event)

        return game
    }

}
