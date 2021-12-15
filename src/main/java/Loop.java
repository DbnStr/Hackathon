import java.util.ArrayList;
import java.util.HashMap;

public class Loop {
    public static boolean isLoop(TRS trs) {
        ArrayList<Rule> allRules = trs.getRules();
        for (Rule r : allRules) {
            ArrayList<String> checked = new ArrayList<>();
            if (isUnified(r, trs, checked))
                return true;
        }
        return false;
    }

    private static boolean isUnified(Rule r, TRS trs, ArrayList<String> checked) {
        System.out.println(r.getLefTerm().getTermName() + " - " + r.getRightTerm().getTermName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checked.add(r.getLefTerm() + "-" + r.getRightTerm());
        boolean res = false;
        ArrayList<Rule> allRules = trs.getRules();
        for (Rule rl : trs.getRules()) {
            System.out.println("rl1 " + rl.getLefTerm().getTermName() + " - " + rl.getRightTerm().getTermName());
        }
        for (Rule rl : allRules) {
            System.out.println("rl " + rl.getLefTerm().getTermName() + " - " + rl.getRightTerm().getTermName());
            HashMap<String, Term> unSubst = new HashMap();
            unSubst = Unifier.unify(r.getRightTerm(), rl.getLefTerm(), unSubst);
            //unSubst.forEach((key, value) -> System.out.println(value.getTermName()));
            System.out.println(unSubst);
            if (unSubst == null) {
                res = true;
                String rulePair = rl.getLefTerm() + "-" + rl.getRightTerm();
                if (checked.contains(rulePair)) {
                    if (!isUnified(rl, trs, checked))
                        return false;
                }
            }
        }
        return res;
    }
}
