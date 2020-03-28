package org.geekhub.crypto.coders;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.util.AbstractMap.SimpleEntry;

@Repository
public class Dictionary {
    private static final Map<String, String> ENGLISH_UKRAINIAN_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, String> UKRAINIAN_ENGLISH_CACHE = new ConcurrentHashMap<>();
    private static final Queue<String> UKRAINIAN_INPUTS = new ConcurrentLinkedQueue<>();
    private static final Queue<String> ENGLISH_INPUTS = new ConcurrentLinkedQueue<>();

    public Dictionary() {
        initialiseExecutor();
    }

    public List<String> getEnglish(String... input) {

        UKRAINIAN_INPUTS.addAll(Arrays.asList(input));

        while (UKRAINIAN_INPUTS.size() >= 1000) {
            UKRAINIAN_INPUTS.poll();
        }

        return Arrays.stream(input).map(UKRAINIAN_ENGLISH_CACHE::get)
                .collect(Collectors.toList());
    }

    public List<String> getUkrainian(String... input) {
        try {
            ENGLISH_INPUTS.addAll(Arrays.asList(input));

            while (ENGLISH_INPUTS.size() >= 1000) {
                ENGLISH_INPUTS.poll();
            }

            return Arrays.stream(input).map(ENGLISH_UKRAINIAN_CACHE::get)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.getMessage();
            throw e;
        }
    }

    private void initialiseExecutor() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        scheduledExecutorService.scheduleAtFixedRate(this::addToEnglishUkrainianCache, 0, 1, TimeUnit.HOURS);
        scheduledExecutorService.scheduleAtFixedRate(this::addToUkrainianEnglishCache, 0, 1, TimeUnit.HOURS);
    }

    private synchronized void addToEnglishUkrainianCache() {

        ENGLISH_UKRAINIAN_CACHE.putAll(ENGLISH_INPUTS.stream().distinct()
                .map(word -> new SimpleEntry<>(word, TranslateApi.englishToUkrainian(word)))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }


    private synchronized void addToUkrainianEnglishCache() {
        UKRAINIAN_ENGLISH_CACHE.putAll(UKRAINIAN_INPUTS.stream().distinct()
                .map(word -> new SimpleEntry<>(word, TranslateApi.ukrainianToEnglish(word)))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }
}
