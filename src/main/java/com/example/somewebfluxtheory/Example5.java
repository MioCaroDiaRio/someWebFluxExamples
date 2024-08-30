package com.example.somewebfluxtheory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@RestController
public class Example5 {
    @GetMapping("/example51")
    public void example51() {
        Flux.just(1, 2, 3)
                .map(i -> {
                    if (i == 2) {
                        throw new RuntimeException("Ошибка");
                    }
                    return i;
                })
                .onErrorResume(e -> {
                    log.info("Ошибка: " + e.getMessage());
                    return Flux.just(4, 5);
                })
                .subscribe(System.out::println);
    }

    @GetMapping("/example52")
    public void example52() {
        Flux.just(1, 2, 3)
                .map(i -> {
                    if (i == 2) {
                        throw new RuntimeException("Ошибка");
                    }
                    return i;
                })
                .onErrorReturn(4)
                .subscribe(s -> log.info(String.valueOf(s)));
    }

    @GetMapping("/example53")
    public void example53() {
        Flux.just(1, 2, 3)
                .map(i -> {
                    if (i == 2) {
                        throw new RuntimeException("Ошибка");
                    }
                    return i;
                })
                .onErrorContinue((e, i) -> log.info("пропускаем " + i))
                .subscribe(s -> log.info(String.valueOf(s)));
    }

    @GetMapping("/example54")
    public void example54() {
        Flux.just(1, 2, 3)
                .map(i -> {
                    log.info(String.valueOf(i));
                    if (Math.random() < 0.90d) {
                        throw new RuntimeException(String.valueOf(i));
                    }
                    return i;
                })
                .doOnError(i -> log.info("ошибка в элементе " + i + " пытаемся повторить"))
                .retryWhen(Retry.backoff(5, Duration.ofMillis(100))
                        .filter(e -> e instanceof RuntimeException))
                .onErrorResume(e -> {
                    log.info("Повторные попытки не дали результата, сворачиваемся :)");
                    return Flux.empty();
                })
                .subscribe();
    }
}
