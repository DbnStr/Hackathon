import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Unifier {
    //Subst = unify(firstTerm, secondTerm, Subst);

    static void findUnifier(Term t, Map<String, Term> unSubst) {
        if (t.getTermType() == Term.TermType.FUNCTION) {
            System.out.print(t.getTermName()+"(");
            for (int i = 0; i < t.getArguments().size(); i++) {
                Term arg = t.getArguments().get(i);
                if (i != 0) {
                    System.out.print(",");
                }
                findUnifier(arg, unSubst);
            }
            System.out.print(")");
        }
        if (t.getTermType() == Term.TermType.CONSTANT)
            System.out.print(t.getTermName());
        if (t.getTermType() == Term.TermType.VARIABLE) {
            if (unSubst.containsKey(t.getTermName()))
                findUnifier(unSubst.get(t.getTermName()), unSubst);
            else System.out.print(t.getTermName());
        }
    }

    static Map<String, Term> unify(Term first, Term second, Map<String, Term> subst) {
        if (subst == null)
            return null;
        if (first.equals(second))
            return subst;
        if (first.getTermType() == Term.TermType.VARIABLE)
            return unifyVar(first, second, subst);
        if (second.getTermType() == Term.TermType.VARIABLE)
            return unifyVar(second, first, subst);
        if (first.getTermType() == Term.TermType.FUNCTION && second.getTermType() == Term.TermType.FUNCTION) {
            if (!first.getTermName().equals(second.getTermName()) || first.getArguments().size() != second.getArguments().size())
                return null;
            for (int i = 0; i < first.getArguments().size(); i++) {
                subst = unify(first.getArguments().get(i), second.getArguments().get(i), subst);
            }
            return subst;
        }
        return null;
    }

    private static Map<String, Term> unifyVar(Term first, Term second, Map<String, Term> subst) {
        if (subst.containsKey(first.getTermName()))
            return unify(subst.get(first.getTermName()), second, subst);
        if (second.getTermType() == Term.TermType.VARIABLE && subst.containsKey(second.getTermName()))
            return unify(first, subst.get(second.getTermName()), subst);
//        if (occursCheck(first, second, subst))
//            return null;
        subst.put(first.getTermName(), second);
        return subst;
    }

    static Boolean occursCheck(Term first, Term second, Map<String, Term> subst) {
        if (first == second)
            return Boolean.TRUE;
        if (second.getTermType() == Term.TermType.VARIABLE && subst.containsKey(second.getTermName()))
            return occursCheck(first, subst.get(second.getTermName()), subst);
        AtomicBoolean result = new AtomicBoolean(false);
        second.getArguments().forEach(t -> {
            if (occursCheck(first, t, subst)) result.set(true);
        });
        return result.get();
    }
}