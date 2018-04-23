package com.joecowman.crowdsurf.api

import com.joecowman.crowdsurf.api.model.GameInfo
import com.joecowman.crowdsurf.api.model.GameRequest
import com.joecowman.crowdsurf.api.model.GameResponse
import com.joecowman.crowdsurf.game.CommandParser
import com.joecowman.crowdsurf.game.model.GameInstance
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.time.LocalDateTime

@CrossOrigin
@RestController
@RequestMapping("crowdsurf/api")
class GameController {

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
        gameIn.state?.thisRequestTimestamp = LocalDateTime.now()

        GameInstance game = CommandParser.parse(gameIn.command, gameIn.state)
        game.run()

        game.state.lastRequestTimestamp = LocalDateTime.now()

        GameResponse response = new GameResponse(
                output: game.output,
                state: game.state
        )

        return ResponseEntity.ok(response)
    }

}
