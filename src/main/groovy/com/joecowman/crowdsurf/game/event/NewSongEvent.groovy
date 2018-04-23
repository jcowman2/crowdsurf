package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.accessor.FileAccessor
import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.ContextWord
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.Song

import static com.joecowman.crowdsurf.game.util.StrUtil.addArticle

class NewSongEvent extends GameEvent {
    int numTopics = 3

    private Song song

    @Override
    protected void onExecute(GameInstance game) {
        List<ContextWord> topics = FileAccessor.getRandomTopics(numTopics).collect { new ContextWord(it) }
        song = new Song(contextWords: topics)
        game.state.currentSong = song
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output << OutputLine.normal("The band starts to play a song, but you can't remember the lyrics.")
        output << OutputLine.normal("You think it has something to do with ${formatTopics(song.contextWords)}.")
        output << OutputLine.normal("Better make up something quick!")
    }

    private String formatTopics(List<ContextWord> topics) {
        StringBuilder sb = new StringBuilder()
        int lastIdx = topics.size() - 1

        topics.eachWithIndex{ ContextWord topic, int i ->
            String word = addArticle(topic.word)
            if (i != lastIdx) {
                sb.append(word).append(", ")
            } else {
                sb.append("and ").append(word)
            }
        }

        return sb.toString()
    }
}
