package com.example.somewebfluxtheory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.scheduler.Schedulers;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
public class Example2 {
    public static void main(String[] args) {
        Example2 example2 = new Example2();
        example2.example2();
    }
    @GetMapping("/example2")
    public void example2(){
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
                .publishOn(Schedulers.boundedElastic())
                .subscribe();
        log.info("Завершено");

    }
}
