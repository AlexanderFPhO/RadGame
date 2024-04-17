package src.AAGames.engine;

import java.io.IOException;
import java.nio.file.*;

public class FileHandler {

    private FileHandler() {
        // Utility class
    }

    public static void mapReader(String filePath) {
        readFile(filePath);
    }

    public static void writeFile(String filePath, String content) {
        try {
            Files.write(Paths.get(filePath), content.getBytes());
        } catch (IOException excp) {
            throw new RuntimeException("Error writing file [" + filePath + "]", excp);
        }
    }

    public static String readFile(String filePath) {
        String str;
        try {
            str = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException excp) {
            throw new RuntimeException("Error reading file [" + filePath + "]", excp);
        }
        return str;
    }
}