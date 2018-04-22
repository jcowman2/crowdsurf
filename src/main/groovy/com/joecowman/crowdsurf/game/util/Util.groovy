package com.joecowman.crowdsurf.game.util

class Util {

    //Adds s if value not 1
    static String pl(int i) {
        return (i == 1) ? '' : 's'
    }

    //Returns number with positive sign if positive (zero is positive)
    static String signed(int i) {
        return "${(i < 0) ? '' : '+'}$i"
    }
}
