package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.accessor.FileAccessor
import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.Constants
import com.joecowman.crowdsurf.game.model.ContextWord
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.Song

import static com.joecowman.crowdsurf.game.util.StrUtil.formatTopics

class NewSongEvent extends GameEvent {
    int numTopics = 3
    int songDuration = 90

    private Song song
    private int crowdHype

    @Override
    protected void onExecute(GameInstance game) {
        List<ContextWord> topics = FileAccessor.getRandomTopics(numTopics).collect { new ContextWord(it) }
        song = new Song(contextWords: topics, duration: songDuration)

        game.state.currentSong = song
        game.state.resetTimestamp()
        String filename = game.state.songs[game.state.songNumber]
        game.state.songNumber++

        crowdHype = game.state.crowdHype
        game.doNext(new SongDurationEvent())
        game.enqueue(new ClientActionRequiredEvent(action: Constants.START_SONG, args: ['filename' : filename ]))
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output << OutputLine.normal("The band starts to play a song, but you can't remember the lyrics.")
        output << OutputLine.normal("You think it has something to do with the words ${formatTopics(song.contextWords)}")
        output << OutputLine.normal("Crowd hype is at $crowdHype%. Better make up something quick!")
    }


}
