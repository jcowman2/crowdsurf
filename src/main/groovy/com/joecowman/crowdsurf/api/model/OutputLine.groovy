package com.joecowman.crowdsurf.api.model

class OutputLine {
    String text
    OutputLineType type

    OutputLine(String text) {
        this(text, OutputLineType.NORMAL)
    }

    OutputLine(String text, OutputLineType type) {
        this.text = text
        this.type = type
    }

    static OutputLine normal(String text) {
        return new OutputLine(text)
    }

    static OutputLine debug(String text) {
        return new OutputLine(text, OutputLineType.DEBUG)
    }

    static OutputLine error(String text) {
        return new OutputLine(text, OutputLineType.ERROR)
    }

    static OutputLine empty() {
        return new OutputLine("", OutputLineType.EMPTY)
    }
}
