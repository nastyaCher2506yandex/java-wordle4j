package ru.yandex.practicum;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок

Игроку доступно шесть попыток.


 */
public class WordleGame {


    private String answer;

    private int steps;

    private WordleDictionary dictionary;

    private int lengthWord;

    Random random = new Random();

    //если позиция известна позиция и символ (т.к. позиция в слове одна, а символом много ключ int
    LinkedHashMap<Integer,Character> knowPosition;
    //если позиция неизвестна то количество и символ
    Set<Character> haveLetter;

    WordleGame(String answer, int lengthWord) {
        this.answer = answer;
        steps = 0;
        dictionary = new WordleDictionary(lengthWord);
        this.lengthWord = lengthWord;

        knowPosition = new LinkedHashMap<>();
        haveLetter = new HashSet<>();
    }

    public String makeTurn(String word, WordleDictionary dictionaryAll) throws WordNotFoundInDictionary, WordEmptyString {
        if (word.isBlank()) throw new WordEmptyString("Передали пустую строку");
        if (word.length() != lengthWord) throw new IllegalStateException("Количество букв в слове меньше или больше " + lengthWord);
        if (!dictionaryAll.containsWord(word)) throw new WordNotFoundInDictionary("В словаре нет слова " + word);

        dictionary.addWord(word);
        steps++;

        String result = dictionary.wordsMatch(word,answer);
        getPosition(word,result);

        return result;
    }

    public void getPosition(String word, String resultCheck) {
        for (int i = 0; i < word.length(); i++) {
            if (resultCheck.charAt(i) == '+') {
                knowPosition.put(i, word.charAt(i));
                if (haveLetter.contains(word.charAt(i))) {
                    haveLetter.remove(word.charAt(i));
                }
            } else if (resultCheck.charAt(i) == '^') {
                haveLetter.add(word.charAt(i));
            }
        }
    }

    public boolean isWordPosition(String word) {
        for (Map.Entry<Integer,Character> entry : knowPosition.entrySet()) {
            if (!(word.charAt(entry.getKey()) == entry.getValue())) {
                return false;
            }
        }

        for (char character : haveLetter) {
            if (word.indexOf(character) == -1) {
                return false;
            }
        }

        return true;
    }

    //получаем рандомную букву, позицию которой еще не нашли
    public void getNewCharacter() {
        int index = -1;
        if (knowPosition.isEmpty()) {
            index = random.nextInt(answer.length());
        } else {
            for (int i = 0; i < answer.length(); i++) {
                if (knowPosition.containsKey(i)) continue;

                index = i;
                break;
            }
        }

        haveLetter.add(answer.charAt(index));
    }

    public String getHint(WordleDictionary dictionaryAll) {
        ArrayList<String> words = new ArrayList<>();

        for (String word : dictionaryAll.getWords()) {
            if (dictionary.getWords().contains(word)) continue;

            //если нет букв, которые есть в слове, но не на своей позиции
            //то берем любую букву из слова
            if (haveLetter.isEmpty()) getNewCharacter();

            //проверяем слово, что в нем есть эта буква и слова на своих позициях
            if (isWordPosition(word)) words.add(word);
        }

        if (words.size() > 0) {
            return words.get(random.nextInt(words.size()));
        }

        return dictionaryAll.getWord(random.nextInt(dictionaryAll.getWordsCount()));
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
