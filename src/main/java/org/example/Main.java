package org.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Main {
    private static final Random random = new Random();
    private static final int CHAR_BOUND_L = 65;
    private static final int CHAR_BOUND_H = 90;
    private static final String TO_SEARCH = "GeekBrains";

    /**
     * Генерация рандомной последовательности символов.
     *
     * @param amount к-во символов.
     * @return символы в строчном представлении.
     */
    private static String generateSymbols(int amount) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            stringBuilder.append((char) random.nextInt(CHAR_BOUND_L, CHAR_BOUND_H + 1));
        }
        return stringBuilder.toString();
    }

    /**
     * Запись символов в файл
     *
     * @param fileName создать название файла
     * @param length   установить длину записанных символов в файл
     * @throws IOException
     */
    private static void writeFileContents(String fileName, int length) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(generateSymbols(length).getBytes());
        }
    }

    /**
     * Запись символов в файл с случайной записью осознанного слова для поиска
     *
     * @param fileName создать название файла
     * @param length   установить длину записанных символов в файл
     * @param words    слова
     * @throws IOException
     */
    private static void writeFileContents(String fileName, int length, int words) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            for (int i = 0; i < words; i++) {
                if (random.nextInt(5) == 5 / 2) {
                    fileOutputStream.write(TO_SEARCH.getBytes());
                } else {
                    fileOutputStream.write(generateSymbols(length).getBytes());
                }
            }
            fileOutputStream.write(' ');
        }
    }

    /**
     * Чтение из файлов и запись в другой файл
     *
     * @param fileName1 имя первого файла для чтения
     * @param fileName2 имя второго файла для чтения
     * @param fileOut   имя склеиных двух предыдущих файлов
     * @throws IOException
     */
    private static void concatenate(String fileName1, String fileName2, String fileOut) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileOut)) { // Запись
            int c;
            try (FileInputStream fileInputStream = new FileInputStream(fileName1)) { // Чтение
                while ((c = fileInputStream.read()) != -1) {
                    fileOutputStream.write(c);
                }
            }
            try (FileInputStream fileInputStream = new FileInputStream(fileName2)) { // Чтение
                while ((c = fileInputStream.read()) != -1) {
                    fileOutputStream.write(c);
                }
            }
        }
    }

    /**
     * Поиск осознанного слова
     * @param file имя файла для поиска
     * @param search искомое слово
     * @return результат ответа
     * @throws IOException
     */
    private static boolean searchInFile(String file, String search) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] searchData = search.getBytes();
            int c;
            int i = 0;
            while ((c = fileInputStream.read()) != -1){
                if (c == searchData[i]) {
                    i++;
                } else {
                    i = 0;
                    if (c == searchData[i]){
                        i++;
                    }
                }
                if (i == searchData.length) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        writeFileContents("simple.txt", 34, 6);
        System.out.println(searchInFile("simple.txt",TO_SEARCH));
        writeFileContents("simple2.txt", 34, 6);
        System.out.println(searchInFile("simple2.txt",TO_SEARCH));
        concatenate("simple.txt", "simple2.txt", "simple2_new.txt");
        System.out.println(searchInFile("simple2_new.txt",TO_SEARCH));
    }
}