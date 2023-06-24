package org.example;

import java.io.File;

public class PrintDir {
    public static void printDir(File file, String indent, boolean isLast) {
        System.out.print(indent);
        if (isLast) {
            System.out.print("╚═► ");
            indent += " ";
        } else {
            System.out.print("╠═► ");
            indent += "║ ";
        }
        // найменование директории или файла
        System.out.println(file.getName()); // имя файла или директории
        File[] files = file.listFiles(); //список саб элементов
        if (files == null) {
            return;
        }
        int subDirTotal = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                subDirTotal++;
            }
        }
        int subDirCounter = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                printDir(files[i], indent, subDirCounter == subDirTotal - 1);
                subDirCounter++;
            }
        }
    }
    public static void printFiles(File file, String indent, boolean isLast) {
        System.out.print(indent);
        if (isLast) {
            System.out.print("╚═► ");
            indent += " ";
        } else {
            System.out.print("╠═► ");
            indent += "║ ";
        }
        // найменование директории или файла
        System.out.println(file.getName()); // имя файла или директории
        File[] files = file.listFiles(); //список саб элементов
        if (files == null) {
            return;
        }
        int subDirTotal = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                subDirTotal++;
            }
        }
        int subDirCounter = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                printFiles(files[i], indent, subDirCounter == subDirTotal - 1);
                subDirCounter++;
            }
        }
    }
}
