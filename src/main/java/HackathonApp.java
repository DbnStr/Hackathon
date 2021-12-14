import java.util.ArrayList;
import java.util.Arrays;

public class HackathonApp {
    public static void main(String[] args) throws SyntaxError {
        ArrayList<String> in = new ArrayList<>(Arrays.asList(args));
        try {
            Parser.parse(FileReaderHelper.readFile("input.txt"));

        } catch (SyntaxError e) {
            System.out.println(e.getMessage());
        }
    }
}
