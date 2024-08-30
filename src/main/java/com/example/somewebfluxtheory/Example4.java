package com.example.somewebfluxtheory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
public class Example4 {
    public static void main(String[] args) throws InterruptedException {
        Example4 example4 = new Example4();
        example4.example41();
    }

    @GetMapping("/example41")
    public void example41() throws InterruptedException {
        Flux<String> publisher = Flux.just("Раз", "Два", "Три");

        publisher.subscribeOn(Schedulers.single())
                .doOnSubscribe(subscription -> log.info("Подписка создана"))
                .doOnNext(element -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("Элемент: " + element);
                })
                .doOnError(ex -> log.info("Ошибка:" + ex.getMessage()))
                .doOnComplete(() -> log.info("Обработка завершена"))
                .publishOn(Schedulers.boundedElastic())
                .subscribe();
        publisher.subscribeOn(Schedulers.boundedElastic())
                .doOnSubscribe(subscription -> log.info("Подписка создана"))
                .doOnNext(element -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("Элемент: " + element);
                })
                .doOnError(ex -> log.info("Ошибка:" + ex.getMessage()))
                .doOnComplete(() -> log.info("Обработка завершена"))
                .publishOn(Schedulers.boundedElastic())
                .subscribe();
    }

    @GetMapping("/example42")
    public void example42() throws InterruptedException {
        Flux<String> publisher = Flux.just("Раз", "Два", "Три");

        ConnectableFlux<String> hotStream = publisher
                .subscribeOn(Schedulers.single())
                .doOnSubscribe(subscription -> log.info("Подписка создана"))
                .doOnNext(element -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnError(ex -> log.info("Ошибка:" + ex.getMessage()))
                .doOnComplete(() -> log.info("Обработка завершена"))
                .publish();

        hotStream
                .publishOn(Schedulers.boundedElastic())
                .subscribe(subscriber1 -> log.info("Subscriber 1: " + subscriber1));
        hotStream.connect();
        Thread.sleep(1000);
        hotStream
                .publishOn(Schedulers.boundedElastic())
                .subscribe(subscriber2 -> log.info("Subscriber 2: " + subscriber2));
    }
}
