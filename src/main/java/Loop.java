import java.util.*;

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
/*        checked.add(r.getLefTerm() + "-" + r.getRightTerm());
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
        }*/

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
        checked.add(r.getLefTerm() + "-" + r.getRightTerm());
        boolean res = false;
        ArrayList<Rule> allRules = trs.getRules();
        for (Rule rl : allRules) {
            List<Object> resBool = unify(r.getRightTerm(), rl.getLefTerm());
            if (!((Boolean) resBool.get(1))) {
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

    private static List<Object> unify(Term term1, Term term2) {
        List<Object> list = new ArrayList<>();
        if (term2.getTermType() == Term.TermType.VARIABLE &&
                (term1.getTermType() == Term.TermType.CONSTANT) || (term1.getTermType() == Term.TermType.FUNCTION)) {
            list.add(term1);
            list.add(false);
            return list;
        }
        if (term1.getTermType() == Term.TermType.VARIABLE && term2.getTermType() == Term.TermType.VARIABLE ||
                term1.getTermType() == Term.TermType.CONSTANT && term2.getTermType() == Term.TermType.CONSTANT &&
                        Objects.equals(term1.getTermName(), term2.getTermName())) {
            list.add(term1);
            list.add(false);
            return list;
        }
        if (term1.getTermType() == Term.TermType.FUNCTION && term2.getTermType() == Term.TermType.FUNCTION &&
                Objects.equals(term1.getTermName(), term2.getTermName()) &&
                term2.getArguments().size() == term1.getArguments().size()) {
            Term term = new Term(term1.getTermName(), Term.TermType.FUNCTION);
            for (int i = 0; i < term1.getArguments().size(); i++) {
                List<Object> result = unify(term1.getArguments().get(i), term2.getArguments().get(i));
                if ((Boolean) result.get(1)) {
                    List<Object> list2 = new ArrayList<>();
                    list2.add(result.get(0));
                    list2.add(result.get(1));
                    return list2;
                }
                term.getArguments().add((Term) result.get(0));
            }
            list.add(term);
            list.add(false);
            return list;
        }
        list.add(null);
        list.add(true);
        return list;
    }

}
