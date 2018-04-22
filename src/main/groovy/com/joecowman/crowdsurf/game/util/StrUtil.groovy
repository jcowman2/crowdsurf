package com.joecowman.crowdsurf.game.util

class StrUtil {

    static List<Character> vowels = ['a', 'e', 'i', 'o', 'u']

    //Adds s if value not 1
    static String pl(int i) {
        return (i == 1) ? '' : 's'
    }

    //Returns number with positive sign if positive (zero is positive)
    static String signed(int i) {
        return "${(i < 0) ? '' : '+'}$i"
    }

    //Adds the appropriate 'a' or 'an' to a word
    static String addArticle(String word) {
        boolean startsWithVowel = vowels.contains(word.charAt(0))
        String article = startsWithVowel ? 'an' : 'a'
        return "$article $word"
    }
}
