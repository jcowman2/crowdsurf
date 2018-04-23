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
            event = new AddLineEvent(new LyricLine(text: command))
        }

        GameInstance game = new GameInstance(state)
        game.doNext(event)
        game.enqueue(new SongDurationEvent()) //Will be executed at the end

        return game
    }

}
