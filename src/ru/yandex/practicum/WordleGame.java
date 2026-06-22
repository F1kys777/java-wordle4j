package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {

    private String answer;
    private int steps = 6;
    private int attempt = 0;
    private WordleDictionary dictionary;
    Scanner scan;
    Map<Integer, Character> rightPosition = new HashMap<>();
    Set<Character> noExist = new HashSet<>();
    HashMap<Character, Integer> existChar = new HashMap<>();
    boolean isUserAnswerRight = false;
    List<String> guessHistory = new ArrayList<>();
    List<String> feedbackHistory = new ArrayList<>();
    private final PrintWriter log;
    private final Random random = new Random();

    public WordleGame(WordleDictionary dictionary, Scanner scanner, PrintWriter log) {
        this.dictionary = dictionary;
        this.scan = scanner;
        this.log = log;
    }

    public void gameStarted() {
        answer = dictionary.getRandomWord();
        if (answer == null) {
            throw new RuntimeException("Не удалось загадать слово: ответ null.");
        }
        log.println("Игра начата. Загаданное слово: " + answer);
    }

    public boolean isValidWord(String word) {
        word = normalizationAnswer(word);
        if (word.length() != 5 || !dictionary.getWords().contains(word)) {
            log.println("Ошибка: Некорректное слово '" + word + "'");
            return false;
        }
        return true;
    }

    public String makeStep(String userAnswer) {
        userAnswer = normalizationAnswer(userAnswer);
        guessHistory.add(userAnswer);
        String check = dictionary.charChecker(userAnswer);
        if (check == null || check.length() != 5) {
            throw new RuntimeException("Некорректный результат сравнения: " + check);
        }
        feedbackHistory.add(check);
        checkAnswer(check, userAnswer);
        attempt = attempt + 1;
        isUserAnswerRight(check);

        if (log != null) {
            log.println("Ход: " + userAnswer + " -> " + check + ", осталось попыток: " + (getAttemptCount()));
        }

        return check;
    }
    private boolean isUserAnswerRight(String check) {
        if (check.equals("+++++")) {
            isUserAnswerRight = true;
            log.println("Верный ответ!\nЗагаданное слов - " + answer);
            return true;
        }
        return false;
    }

    public boolean isGameEnd() {
        if(steps == attempt) {
            return true;
        } else if(isUserAnswerRight) {
            return true;
        }
        return false;
    }

    public int getAttemptCount() {
        return steps - attempt;
    }

    public void checkAnswer(String check, String userAnswer) {
        Map<Character, Integer> currentCount = new HashMap<>();

        for (int j = 0; j < check.length(); j++) {
            if (check.charAt(j) == '+') {
                rightPosition.put(j, userAnswer.charAt(j));
                currentCount.put(userAnswer.charAt(j), currentCount.getOrDefault(userAnswer.charAt(j), 0) + 1);
            } else if (check.charAt(j) == '^') {
                currentCount.put(userAnswer.charAt(j), currentCount.getOrDefault(userAnswer.charAt(j), 0) + 1);
            } else {
                if (!existChar.containsKey(userAnswer.charAt(j)) && !rightPosition.containsValue(userAnswer.charAt(j))) {
                    noExist.add(userAnswer.charAt(j));
                }
            }
        }

        for (Map.Entry<Character, Integer> entry : currentCount.entrySet()) {
            char simbol = entry.getKey();
            int countInCurrent = entry.getValue();
            int oldCount = existChar.getOrDefault(simbol, 0);
            if (countInCurrent > oldCount) {
                existChar.put(simbol, countInCurrent);
            }
        }

        if (log != null) {
            log.println("Точная позиция: " + rightPosition + ", Нет вообще: " + noExist + ", Есть в слове: " + existChar);
        }
    }

    public String normalizationAnswer(String userAnswer) {
        String normalizationAnswer = userAnswer.toLowerCase().trim().replace('ё', 'е');
        return normalizationAnswer;
    }

    public String getHelp() throws RuntimeException {

        if (dictionary.getWords() == null) {
            throw new RuntimeException("Словарь не инициализирован.");
        }

        List<String> help = new ArrayList<>(dictionary.getWords());

        for (char c : noExist) {
            List<String> filtered = new ArrayList<>();
            for (String word : help) {
                if (!word.contains(String.valueOf(c))) {
                    filtered.add(word);
                }
            }
            help = filtered;
        }

        for (Map.Entry<Character, Integer> entry : existChar.entrySet()) {
            char c = entry.getKey();
            int required = entry.getValue();

            List<String> filtered = new ArrayList<>();
            for (String word : help) {
                int count = 0;
                for (char ch : word.toCharArray()) {
                    if (ch == c) count++;
                }
                if (count >= required) {
                    filtered.add(word);
                }
            }
            help = filtered;
        }

        for (Map.Entry<Integer, Character> entry : rightPosition.entrySet()) {
            int pos = entry.getKey();
            char c = entry.getValue();

            List<String> filtered = new ArrayList<>();
            for (String word : help) {
                if (word.charAt(pos) == c) {
                    filtered.add(word);
                }
            }
            help = filtered;
        }

        help.removeAll(guessHistory);

        String clue;

        if (help.isEmpty()) {
            List<String> allWords = dictionary.getWords();
            clue = allWords.get(random.nextInt(allWords.size()));
        } else {
            clue = help.get(random.nextInt(help.size()));
        }

        if (log != null) {
            log.println("Подсказка выведенная пользователю: " + clue);
        }

        return clue;
    }

}
