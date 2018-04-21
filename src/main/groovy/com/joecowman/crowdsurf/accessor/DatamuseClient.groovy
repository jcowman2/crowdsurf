package com.joecowman.crowdsurf.accessor

import com.joecowman.crowdsurf.accessor.model.DmWord
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "Datamuse", url = "http://api.datamuse.com")
interface DatamuseClient {

    @GetMapping("/words?ml={word}&md=fps")
    List<DmWord> similarMeaningMetadata(@RequestParam("word") String word)

    @GetMapping("/words?rel_rhy={word}")
    List<DmWord> rhymes(@RequestParam("word") String word)

    @GetMapping("/words?ml={word}&topics={topics}&max={max}")
    List<DmWord> similar(@RequestParam("word") String word, @RequestParam("topics") String topics, @RequestParam("max") int max)

}