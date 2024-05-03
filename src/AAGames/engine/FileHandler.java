package src.AAGames.engine;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import com.dslplatform.json.*;

public class FileHandler {

    private FileHandler() {
        // Utility class
    }

    public static ArrayList<int[]> worldReader(String filePath) {
        String json = readFile(filePath);
        DslJson<Object> dslJson = new DslJson<>();
        JsonReader<Object> reader = dslJson.newReader(json.getBytes());

        try {
            reader.read();
            String worldName = "";
            ArrayList<int[]> chunkIDs = new ArrayList<int[]>();

            reader.fillName();
            if (reader.wasLastName("world_name")) {
                worldName = reader.readString();
            }

            reader.getNextToken(); // Move to the next token, which should be the beginning of the array or next property
            reader.fillName();
            if (reader.wasLastName("chunks")) {
                reader.startArray(); // Start reading the array
                reader.startArray(); // Start reading the array
                while (reader.last() != ']') {
                    reader.getNextToken(); // Prepare to read next array element
                    String chunk = reader.readString();
                    String[] parts = chunk.split(",");
                    int[] coordinates = new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
                    chunkIDs.add(coordinates);
                }
                reader.endArray(); // Ensure closing the array parsing
            }

            return chunkIDs;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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