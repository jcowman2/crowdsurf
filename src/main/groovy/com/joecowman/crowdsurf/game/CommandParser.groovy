package com.joecowman.crowdsurf.game

import com.joecowman.crowdsurf.game.event.AddLineEvent
import com.joecowman.crowdsurf.game.model.*

class CommandParser {

    static GameInstance parse(String command, GameState state) {
        if (!state) {
            state = new GameState()
            state.currentSong = new Song()
            state.currentSong.contextWords.add(new ContextWord('dog', ['animal']))
        }
        state.commandNumber++

        GameInstance game = new GameInstance(state)
        AddLineEvent cmd = new AddLineEvent(newLine: new LyricLine(text: command))
        game.doNext(cmd)

        return game
    }

}
