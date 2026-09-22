package ru.yandex.practicum;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат

    Дополнительно программа проверяет, что слово соответствует правилам:
    состоит из пяти букв и присутствует в словаре.
    Если слово корректное, ход засчитывается, иначе программа будет повторно
    ожидать ввод правильного слова, и ход засчитан не будет.

 */
public class Wordle {

    static final int MAX_SHOT = 6;
    static final int LENGTH_WORD = 5;
    static final String NAME_DICTIONARY_FILE = "words_ru.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        try (PrintWriter logFile = new PrintWriter(new File("log.txt"),StandardCharsets.UTF_8)) {
            //создание загрузчика словаря
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(logFile);


            //загрузка словаря с помощью WordleDictionaryLoader
            WordleDictionary wordleDictionary = wordleDictionaryLoader.addWordFromFile(NAME_DICTIONARY_FILE, LENGTH_WORD);

            //компьютер выбирает произвольный индекс слова из всего словаря
            int indexRandom = random.nextInt(wordleDictionary.getWordsCount());

            WordleGame wordleGame = new WordleGame(wordleDictionary.getWord(indexRandom),LENGTH_WORD,logFile);
            logFile.println("Игрок начал игру");

            while (wordleGame.getSteps() < MAX_SHOT) {
                try {
                    System.out.println("Введите слово:");
                    String word = scanner.nextLine();

                    if (word.isBlank()) {
                        String hint = wordleGame.getHint(wordleDictionary);
                        System.out.println("Подсказка: " + hint);
                        logFile.println("Слово подсказка - " + hint);
                        continue;
                    }

                    String turn = wordleGame.makeTurn(wordleDictionary.normalizeText(word), wordleDictionary);
                    System.out.println(turn);

                    if (turn.equals("+++++")) {
                        System.out.println("Вы выиграли!");
                        logFile.println("Игра завершилась. Игрок выиграл.");
                        break;
                    } else if (wordleGame.getSteps() == MAX_SHOT) {
                        System.out.println("Вы проиграли! Загаданное слово было " + wordleGame.getAnswer());
                        logFile.println("Игра завершилась. Игрок проиграл.");
                    } else {
                        logFile.println("Игрок не отгадал слово. Ввел слово " + word);
                    }
                } catch (WordNotFoundInDictionary e) {
                    logFile.println(e.getMessage());
                } catch (WordEmptyString e) {
                    logFile.println(e.getMessage());
                } catch (Exception e) {
                    logFile.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Невозможно создать log-файл " + e.getMessage());
        }
    }
}
