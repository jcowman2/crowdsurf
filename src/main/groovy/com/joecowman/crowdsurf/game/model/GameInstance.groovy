package com.joecowman.crowdsurf.game.model

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.event.GameEvent

class GameInstance {
    private Queue<GameEvent> eventQueue = new PriorityQueue<>()
    private List<GameEvent> eventLog = new ArrayList<>()
    private List<OutputLine> output = new ArrayList<>()

    GameState state

    GameInstance(GameState state) {
        this.state = state
    }

    GameInstance run() {
        while (!eventQueue.isEmpty()) {
            GameEvent event = eventQueue.poll()
            event.execute(this)
            eventLog.add(event)
            output.addAll(event.output)
        }
        return this
    }

    void doNext(GameEvent event) {
        eventQueue.add(event)
    }

    List<OutputLine> getOutput() {
        return output
    }
}
