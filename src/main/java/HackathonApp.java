import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HackathonApp {
    public static void main(String[] args) throws SyntaxError {
        ArrayList<String> in = new ArrayList<>(Arrays.asList(args));
        try {
            ArrayList<String> input = FileReaderHelper.readFile("test.trs");
            input = FileReaderHelper.deleteAllSpaces(input);
            Parser parser = new Parser(input);
            TRS trs = parser.parse();
//            ArrayList<Rule> rls = trs.getRules();
//            HashMap<String, Term> unSubst = new HashMap();
//            unSubst = Unifier.unify(rls.get(0).getLefTerm(), rls.get(0).getRightTerm(), unSubst);
//            if (unSubst == null) {
//                System.out.println("НЕВОЗМОЖНО УНИФИЦИРОВАТЬ");
//            } else {
//                Unifier.showSubstitution(unSubst);
//                Unifier.showUnifier(rls.get(0).getLefTerm(), false, unSubst);
//            }
            boolean isTerminating = LPO.checkTerminating(trs);
            boolean isLoop = Loop.isLoop(trs);
            if (isTerminating)
                System.out.println("True");
            else if (isLoop)
                System.out.println("False");
            else
                System.out.println("unknown");

        } catch (SyntaxError e) {
            System.out.println(e.getMessage());
        }
    }
}
