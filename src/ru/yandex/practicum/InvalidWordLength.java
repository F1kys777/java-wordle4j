package ru.yandex.practicum;

class InvalidWordLength extends GameException {
    public InvalidWordLength() {
        super("Слово должно состоять из 5 букв.");
    }
}