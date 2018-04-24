package com.joecowman.crowdsurf.game.model

import java.time.LocalDateTime

class GameState {
    int commandNumber = 0
    int score = 0
    int songNumber = 0
    int crowdHype = 50

    String bandName

    Song currentSong
    List<String> songs = []

    String bestScoringLine = "(nothing)"
    int bestLineScore = 0
    String worstScoringLine = "(nothing)"
    int worstLineScore = 0

    LocalDateTime lastRequestTimestamp

    void resetTimestamp() {
        lastRequestTimestamp = null
    }

    void compareLine(String line, int score) {
        if (score > bestLineScore) {
            bestScoringLine = line
            bestLineScore = score
        } else if (score < worstLineScore) {
            worstScoringLine = line
            worstLineScore = score
        }
    }
}