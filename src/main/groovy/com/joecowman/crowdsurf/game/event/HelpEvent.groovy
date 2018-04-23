package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

class HelpEvent extends GameEvent {

    @Override
    protected void onExecute(GameInstance game) {
        //doNothing
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output.addAll(
                OutputLine.normal("Crowdsurf is a game about improvisation."),
                OutputLine.normal("You play a band's lead singer who has forgotten the lyrics to his songs during a concert."),
                OutputLine.normal("It's your job to come up with new lyrics that match the original theme of your songs."),
                OutputLine.normal(""),
                OutputLine.normal("The game consists of 3 songs. At the start of each song, you'll be given a short list of keywords."),
                OutputLine.normal("You're scored based on how well your lyrics match these keywords. You can also earn points for having clever rhymes."),
                OutputLine.normal(""),
                OutputLine.normal("While a song is playing, you sing just by typing the text and pressing enter."),
                OutputLine.normal("You can also do any of these actions during a song by typing an asterisk (*) before the command."),
                OutputLine.normal("\t*key   - View the song's keywords."),
                OutputLine.normal("\t*stop  - End the song early.")
        )

        return output
    }

}
