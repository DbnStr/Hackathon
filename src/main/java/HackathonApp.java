import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HackathonApp {

    private static final String TEST_FILE_NAME = "test.trs";
    private static final String RESULT_FILE_NAME = "result";
    private static final String SYNTAX_ERROR_MESSAGE = "Syntax error";
    private static final String UNKNOWN_MESSAGE = "Unknown";
    private static final String TRUE_MESSAGE = "True";
    private static final String FALSE_MESSAGE = "False";

    public static void main(String[] args) throws SyntaxError {
        ArrayList<String> in = new ArrayList<>(Arrays.asList(args));
        try {
            ArrayList<String> input = FileReaderHelper.readFile(TEST_FILE_NAME);
            input = FileReaderHelper.deleteAllSpaces(input);
            Parser parser = new Parser(input);
            TRS trs = parser.parse();
            checkTerms(trs);
            boolean isTerminating = LPO.checkTerminating(trs);
            boolean isLoop = Loop.isLoop(trs);
            if (isLoop) {
                writeResult(FALSE_MESSAGE);
            } else {
                if (isTerminating) {
                    writeResult(TRUE_MESSAGE);
                } else {
                    writeResult(UNKNOWN_MESSAGE);
                }
            }
        } catch (SyntaxError e) {
            System.out.println(e.getMessage());
            writeResult(SYNTAX_ERROR_MESSAGE);
        }
    }

    public static void writeResult(String result) {
        try(FileWriter writer = new FileWriter(RESULT_FILE_NAME, false))
        {
            writer.write(result);

            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
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
