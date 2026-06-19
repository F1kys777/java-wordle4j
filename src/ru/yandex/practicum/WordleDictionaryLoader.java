package ru.yandex.practicum;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
*/
public class WordleDictionaryLoader {
    private static final String connectDictionary = "C:\\Users\\user\\Desktop\\код\\java-wordle4j";

    try(BufferedReader reader = new BufferedReader(
            (new FileReader("words_ru.txt", StandardCharsets.UTF_8))) {
        while(reader.ready()) {
            String line = reader.readLine();
            Path filteredDictionary = Paths.get(connectDictionary, "filteredDictionaryFile.txt")
            if(line.length() == 5) {
                try (Writer fileWriter = new FileWriter("filteredDictionaryFile", true)){
                    fileWriter.write(line + "\n");
                } catch (IIOException e) {
                    e.printStackTrace();
                } finally {
                    fileWriter.close();
                }
            } else {
                continue;
            }
        }
    } catch {

    }

}
