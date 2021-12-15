import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HackathonApp {
    public static void main(String[] args) throws SyntaxError {
        ArrayList<String> in = new ArrayList<>(Arrays.asList(args));
        try {
            ArrayList<String> input = FileReaderHelper.readFile("input.txt");
            input = FileReaderHelper.deleteAllSpaces(input);
            Parser parser = new Parser(input);
            TRS trs = parser.parse();
//            ArrayList<Rule> rls = trs.getRules();
//            for (Rule r : rls) {
//                r.getLefTerm().showTerm();
//                System.out.println();
//                r.getRightTerm().showTerm();
//                System.out.println();
//            }
//            Map<String, Term> Subst = new HashMap<>();
//            Subst = Unifier.unify(rls.get(0).getLefTerm(), rls.get(0).getRightTerm(), Subst);
//            long i = 0;
//            for (Map.Entry<String, Term> pair : Subst.entrySet()) {
//                System.out.print(pair.getKey() + "=" );
//                pair.getValue().showTerm();
//            }
//            Unifier.findUnifier(rls.get(0).getRightTerm(), Subst);
            trs.showTRS();
            boolean isTerminating = LPO.checkTerminating(trs);
            if (isTerminating)
                System.out.println("true");
            else
                System.out.println("unknown");

        } catch (SyntaxError e) {
            System.out.println(e.getMessage());
        }
    }
}
