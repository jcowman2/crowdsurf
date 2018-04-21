package com.joecowman.crowdsurf.api

import com.joecowman.crowdsurf.accessor.DatamuseClient
import com.joecowman.crowdsurf.api.model.GameInfo
import com.joecowman.crowdsurf.api.model.GameRequest
import com.joecowman.crowdsurf.api.model.GameResponse
import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.event.AddLineEvent
import com.joecowman.crowdsurf.game.model.GameState
import com.joecowman.crowdsurf.game.model.LyricLine
import com.joecowman.crowdsurf.game.model.Lyrics
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

    @PostMapping("/similar") //todo remove
    ResponseEntity rate(@RequestBody String lyric) {
        return ResponseEntity.ok(datamuseClient.similarMeaningMetadata(lyric))
    }

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
            state = new GameState(commandNumber: 0)
            state.lyrics = new Lyrics()
        }
        state.commandNumber++

        AddLineEvent cmd = new AddLineEvent(newLine: new LyricLine(text: command), lyrics: state.lyrics)
        cmd.execute()
        List<OutputLine> output = cmd.output

        GameResponse response = new GameResponse(
                output: output,
                state: state
        )

        return ResponseEntity.ok(response)
    }

}
