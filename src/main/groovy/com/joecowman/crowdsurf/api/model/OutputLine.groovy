package com.joecowman.crowdsurf.api.model

class OutputLine {
    String text
    OutputLineType type

    OutputLine() {}

    OutputLine(String text) {
        this.text = text
        type = OutputLineType.NORMAL
    }
}
