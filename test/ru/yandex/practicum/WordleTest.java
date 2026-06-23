package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    void testDictionaryLoaderValidFile() throws Exception {
        File file = new File("test_dictionary.txt");
        try {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                 PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {
                printWriter.println("кот");
                printWriter.println("котик");
                printWriter.println("собака");
                printWriter.println("мышь");
                printWriter.println("котёл");
            }

            WordleDictionaryLoader loader = new WordleDictionaryLoader(file.getAbsolutePath(), null);
            WordleDictionary dict = loader.dictionaryFilter();

            assertNotNull(dict);
            assertEquals(2, dict.getWords().size());
            assertTrue(dict.contains("котик"));
            assertTrue(dict.contains("котел"));
        } finally {
            Files.deleteIfExists(file.toPath());
        }
    }

    @Test
    void testDictionaryGetRandomWord() {
        List<String> words = List.of("котик");
        WordleDictionary dict = new WordleDictionary(words, null);
        String word = dict.getRandomWord();
        assertNotNull(word);
        assertEquals("котик", word);
    }

    @Test
    void testDictionaryCharCheckerExactMatch() {
        List<String> words = List.of("котик");
        WordleDictionary dict = new WordleDictionary(words, null);
        dict.getRandomWord();
        assertEquals("+++++", dict.charChecker("котик"));
    }

    @Test
    void testDictionaryCharCheckerAllPresentWrongPositions() {
        List<String> words = List.of("котик");
        WordleDictionary dict = new WordleDictionary(words, null);
        dict.getRandomWord();

        assertEquals("^+^++", dict.charChecker("токик"));
    }

    @Test
    void testDictionaryCharCheckerPartialMatch() {
        List<String> words = List.of("котик");
        WordleDictionary dict = new WordleDictionary(words, null);
        dict.getRandomWord();

        assertEquals("+++-+", dict.charChecker("коток"));
    }

    @Test
    void testDictionaryCharCheckerRepeatedLetters() {
        List<String> words = List.of("банан");
        WordleDictionary dict = new WordleDictionary(words, null);
        dict.getRandomWord();

        assertEquals("++-++", dict.charChecker("бааан"));
    }

    @Test
    void testGameEndAfterWin() throws Exception {
        List<String> words = List.of("котик");
        WordleDictionary dict = new WordleDictionary(words, null);
        PrintWriter logger = new PrintWriter(System.out);
        WordleGame game = new WordleGame(dict, logger);
        game.gameStarted();

        game.makeStep("котик");
        assertTrue(game.isUserAnswerRight);
        assertTrue(game.isGameEnd());
    }

    @Test
    void testGameEndAfterAttemptsExhausted() throws Exception {
        List<String> words = List.of("котик", "каток");
        WordleDictionary dict = new WordleDictionary(words, null);
        PrintWriter logger = new PrintWriter(System.out);
        WordleGame game = new WordleGame(dict, logger);
        game.gameStarted();

        String guessWord = words.get(0);
        for (int i = 0; i < 6; i++) {
            game.makeStep(guessWord);
        }
        assertTrue(game.isGameEnd());

        assertTrue(game.isGameEnd());
    }
}