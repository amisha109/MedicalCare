package org.amishakalhan;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIO {
    private static final String PATH = System.getProperty("user.home") + "/.hms/";

    public static void createFile(String filename) {
        File file = new File(PATH + filename + ".json");
        if (!file.exists()) {
            try {
                if(!file.createNewFile()) {
                    throw new RuntimeException("Could not create file: " + PATH + filename + ".json");
                }
                FileIO.writeFile(filename, "[]");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String readFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(PATH + fileName + ".json")));
        } catch (IOException e) {
            throw new RuntimeException("Some error occurred in reading " + fileName + ".json\nError: " + e);
        }
    }

    public static void writeFile(String fileName, String contents) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(PATH + fileName + ".json");
        } catch (IOException e) {
            throw new RuntimeException("Some error occurred in reading " + fileName + ".json\nError: " + e);
        }
        try{
            fileWriter.write(contents);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Some error occurred in writing to " + fileName + ".json\nError: " + e);
        }
    }
}
