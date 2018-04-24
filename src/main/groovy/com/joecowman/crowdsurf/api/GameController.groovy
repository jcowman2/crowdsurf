package com.joecowman.crowdsurf.api

import com.joecowman.crowdsurf.api.model.GameInfo
import com.joecowman.crowdsurf.api.model.GameRequest
import com.joecowman.crowdsurf.api.model.GameResponse
import com.joecowman.crowdsurf.game.CommandParser
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.util.StrUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("crowdsurf/api")
class GameController {

    private static Logger LOG = LoggerFactory.getLogger(GameController.class)

    @GetMapping("/info")
    ResponseEntity info() {
        GameInfo info = new GameInfo(
                title: "Crowdsurf",
                author: "Joe Cowman",
                version: "1.0",
                website: "https://github.com/jcowman2/crowdsurf",
                description: "A text-based music performance game. Developed for Ludum Dare 41 (\"Combine Two Incompatible Genres\")."
        )

        return ResponseEntity.ok(info)
    }

    @GetMapping("/welcome")
    ResponseEntity welcome() {
        String welcome = "Welcome to Crowdsurf!\n" +
                "If this is your first time playing, type \"help\" for a few instructions.\n" +
                "Otherwise, type \"start\" to begin the game. Have fun!"

        return ResponseEntity.ok(welcome)
    }

    @PostMapping("/play")
    ResponseEntity play(@RequestBody GameRequest gameIn) {
        String keywordString = "null"
        if (gameIn.state && gameIn.state.currentSong) {
            keywordString = StrUtil.formatTopics(gameIn.state.currentSong.contextWords)
        }
        LOG.info("Play endpoint. { command: $gameIn.command, keywords: $keywordString}")

        GameInstance game = CommandParser.parse(gameIn.command, gameIn.state, gameIn.payload)
        game.run()

        GameResponse response = new GameResponse(
                output: game.output,
                requirements: game.responseRequirements,
                state: game.state
        )

        return ResponseEntity.ok(response)
    }

}
