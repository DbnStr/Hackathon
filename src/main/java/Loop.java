import java.util.ArrayList;
import java.util.HashMap;

public class Loop {
    private static ArrayList<String> checked = new ArrayList<>();

    public static boolean isLoop(TRS trs) {
        ArrayList<Rule> allRules = trs.getRules();
        for (Rule r : allRules) {
            String rulePair = r.getLefTerm()+"-"+r.getRightTerm();
            if (checked.contains(rulePair)) {
                if (isUnified(r, trs))
                    return true;
            }
        }
        return false;
    }

    private static boolean isUnified(Rule r, TRS trs) {
        checked.add(r.getLefTerm()+"-"+r.getRightTerm());
        boolean res = false;
        trs.getRules();
        ArrayList<Rule> allRules = trs.getRules();
        for (Rule rl : allRules) {
            HashMap<String, Term> unSubst = new HashMap();
            unSubst = Unifier.unify(r.getRightTerm(), rl.getLefTerm(), unSubst);
            if (unSubst == null) {
                res = true;
                String rulePair = r.getLefTerm()+"-"+r.getRightTerm();
                if (checked.contains(rulePair)) {
                    if (!isUnified(rl, trs))
                        return false;
                }
            }
        }
        return res;
    }
}
