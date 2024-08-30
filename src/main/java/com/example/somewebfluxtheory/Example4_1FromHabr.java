package com.example.somewebfluxtheory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@Slf4j
public class Example4_1FromHabr {
    @GetMapping("/example43")
    public void coldPublisherExample() throws InterruptedException {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
        Thread.sleep(2000);
        intervalFlux.subscribe(i -> log.info(String.format("Subscriber A, value: %d", i)));
        Thread.sleep(2000);
        intervalFlux.subscribe(i -> log.info(String.format("Subscriber B, value: %d", i)));
    }

    @GetMapping("/example44")
    public void hotPublisherExample() throws InterruptedException {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
        ConnectableFlux<Long> intervalCF = intervalFlux.publish();
        intervalCF.connect();
        Thread.sleep(2000);
        intervalCF.subscribe(i -> log.info(String.format("Subscriber A, value: %d", i)));
        Thread.sleep(2000);
        intervalCF.subscribe(i -> log.info(String.format("Subscriber B, value: %d", i)));
    }
}
