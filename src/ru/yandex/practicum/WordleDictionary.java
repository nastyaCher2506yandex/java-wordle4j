package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.

    программа проверяет, что слово соответствует правилам: состоит из пяти букв и присутствует в словаре.
 */
public class WordleDictionary {

    private List<String> words;
    private final int lengthWord;

    WordleDictionary(int lengthWord) {
        this.lengthWord = lengthWord;
        words = new ArrayList<>();
    }

    //метод добавляет слово в словарь
    public boolean addWord(String word) {
        //слово должно соответствовать фиксированному размеру игры
        if (word.length() != lengthWord) {
            //если нет, то создаем исключение
            throw new IllegalStateException("Количество букв в слове меньше или больше " + lengthWord);
        }

        //приводим к общему виду и добавляем слово
        return words.add(normalizeText(word));
    }

    //проверка есть ли слово в словаре
    public boolean containsWord(String word) {
        return words.contains(word);
    }

    //получение слова по индексу
    public String getWord(int index) {
        return words.get(index);
    }

    //метод возвращает измененое слово, которое соответствует требованию:
    //нижний регистр и ё = е
    public String normalizeText(String text) {

        StringBuilder result = new StringBuilder(text);

        //замена буквы на ё на е (в игре они равнозначны)
        int index = result.indexOf("ё");

        while (index != -1) {
            result.setCharAt(index,'e');

            index = result.indexOf("ё");
        }

        //помимо этого все слова приводим к нижнему регистру
        return result.toString().toLowerCase().trim();
    }

    //сравнивает слова и передает строку вида
    //- — им отмечается буква, которой НЕТ в загаданном слове;
    //+ — этим символом отмечается буква, которая ЕСТЬ в загаданном слове и находится на правильной позиции;
    //^ — так отмечается буква, которая ЕСТЬ в загаданном слове, но находится в другом месте.
    public static String wordsMatch(String word, String answer) {
        if (answer.equals(word)) return "+++++";

        StringBuilder checkAnswer = new StringBuilder(answer);
        StringBuilder checkWord = new StringBuilder(word);

        //первая проверка смотрит на буквы, которые стоят на своих местах
        for (int i = 0; i < word.length(); i++) {
            if (checkWord.charAt(i) == checkAnswer.charAt(i)) {
                checkAnswer.setCharAt(i,'_');
                checkWord.setCharAt(i,'+');
            }
        }

        //вторая проверка смотрит есть ли в слове буквы, которые остались в ответе
        for (int i = 0; i < word.length(); i++) {
            if (checkWord.charAt(i) == '+') continue;

            int indexSearch = checkAnswer.indexOf(String.valueOf(checkWord.charAt(i)));

            if (indexSearch > -1) {
                checkWord.setCharAt(i,'^');
                checkAnswer.setCharAt(indexSearch,'_');
            } else {
                checkWord.setCharAt(i,'-');
            }
        }

        return checkWord.toString();
    }

    public int getWordsCount() {
        return words.size();
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WordleDictionary that = (WordleDictionary) o;
        return Objects.equals(words, that.words);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(words);
    }
}
