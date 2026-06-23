package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        try (PrintWriter logger = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream("game.log"), StandardCharsets.UTF_8))) {

            Scanner scanner = new Scanner(System.in);
            WordleDictionaryLoader loader = new WordleDictionaryLoader(logger);
            WordleDictionary dictionary;
            try {
                dictionary = loader.dictionaryFilter();
                if (dictionary == null) {
                    throw new RuntimeException("Словарь не загружен.");
                }
                logger.println("Словарь успешно загружен, слов: " + dictionary.getWords().size());
            } catch (IOException | EmptyDictionaryException e) {
                logger.println("Критическая ошибка: " + e.getMessage());
                return;
            }

            printMenu();

            while (true) {
                System.out.print("Выберите действие: ");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Введите число!");
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.println("Запускаем игру...");
                        WordleGame newGame = new WordleGame(dictionary, logger);
                        newGame.gameStarted();
                        System.out.println("Слово загадано. Введите слово или нажмите Enter для получения подсказки:");
                        while (!newGame.isGameEnd()) {
                            System.out.println("Попыток осталось - " + newGame.getAttemptCount());
                            String userAnswer = scanner.nextLine();
                            if (userAnswer.isEmpty()) {
                                System.out.println("Подсказка: " + newGame.getHelp());
                            } else if (!newGame.isValidWord(userAnswer)) {
                                System.out.println("Некорректное слово. Попробуйте снова.");
                            } else {
                                try {
                                    String check = newGame.makeStep(userAnswer);
                                    System.out.println(check);
                                } catch (InvalidWordLength | WordNotFoundInDictionary e) {
                                    System.out.println(e.getMessage());
                                    logger.println("Игровая ошибка: " + e.getMessage());
                                }
                            }
                        }
                        if (newGame.isUserAnswerRight){
                            System.out.println("Игра окончена! Вы победили!");
                        } else {
                            System.out.println("Игра окончена! К сожалению, вы проиграли.");
                        }
                        break;
                    case 2:
                        System.out.println("Выход из игры, до свидания!");
                        scanner.close();
                        logger.println("Программа завершена пользователем.");
                        return;
                    default:
                        System.out.println("Неверный выбор! Вы можете начать игру или выйти.");
                }
            }

        } catch (IOException e) {
            System.err.println("Не удалось создать лог-файл: " + e.getMessage());
        }
    }

    public static void printMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Начать игру");
        System.out.println("2. Выход");
    }
}