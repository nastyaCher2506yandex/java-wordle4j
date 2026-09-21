package ru.yandex.practicum;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        File log = new File("log.txt");

        try (Writer logFile = new FileWriter(log,StandardCharsets.UTF_8)){
            //создание загрузчика словаря
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader();

            //загрузка словаря с помощью WordleDictionaryLoader
            WordleDictionary wordleDictionary = wordleDictionaryLoader.addWordFromFile(NAME_DICTIONARY_FILE, LENGTH_WORD);

            //компьютер выбирает произвольный индекс слова из всего словаря
            int indexRandom = random.nextInt(wordleDictionary.getWordsCount());

            WordleGame wordleGame = new WordleGame(wordleDictionary.getWord(indexRandom),LENGTH_WORD);

            System.out.println(wordleDictionary.getWord(indexRandom));

            while (wordleGame.getSteps() < 6) {
                try {
                    System.out.println("Введите слово:");
                    String word = scanner.nextLine();

                    if (word.isBlank()) {
                        System.out.println("Подсказка");
                        word = wordleGame.getHint(wordleDictionary);
                    }

                    String turn = wordleGame.makeTurn(wordleDictionary.normalizeText(word), wordleDictionary);
                    System.out.println(turn);
                    System.out.println(word);

                    if (turn.equals("+++++")) {
                        System.out.println("Вы выиграли!");
                        break;
                    } else if (wordleGame.getSteps() == 6) {
                        System.out.println("Вы проиграли! Загаданное слово было " + wordleGame.getAnswer());
                    }
                } catch (WordNotFoundInDictionary e) {
                    logFile.write(e.getMessage() + "\n");
                } catch (WordEmptyString e ) {
                    logFile.write(e.getMessage() + "\n");
                } catch (Exception e) {
                    logFile.write(e.getMessage() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
