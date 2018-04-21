package com.joecowman.crowdsurf.api

import com.joecowman.crowdsurf.accessor.DatamuseClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
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

    @PostMapping("/rate")
    ResponseEntity rate(@RequestBody String lyric) {
        return ResponseEntity.ok(datamuseClient.similarMeaning(lyric))
    }

}
