package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance

class NewGameEvent extends GameEvent {
    String bandName = "Groove Melon"
    int numSongs = 3
    List<String> songOptions

    private List<String> chosenSongs = []

    @Override
    protected void onExecute(GameInstance game) {
        Collections.shuffle(songOptions)
        numSongs.times {
            chosenSongs.add(songOptions.pop())
        }

        game.state.bandName = bandName
        game.state.songs = chosenSongs
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        output.addAll([
                OutputLine.normal("This was supposed to be the biggest night of your life."),
                OutputLine.normal(""),
                OutputLine.normal("After years of playing in leaky basements and birthday parties, your band \"$bandName\" is opening for a popular music festival."),
                OutputLine.normal("You and your bandmates have been practicing every night for months in anticipation of tonight."),
                OutputLine.normal("You were sure that this would finally be your big break."),
                OutputLine.normal(""),
                OutputLine.normal("...until a few minutes ago, when your drummer surprised you with a ride cymbal to the back of your head."),
                OutputLine.normal("That's the last time you'll let Spider get hopped up on taquitos and Diet Sprite before a show."),
                OutputLine.normal(""),
                OutputLine.normal("You're still feeling a bit foggy, but the crowd is already screaming for you to go onstage."),
                OutputLine.debug("Type \"start\" to begin the set.")
        ])

        return output
    }
}
