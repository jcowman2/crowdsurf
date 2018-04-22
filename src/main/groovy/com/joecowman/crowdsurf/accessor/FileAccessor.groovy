package com.joecowman.crowdsurf.accessor

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class FileAccessor {

    static Resource topicsFile

    @Value("classpath:topics")
    void setTopicsFile(Resource r) {
        topicsFile = r
    }

    //Credit: https://codereview.stackexchange.com/questions/16154/is-this-code-an-efficient-implementation-of-reservoir-sampling
    static List<String> getRandomLines(Resource file, int num) {
        String line = -1
        int count = 0
        int randomNumber = 0

        Random random = new Random()
        List<String> lines = new ArrayList<>()

        new Scanner(file.inputStream).withCloseable { sc ->
            while (sc.hasNext()) {
                line = sc.next()
                count++

                if (count <= num) {
                    lines.add(line)
                } else if ((randomNumber = (int) random.nextInt(count)) < num) {
                    lines.set(randomNumber, line)
                }
            }
        }

        return lines
    }

    static List<String> getRandomTopics(int num) {
        return getRandomLines(topicsFile, num)
    }

}
