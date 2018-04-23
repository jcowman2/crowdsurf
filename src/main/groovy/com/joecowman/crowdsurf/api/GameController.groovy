package com.joecowman.crowdsurf.api

import com.joecowman.crowdsurf.api.model.GameInfo
import com.joecowman.crowdsurf.api.model.GameRequest
import com.joecowman.crowdsurf.api.model.GameResponse
import com.joecowman.crowdsurf.game.CommandParser
import com.joecowman.crowdsurf.game.model.GameInstance
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("crowdsurf/api")
class GameController {

    @GetMapping("/info")
    ResponseEntity info() {
        GameInfo info = new GameInfo(
                title: "Crowdsurf",
                author: "Joe Cowman",
                version: "1.0 (SNAPSHOT 4)",
                website: "http://joecowman.com",
                description: "Under development for Ludum Dare 41."
        )

        return ResponseEntity.ok(info)
    }

    @PostMapping("/play")
    ResponseEntity play(@RequestBody GameRequest gameIn) {
        GameInstance game = CommandParser.parse(gameIn.command, gameIn.state)
        game.run()

        GameResponse response = new GameResponse(
                output: game.output,
                requirements: game.responseRequirements,
                state: game.state
        )

        return ResponseEntity.ok(response)
    }

}
