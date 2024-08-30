package com.example.somewebfluxtheory;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Example1 {
    public static void main(String[] args) {
        Flux.just("Раз", "Два", "Три").doOnSubscribe(
                subscription -> subscription.request(Long.MAX_VALUE))
                .doOnNext(element -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.info("Элемент: " + element);})
                .doOnError(ex -> log.info("Ошибка: " + ex.getMessage()))
                .doOnComplete(() -> log.info("Обработка завершена"))
                .subscribe();
        log.info("Завершено");
    }
}