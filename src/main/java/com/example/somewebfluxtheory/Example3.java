package com.example.somewebfluxtheory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@Slf4j
public class Example3 {
    public static void main(String[] args) throws InterruptedException {
        Example3 example3 = new Example3();
//        example3.example31();
//        example3.example32();
        example3.example33();
    }

    @GetMapping("/example31")
    public void example31() {
        Flux.just("Раз", "Два", "Три")
                .subscribeOn(Schedulers.single())
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
                .blockFirst();
        log.info("Завершено");
    }

    @GetMapping("/example32")
    public void example32() {
        Flux.just("Раз", "Два", "Три")
                .subscribeOn(Schedulers.single())
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
                .blockLast();
        log.info("Завершено");
    }

    @GetMapping("/example33")
    public void example33() throws InterruptedException {
        Flux.just("Раз", "Два", "Три")
                .subscribeOn(Schedulers.single())
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
                .collectList().doOnNext(n->n.forEach(log::info)).subscribe();
        log.info("Завершено");
        Thread.sleep(10000);
    }
}
