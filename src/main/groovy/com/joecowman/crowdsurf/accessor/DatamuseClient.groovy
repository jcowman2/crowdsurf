package com.joecowman.crowdsurf.accessor

import com.joecowman.crowdsurf.model.DmWord
import feign.Param
import feign.RequestLine
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "Datamuse", url = "http://api.datamuse.com")
interface DatamuseClient {

    @GetMapping("/words?ml={word}&md=fps")
    List<DmWord> similarMeaning(@RequestParam("word") String word)

}