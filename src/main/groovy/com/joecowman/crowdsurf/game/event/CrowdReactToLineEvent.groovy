package com.joecowman.crowdsurf.game.event

import com.joecowman.crowdsurf.api.model.OutputLine
import com.joecowman.crowdsurf.game.model.GameInstance
import com.joecowman.crowdsurf.game.model.GameState

class CrowdReactToLineEvent extends GameEvent {
    int lineScore

    private int hypeChange
    private int newHype
    
    @Override
    protected void onExecute(GameInstance game) {
        GameState state = game.state
        
        int currentHype = state.crowdHype
        hypeChange = lineScore
        
//        TODO scaled hyping
//        int scoreRemaining = lineScore
//        boolean scorePositive = (lineScore > 0)
//        
//        while (scoreRemaining > 0) {
//            if (currentHype <= 15) {
//                if (scorePositive) {
//                    
//                }
//            }
//        }
        
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
        if (hypeChange >= 10) {
            comment = "That really blew them away!"
        } else if (hypeChange <= -10) {
            comment = "That didn't go over well..."
        }
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

        if (comment == "") {
            Random rand = new Random()
            if (rand.nextInt(2)) {
                comment = comments[rand.nextInt(comments.size())]
            }
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
