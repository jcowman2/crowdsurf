package com.joecowman.crowdsurf.game.util

import com.joecowman.crowdsurf.game.model.ContextWord

class StrUtil {

    static List<String> vowels = ['a', 'e', 'i', 'o', 'u']

    //Adds suffix if value not 1
    static String pl(int i, String suffix = 's') {
        return (i == 1) ? '' : suffix
    }

    //Returns number with positive sign if positive (zero is positive)
    static String signed(int i) {
        return "${(i < 0) ? '' : '+'}$i"
    }

    //Adds the appropriate 'a' or 'an' to a word
    static String addArticle(String word) {
        boolean startsWithVowel = vowels.contains(word.charAt(0).toString())
        String article = startsWithVowel ? 'an' : 'a'
        return "$article $word"
    }

    //Prints a nicely formatted sequence
    static String formatTopics(List<ContextWord> topics) {
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
