package com.example.somewebfluxtheory;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class Example2_1IfWrongAnswer {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("Раз");
        words.add("Два");
        words.add("Три");

        CompletableFuture.runAsync(() -> words.forEach(word -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info(word);
        }));
        log.info("тест");
    }

}
