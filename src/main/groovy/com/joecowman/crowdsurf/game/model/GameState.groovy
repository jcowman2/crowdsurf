package com.joecowman.crowdsurf.game.model

import java.time.LocalDateTime

class GameState {
    int commandNumber = 0
    int score = 0
    int songNumber = 0
    int crowdHype = 50

    Song currentSong

    LocalDateTime lastRequestTimestamp

    void resetTimestamp() {
        lastRequestTimestamp = null
    }
}