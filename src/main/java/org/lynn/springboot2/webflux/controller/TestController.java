package org.lynn.springboot2.webflux.controller;

import java.util.stream.IntStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;

/**
 * @author tangxinyi@Ctrip.com
 * @date 2018/6/12 14:02
 */
@RestController
public class TestController {
  @GetMapping("/name")
  public Mono<String> getName(){
    return Mono.just("Lynn");
  }

  @GetMapping(value = "/age", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Integer> getAge(){
    return Flux.fromStream(IntStream.range(1, 18).mapToObj(i -> i));
  }
}
