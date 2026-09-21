package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    WordleDictionaryLoader wordleDictionaryLoader;
    WordleDictionary wordleDictionary;

    @BeforeEach
    void forTest() {
        wordleDictionaryLoader = new WordleDictionaryLoader();
        wordleDictionary = wordleDictionaryLoader.addWordFromFile("words_ru.txt",5);
    }

    //тест, если файла со словарем нет
    @Test
    void addWordFromFile_fileNotFound_throwsIOException() {
        try {
            String fileName = "noExist.txt";

            WordleDictionary wordleDictionaryTest = wordleDictionaryLoader.addWordFromFile(fileName,5);

            assertEquals(0, wordleDictionaryTest.getWordsCount());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //проверка на правильную подсказку позиций
    @Test
    void checkHintsAreValid() {
        String answer = "банан";
        String word = "нанка";

        String result = "";

        WordleGame wordleGame = new WordleGame(answer,5);
        try {
            result = wordleGame.makeTurn(word,wordleDictionary);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertEquals("^++-^", result);
    }

    //проверка когда количество слов меньше / больше
    @Test
    void checkHintsAreValidNotEquals() {
        String answer = "банан";
        String word1 = "бара";
        String word2 = "барабара";

        String result1 = "";
        String result2 = "";

        WordleGame wordleGame = new WordleGame(answer,5);
        try {
            //строка будет равно нулю, т.к. количество неправильно
            result1 = wordleGame.makeTurn(word1,wordleDictionary);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            result2 = wordleGame.makeTurn(word2,wordleDictionary);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        assertEquals(0, result1.length());
        assertEquals(0, result2.length());
    }

    //проверка когда передана пустая строка
    @Test
    void checkHintsAreValidEmptyWord() {
        String answer = "банан";
        String word = "";

        String result = "";

        WordleGame wordleGame = new WordleGame(answer,5);
        try {
            //строка будет равно нулю, т.к. количество неправильно
            result = wordleGame.makeTurn(word,wordleDictionary);
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        assertEquals(0, result.length());
    }

    //когда слово отгадано
    @Test
    void checkWinWordle() {
        String answer = "банан";
        String word = "банан";

        String result = "";

        WordleGame wordleGame = new WordleGame(answer,5);
        try {
            //строка будет равно нулю, т.к. количество неправильно
            result = wordleGame.makeTurn(word,wordleDictionary);
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        assertEquals("+++++", result);
    }

    //когда слово полностью несоответствует
    @Test
    void checkFullWrongWordle() {
        String answer = "банан";
        String word = "кофей";

        String result = "";

        WordleGame wordleGame = new WordleGame(answer,5);
        try {
            //строка будет равно нулю, т.к. количество неправильно
            result = wordleGame.makeTurn(word,wordleDictionary);
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        assertEquals("-----", result);
    }

    //проверка что в подсказке все отгаданые позиции на месте
    //а также буквы которые угадали, но позиции неизвестны
    @Test
    void checkHintsAreContainsWordPosition() {
        String answer = "банан";
        String word = "нанка";

        WordleGame wordleGame = new WordleGame(answer,5);

        String hints = "";

        String result = "";


        try {
            //строка будет равно нулю, т.к. количество неправильно
            result = wordleGame.makeTurn(word,wordleDictionary);
            hints = wordleGame.getHint(wordleDictionary);
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        assertTrue(wordleGame.isWordPosition(hints));
    }

    //проверка если случайно подсказка не будет соответствовать требованиями
    //сохрание отгаданных букв с известной и неизвестной позицией
    @Test
    void checkHintsAreNotContainsWordPosition() {
        String answer = "банан";
        String word = "нанка";

        WordleGame wordleGame = new WordleGame(answer,5);

        String hints = "базар";

        String result = "";


        try {
            //строка будет равно нулю, т.к. количество неправильно
            result = wordleGame.makeTurn(word,wordleDictionary);
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        assertFalse(wordleGame.isWordPosition(hints));
    }

}
