package com.joecowman.crowdsurf.game.model

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.event.GameEvent

class GameInstance {
    private List<GameEvent> eventQueue = []
    private List<GameEvent> eventLog = []
    private List<OutputLine> output = []

    GameState state

    GameInstance(GameState state) {
        this.state = state
    }

    GameInstance run() {
        while (!eventQueue.isEmpty()) {
            GameEvent event = eventQueue.pop()
            event.execute(this)
            eventLog.add(event)
            output.addAll(event.output)
        }
        return this
    }

    //Event will be executed next
    void doNext(GameEvent event) {
        eventQueue.add(event)
    }

    //Event will be executed after all others have
    void enqueue(GameEvent event) {
        eventQueue.add(0, event)
    }

    List<OutputLine> getOutput() {
        return output
    }
}
