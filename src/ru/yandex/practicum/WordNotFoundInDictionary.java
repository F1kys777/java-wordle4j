package ru.yandex.practicum;

class WordNotFoundInDictionary extends GameException {
    public WordNotFoundInDictionary(String word) {
        super("Слово '" + word + "' не найдено в словаре.");
    }
}