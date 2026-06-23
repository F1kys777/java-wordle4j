package ru.yandex.practicum;

class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }
}

class InvalidWordLength extends GameException {
    public InvalidWordLength() {
        super("Слово должно состоять из 5 букв.");
    }
}

class WordNotFoundInDictionary extends GameException {
    public WordNotFoundInDictionary(String word) {
        super("Слово '" + word + "' не найдено в словаре.");
    }
}

class EmptyDictionaryException extends Exception  {
    public EmptyDictionaryException(String message) {
        super(message);
    }
}