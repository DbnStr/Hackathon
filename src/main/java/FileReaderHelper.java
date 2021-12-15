

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReaderHelper {
    public static ArrayList<String> readFile(String fileName) {
        try {
            File file = new File(fileName);
            //создаем объект FileReader для объекта File
            FileReader reader = new FileReader(file);

            ArrayList<String> input = new ArrayList<>();

            BufferedReader bufferedReader = new BufferedReader(reader);

            String nextLine = bufferedReader.readLine();
            while (nextLine != null) {
                if (!nextLine.isEmpty()) {
                    input.add(nextLine);
                }
                nextLine = bufferedReader.readLine();
            }
            return input;
        } catch (
                IOException exception) {
            System.out.print(exception.getMessage());
            return null;
        }
    }

    public static ArrayList<String> deleteAllSpaces(ArrayList<String> system) {
        ArrayList<String> result = new ArrayList<>();
        for(String str : system) {
            result.add(deleteSpaces(str));
        }
        return result;
    }

    private static String deleteSpaces(String input) {
        return input.replaceAll("\\s", "");
    }
}
