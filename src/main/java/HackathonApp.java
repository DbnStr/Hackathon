import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HackathonApp {
    public static void main(String[] args) throws SyntaxError {
        try {
            ArrayList<String> input = FileReaderHelper.readFile("test.trs");
            input = FileReaderHelper.deleteAllSpaces(input);
            Parser parser = new Parser(input);
            TRS trs = parser.parse();
            checkTerms(trs);

            try(FileWriter writer = new FileWriter("result", false))
            {
                String text = "Unknown";
                writer.write(text);

                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        } catch (SyntaxError e) {
            System.out.println(e.getMessage());
            try(FileWriter writer = new FileWriter("result", false))
            {
                String text = "Syntax error";
                writer.write(text);

                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void checkTerms(TRS trs) throws SyntaxError {
        Map<String, TreeSet<Integer>> functions = trs.getAllTerms();
        for (String functionName: functions.keySet()) {
            if (trs.getAllVariablesNames().contains(functionName) && functions.get(functionName).size() == 1 &&
             functions.get(functionName).first() > 0)
                throw new SyntaxError("NOT A FUNCTION NAME");
            else if (functions.get(functionName).size() > 1)
                throw new SyntaxError("CONFLICT IN FUNCTION ARGUMENTS SIZE");
        }
    }
}
