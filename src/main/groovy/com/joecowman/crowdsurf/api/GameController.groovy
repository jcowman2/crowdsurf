package com.joecowman.crowdsurf.api

import com.joecowman.crowdsurf.accessor.DatamuseClient
import com.joecowman.crowdsurf.api.model.GameInfo
import com.joecowman.crowdsurf.api.model.GameRequest
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
        return ResponseEntity.ok(datamuseClient.similarMeaning(lyric))
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

    @PostMapping
    ResponseEntity play(@RequestBody GameRequest gameIn) {

    }

}
