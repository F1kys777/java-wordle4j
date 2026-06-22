package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public class WordleDictionary {
    private List<String> words;
    private String randomWord;
    private final PrintWriter log;

    public WordleDictionary(List<String> words, PrintWriter log) {
        this.words = words;
        this.log = log;
    }

    public List<String> getWords() {
        return words;
    }

    public String getRandomWord() throws RuntimeException {
        Random random = new Random();
        if (words == null || words.isEmpty()) {
            throw new RuntimeException("Словарь пуст.");
        }
        int x = random.nextInt(words.size());
        randomWord = words.get(x);
        if (randomWord == null) {
            throw new RuntimeException("Ошибка: слово не было загадано.");
        }
        if (log != null) {
            log.println("Загадано слово: " + randomWord);
        }
        return randomWord;
    }

    public String charChecker(String answer) {
        if (answer == null || answer.length() != 5) {
            throw new RuntimeException("Ошибка: аргумент должен быть строкой из 5 символов.");
        }
        boolean[] rightPlace = new boolean[5];
        char[] charAnswer = new char[5];

        if (randomWord == null) {
            throw new RuntimeException("Слово не загадано.");
        }

        for(int i = 0; i < 5; i++) {
            if(randomWord.charAt(i) == answer.charAt(i)) {
                rightPlace[i] = true;
                charAnswer[i] = '+';
            }
        }

        for (int i = 0; i < 5; i++) {
            if (charAnswer[i] == '+') continue;
            boolean found = false;
            for (int j = 0; j < 5; j++) {
                if (!rightPlace[j] && answer.charAt(i) == randomWord.charAt(j)) {
                    charAnswer[i] = '^';
                    rightPlace[j] = true; //ПРОВЕРИТЬ ВСЮ ЛОГИКУ ПРОВЕРКИ, КАК БУДТО ОНО НЕ НУЖНО ВООБЩЕ ТУТ,УБРАТЬ МБ ВООБЩЕ
                    found = true;
                    break;
                }
            }
            if (!found) { //ПРОВЕРИТЬ ВСЮ ЛОГИКУ ПРОВЕРКИ, ДОБАВИТЬ ЛОГИКУ ПРОВЕРКИ ЧТО НЕ НАЙДЕНО И НЕ ВЕРНОЕ МЕСТО
                charAnswer[i] = '-';
            }
        }

        String result = new String(charAnswer);

        if (log != null) {
            log.println("Сравнение: " + answer + " -> " + result);
        }

        return result;
    }
}

