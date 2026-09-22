package ru.yandex.practicum;

public class WordNotFoundInDictionary extends Exception {

    public WordNotFoundInDictionary() {
        super();
    }

    public WordNotFoundInDictionary(String message) {
        super(message);
    }
}
