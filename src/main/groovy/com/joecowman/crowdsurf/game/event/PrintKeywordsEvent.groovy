package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

import static com.joecowman.crowdsurf.game.util.StrUtil.formatTopics

class PrintKeywordsEvent extends GameEvent {

    private String keywords

    @Override
    protected void onExecute(GameInstance game) {
        keywords = formatTopics(game.state.currentSong.contextWords)
        game.enqueue(new SongDurationEvent())
    }

    @Override
    protected List<OutputLine> generateOutput() {
        return [OutputLine.normal("You're trying to sing lyrics about $keywords.")]
    }

}
