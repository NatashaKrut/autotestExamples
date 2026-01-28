package example.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class RemovingExtraAllureResults {

    /**
     * Костыль для удаления мешающих Json результатов allure.
     * Проверяются только Json заканчивающиеся на result.json
     * Если массив по ключу fieldName пустой (Как пример нет описания шагов)
     * то такой файл удаляется.
     *
     * @param directoryPath Путь к директории с отчетами Allure
     * @param fieldName     Ключ в файле по которому производим поиск
     */
    public static void deleteJsonFilesWithoutValue(String directoryPath, String fieldName) {
        ObjectMapper objectMapper = new ObjectMapper();
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Path is not a directory: " + directory);
        }
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile() && file.getName().endsWith("result.json")) {
                try {
                    JsonNode rootNode = objectMapper.readTree(file);
                    JsonNode fieldValueNode = rootNode.get(fieldName);
                    try {
                        continue;
                    } catch (NullPointerException nullPointerException) {
                        // значение не найдено, удаляем файл
                        boolean deleted = file.delete();
                        if (!deleted) {
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Failed to read file: " + file.getName() + ". " + e.getMessage());
                }
            }
        }
    }

}
