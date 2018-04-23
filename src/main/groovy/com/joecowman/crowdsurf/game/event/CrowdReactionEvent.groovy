package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.GameState
import com.joecowman.crowdsurf.game.model.ReactionTrigger

class CrowdReactionEvent extends GameEvent {
    ReactionTrigger trigger
    int score

    CrowdReactionEvent(ReactionTrigger trigger, int score = 0) {
        this.trigger = trigger
        this.score = score
    }

    private int hypeChange
    private int newHype
    
    @Override
    protected void onExecute(GameInstance game) {
        GameState state = game.state
        
        int currentHype = state.crowdHype
        hypeChange = score
        
        newHype = currentHype + hypeChange
        if (newHype < 0) {
            int diff = 0 - newHype
            newHype += diff
            hypeChange += diff
        } else if (newHype > 100) {
            int diff = newHype - 100
            newHype -= diff
            hypeChange -= diff
        }
        
        state.crowdHype = newHype
    }

    @Override
    protected List<OutputLine> generateOutput() {
        List<OutputLine> output = []

        String comment = ""
        List<String> comments = []

        if (!comment) {
            if (hypeChange >= 10) {
                comment = "That really blew them away!"
            } else if (hypeChange <= -10) {
                comment = "That didn't go over well..."
            }
        }

        if (!comment) {
            if (newHype <= 15) {
                comments += [
                        "The crowd looks like they're out for blood!",
                        "The crowd is about to storm the stage!",
                        "Anguished wails come from the crowd.",
                        "The band is buffeted with thrown debris!"
                ]
            } else if (newHype <= 30) {
                comments += [
                        "People in the crowd are booing.",
                        "A steady stream of people are leaving the venue.",
                        "This is a disaster!",
                        "Nobody in the crowd is dancing or smiling."
                ]
            } else if (newHype <= 50) {
                comments += [
                        "The crowd isn't doing much.",
                        "The crowd can't tell if they like the song or not.",
                        "The crowd is not impressed.",
                        "Some devoted fans in the front are dancing."
                ]
            } else if (newHype <= 70) {
                comments += [
                        "The crowd is cheering loudly.",
                        "People in the crowd seem to be having fun.",
                        "The crowd is riled up!",
                        "Lots of people are dancing and singing."
                ]
            } else if (newHype <= 85) {
                comments += [
                        "The crowd is screaming!",
                        "The noise from the crowd is deafening!",
                        "Everyone is on their feet!",
                        "You can see many people filming you."
                ]
            } else {
                comments += [
                        "The energy is just insane!",
                        "You might bring down the house!",
                        "You've never seen anything like this!",
                        "This is the best show ever performed!"
                ]
            }

            Random rand = new Random()
            if (rand.nextInt(2)) {
                comment = comments[rand.nextInt(comments.size())]
            }
        }

        if (!comment && trigger == ReactionTrigger.TIMEOUT && newHype <= 60) {
            comment = "The crowd is restless!"
        }

        String changed = ""
        if (hypeChange < 0) {
            changed = "decreased to"
        } else if (hypeChange == 0) {
            changed = "remained at"
        } else {
            changed = "increased to"
        }

        output << OutputLine.normal("Crowd hype $changed $newHype%. $comment")

        return output
    }
}
