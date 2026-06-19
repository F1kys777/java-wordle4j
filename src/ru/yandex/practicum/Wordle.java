package ru.yandex.practicum;

import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printMenu();

        while (true) {

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Запускаем игру...");
                    break;
                case 2:
                    System.out.println("Выход из игры, досвидания!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор! Вы можете начать игру или выйти из игры.");
            }
        }
    }

    public static void printMenu() {

        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Начать игру");
        System.out.println("2. Выход");

    }
}
