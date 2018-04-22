package com.joecowman.crowdsurf.api

import com.joecowman.crowdsurf.accessor.DatamuseClient
import com.joecowman.crowdsurf.api.model.GameInfo
import com.joecowman.crowdsurf.api.model.GameRequest
import com.joecowman.crowdsurf.api.model.GameResponse
import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.event.AddLineEvent
import com.joecowman.crowdsurf.game.model.ContextWord
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.GameState
import com.joecowman.crowdsurf.game.model.LyricLine
import com.joecowman.crowdsurf.game.model.Song
import com.joecowman.crowdsurf.game.util.Yikes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("crowdsurf/api")
class GameController {

    @Autowired
    DatamuseClient datamuseClient

    @GetMapping("/info")
    ResponseEntity info() {
        GameInfo info = new GameInfo(
                title: "Crowdsurf",
                author: "Joe Cowman",
                version: "1.0 (SNAPSHOT)",
                website: "http://joecowman.com",
                description: "Created in 72 hours for Ludum Dare 41."
        )

        return ResponseEntity.ok(info)
    }

    @PostMapping("/play")
    ResponseEntity play(@RequestBody GameRequest gameIn) {
        String command = gameIn.command
        GameState state = gameIn.state

        if (!state) {
            state = new GameState()
            state.currentSong = new Song()
            state.currentSong.contextWords.add(new ContextWord(word: 'dog', topicWords: ['animal']))
        }
        state.commandNumber++

        GameInstance game = new GameInstance(state)
        AddLineEvent cmd = new AddLineEvent(newLine: new LyricLine(text: command))
        game.doNext(cmd)
        game.run()

        GameResponse response = new GameResponse(
                output: game.output,
                state: game.state
        )

        return ResponseEntity.ok(response)
    }

}
