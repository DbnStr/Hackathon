import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Unifier {

    public static HashMap<String, Term> unify(Term x, Term y, HashMap<String, Term> unSubst) {
        if (unSubst == null) {
            return null;
        } else if (x.equals(y)) {
            return unSubst;
        } else if (x.getTermType() == Term.TermType.VARIABLE) {
            return unifyVariable(x, y, unSubst);
        } else if (y.getTermType() == Term.TermType.VARIABLE) {
            return unifyVariable(y, x, unSubst);
        } else if (x.getTermType() == Term.TermType.FUNCTION && y.getTermType() == Term.TermType.FUNCTION) {
            if (x.getTermName().equals(y.getTermName()) && x.getArguments().size() == y.getArguments().size()) {
                for(int i = 0; i < x.getArguments().size(); ++i) {
                    unSubst = unify((Term)x.getArguments().get(i), (Term)y.getArguments().get(i), unSubst);
                }

                return unSubst;
            } else {
                return null;
            }
        } else {
            return unSubst;
        }
    }

    public static HashMap<String, Term> unifyVariable(Term variable, Term y, HashMap<String, Term> unSubst) {
        if (variable.getTermName().equals(y.getTermName())) {
            return unSubst;
        } else if (occursCheck(variable, y, unSubst)) {
            return null;
        } else if (!unSubst.containsKey(variable.getTermName())) {
            unSubst.put(variable.getTermName(), y);
            return unSubst;
        } else if (y.getTermType() == Term.TermType.VARIABLE && !unSubst.containsKey(y.getTermName())) {
            unSubst.put(y.getTermName(), variable);
            return unSubst;
        } else if (unSubst.containsKey(variable.getTermName())) {
            return unify((Term)unSubst.get(variable.getTermName()), y, unSubst);
        } else if (y.getTermType() == Term.TermType.VARIABLE && unSubst.containsKey(y.getTermName())) {
            return unify(variable, (Term)unSubst.get(y.getTermName()), unSubst);
        } else if (occursCheck(variable, y, unSubst)) {
            return null;
        } else {
            unSubst.put(variable.getTermName(), y);
            return unSubst;
        }
    }

    public static boolean occursCheck(Term variable, Term y, HashMap<String, Term> unSubst) {
        if (variable.getTermName().equals(y.getTermName())) {
            return true;
        } else if (y.getTermType() == Term.TermType.VARIABLE && unSubst.containsKey(y.getTermName())) {
            return occursCheck(variable, (Term)unSubst.get(y.getTermName()), unSubst);
        } else if (y.getTermType() == Term.TermType.FUNCTION) {
            AtomicBoolean result = new AtomicBoolean(false);
            y.getArguments().forEach((term) -> {
                if (occursCheck(variable, term, unSubst)) {
                    result.set(true);
                }

            });
            return result.get();
        } else {
            return false;
        }
    }
}