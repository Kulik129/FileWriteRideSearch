package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
     *
     * @param file   имя файла для поиска
     * @param search искомое слово
     * @return результат ответа
     * @throws IOException
     */
    private static boolean searchInFile(String file, String search) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] searchData = search.getBytes();
            int c;
            int i = 0;
            while ((c = fileInputStream.read()) != -1) {
                if (c == searchData[i]) {
                    i++;
                } else {
                    i = 0;
                    if (c == searchData[i]) {
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

    /**
     * Поиск искомого слова в массиве файлов
     * @param files файл
     * @param search искомое слово
     * @return
     * @throws IOException
     */
    private static List<String> searchMath(String[] files, String search) throws IOException {
        List<String> list = new ArrayList<>();
        File path = new File(new File(".").getCanonicalPath());
        File[] dir = path.listFiles();
        for (int i = 0; i < dir.length; i++) {
            if (dir[i].isDirectory()) {
                continue;
            } else {
                for (int j = 0; j < files.length; j++) {
                    if (dir[i].getName().equals(files[j])) {
                        if (searchInFile(dir[i].getName(), search)) {
                            list.add(dir[i].getName());
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * Функция, создающая резервную копию всех файлов в директории(без поддиректорий) во вновь созданную папку ./backup
     * @param sourceDirectory путь к директории из которой копировать файлы
     * @throws IOException
     */
    public static void backupDirectory(String sourceDirectory) throws IOException {
        Path path = Paths.get("./backup"); // Создание папки для резервной копии
        Files.createDirectories(path);
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(sourceDirectory)); // Получение списка файлов в директории
        for (Path pathFile : directoryStream){
            if (Files.isRegularFile(pathFile)){
                Files.copy(pathFile,path.resolve(pathFile.getFileName())); // Если это файл, то копируем его в папку для резервной копии
            }
        }
    }

    public static void main(String[] args) throws IOException {
        writeFileContents("simple.txt", 34, 6);
        System.out.println(searchInFile("simple.txt", TO_SEARCH));
        writeFileContents("simple2.txt", 34, 6);
        System.out.println(searchInFile("simple2.txt", TO_SEARCH));
        concatenate("simple.txt", "simple2.txt", "simple2_new.txt");
        System.out.println(searchInFile("simple2_new.txt", TO_SEARCH));
//        PrintDir.printDir(new File("."),"",true);
        PrintDir.printFiles(new File("."), "", true);

        String[] filesName = new String[10];
        for (int i = 0; i < filesName.length; i++) {
            filesName[i] = "file_" + (i + 1) + ".txt";
            writeFileContents(filesName[i], 100, 5);
            System.out.printf("Файл %s создан \n", filesName[i]);
        }
        List<String> result = searchMath(filesName, TO_SEARCH);
        for (String s : result) {
            System.out.printf("Файл %s содержит искомое слово'%s'\n", s, TO_SEARCH);
        }

        backupDirectory("/Users/mitya.kulikbk.ru/Desktop/FileWriteRideAndSearch/");

    }
}