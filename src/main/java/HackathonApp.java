import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;

public class HackathonApp {

    private static final String TEST_FILE_NAME = "test.trs";
    private static final String RESULT_FILE_NAME = "result";
    private static final String SYNTAX_ERROR_MESSAGE = "Syntax error";
    private static final String UNKNOWN_MESSAGE = "Unknown";
    private static final String TRUE_MESSAGE = "true";

    public static void main(String[] args) throws SyntaxError {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                exit(0);
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3 * 60 * 1000, 1000);
        try {
            ArrayList<String> input = FileReaderHelper.readFile(TEST_FILE_NAME);
            input = FileReaderHelper.deleteAllSpaces(input);
            Parser parser = new Parser(input);
            TRS trs = parser.parse();
//            trs.showTRS();
            checkTerms(trs);
            boolean isTerminating = LPO.checkTerminating(trs);

            if (isTerminating) {
                writeResult(TRUE_MESSAGE);
            } else {
                writeResult(UNKNOWN_MESSAGE);
            }
        } catch (SyntaxError e) {
            writeResult(SYNTAX_ERROR_MESSAGE);
        }
    }

    public static void writeResult(String result) {
        System.out.println(result);
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
