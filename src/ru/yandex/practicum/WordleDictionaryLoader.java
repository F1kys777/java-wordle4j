package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    private static final String DEFAULT_DICTIONARY_FILE = "words_ru.txt";
    private final PrintWriter log;
    private final String filePath;

    public WordleDictionaryLoader(PrintWriter log) {
        this(DEFAULT_DICTIONARY_FILE, log);
    }

    public WordleDictionaryLoader(String filePath, PrintWriter log) {
        this.log = log;
        this.filePath = filePath;
    }

    public WordleDictionary dictionaryFilter() throws IOException, EmptyDictionaryException {
        List<String> wordsFiltered = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 5) {
                    String lineLowReplaced = normalize(line);
                    wordsFiltered.add(lineLowReplaced);
                }
            }
        }
        if (wordsFiltered.isEmpty()) {
            throw new EmptyDictionaryException("В словаре нет слов");
        }
        if (log != null) {
            log.println("Загружено слов: " + wordsFiltered.size());
        }
        return new WordleDictionary(wordsFiltered, log);
    }

    private String normalize(String word) {
        return word.toLowerCase().replace('ё', 'е').trim();
    }
}

