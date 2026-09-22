package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;


/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary

    Используйте для этого словарь русских слов, состоящих из пяти букв.
 */
public class WordleDictionaryLoader {

    private PrintWriter logFile;

    public WordleDictionaryLoader(PrintWriter logFile) {
        this.logFile = logFile;
    }

    public WordleDictionary  addWordFromFile(String nameFile, int lengthWord) {
        WordleDictionary dictionary = new WordleDictionary(lengthWord);

        try (FileReader fileReader = new FileReader(nameFile, StandardCharsets.UTF_8)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() == lengthWord) dictionary.addWord(line);
            }
            logFile.println("Словарь для игры загружен");
        } catch (IOException e) {
            System.err.println("Не удалось загрузить словарь: " + e.getMessage());
        }

        return dictionary;
    }
}
