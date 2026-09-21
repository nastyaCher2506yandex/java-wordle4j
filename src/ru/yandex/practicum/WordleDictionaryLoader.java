package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary

    Используйте для этого словарь русских слов, состоящих из пяти букв.
 */
public class WordleDictionaryLoader {

    public WordleDictionary  addWordFromFile(String nameFile, int lengthWord) {
        WordleDictionary dictionary = new WordleDictionary(lengthWord);

        try (FileReader fileReader = new FileReader(nameFile, StandardCharsets.UTF_8)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();

                if (line.length() == lengthWord) dictionary.addWord(line);
            }
        } catch (IOException e) {
            System.err.println("Не удалось загрузить словарь: " + e.getMessage());
        }

        return dictionary;
    }
}
