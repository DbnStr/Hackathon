import java.util.ArrayList;
import java.util.HashMap;

public class Loop {
    public static boolean isLoop(TRS trs) throws StackOverflowError {
        ArrayList<Rule> allRules = trs.getRules();
        for (Rule r : allRules) {
            ArrayList<String> checked = new ArrayList<>();
            if (isUnified(r, trs, checked))
                return true;
        }
        return false;
    }

    private static boolean isUnified(Rule r, TRS trs, ArrayList<String> checked) throws StackOverflowError {
        checked.add(r.getLefTerm() + "-" + r.getRightTerm());
        boolean res = false;
        ArrayList<Rule> allRules = trs.getRules();
        for (Rule rl : allRules) {
            HashMap<String, Term> unSubst = new HashMap();
            unSubst = Unifier.unify(r.getRightTerm(), rl.getLefTerm(), unSubst);
            if (unSubst == null) {
                res = true;
                String rulePair = rl.getLefTerm() + "-" + rl.getRightTerm();
                if (checked.contains(rulePair)) {
                    if (!isUnified(rl, trs, checked))
                        return false;
                }
            }
        }
//        checked.add(r.getLefTerm() + "-" + r.getRightTerm());
//        boolean res = false;
//        ArrayList<Rule> allRules = trs.getRules();
//        for (Term arg : r.getRightTerm().getArguments()) {
//            res = false;
//            for (Rule rl : allRules) {
//                HashMap<String, Term> unSubst = new HashMap();
//                unSubst = Unifier.unify(arg, rl.getLefTerm(), unSubst);
//                if (unSubst == null) {
//                    res = true;
//                    String rulePair = rl.getLefTerm() + "-" + rl.getRightTerm();
//                    if (checked.contains(rulePair)) {
//                        if (!isUnified(rl, trs, checked))
//                            return false;
//                    }
//                }
//            }
//        }
        return res;
    }
}
