import java.util.ArrayList;
import java.util.Arrays;

public class HackathonApp {
    public static void main(String[] args) throws SyntaxError {
        ArrayList<String> in = new ArrayList<>(Arrays.asList(args));
        try {
            ArrayList<String> input = FileReaderHelper.readFile("input.txt");
            input = FileReaderHelper.deleteAllSpaces(input);
            Parser parser = new Parser(input);
            TRS trs = parser.parse();
            trs.showTRS();

        } catch (SyntaxError e) {
            System.out.println(e.getMessage());
        }
    }
}
