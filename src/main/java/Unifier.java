import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Unifier {

    public static HashMap<String, Term> unify(Term x, Term y, HashMap<String, Term> unSubst) {
        if (unSubst == null) {
            return null;
        } else if (x.equals(y)) {
            return unSubst;
        } else if (Term.isVariable(x)) {
            return unifyVariable(x, y, unSubst);
        } else if (Term.isVariable(y)) {
            return unifyVariable(y, x, unSubst);
        } else if (Term.isConstructor(x) && Term.isConstructor(y)) {
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
        } else if (Term.isVariable(y) && !unSubst.containsKey(y.getTermName())) {
            unSubst.put(y.getTermName(), variable);
            return unSubst;
        } else if (unSubst.containsKey(variable.getTermName())) {
            return unify((Term)unSubst.get(variable.getTermName()), y, unSubst);
        } else if (Term.isVariable(y) && unSubst.containsKey(y.getTermName())) {
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
        } else if (Term.isVariable(y) && unSubst.containsKey(y.getTermName())) {
            return occursCheck(variable, (Term)unSubst.get(y.getTermName()), unSubst);
        } else if (Term.isConstructor(y)) {
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

    public static void showUnifier(Term term, Boolean unSubstIsVariable, HashMap<String, Term> unSubst) {
        if (Term.isConstructor(term)) {
            System.out.print(term.getTermName());
            System.out.print("(");

            for(int i = 0; i < term.getArguments().size(); ++i) {
                Term arg = (Term)term.getArguments().get(i);
                if (i != 0) {
                    System.out.print(",");
                }

                if (unSubstIsVariable) {
                    System.out.print(arg.getTermName());
                } else {
                    showUnifier(arg, false, unSubst);
                }
            }

            System.out.print(")");
        }

        if (Term.isConstant(term)) {
            System.out.print(term.getTermName());
        }

        if (Term.isVariable(term)) {
            if (unSubst.containsKey(term.getTermName())) {
                Term subTerm = (Term)unSubst.get(term.getTermName());
                if (Term.isVariable(subTerm)) {
                    System.out.print(subTerm.getTermName());
                } else {
                    showUnifier(subTerm, true, unSubst);
                }
            } else {
                System.out.print(term.getTermName());
            }
        }

    }

    public static void showSubstitution(HashMap<String, Term> unSubst) {
        unSubst.forEach((k, v) -> {
            System.out.print(k + " := ");
            showSub(v, unSubst);
            System.out.println();
        });
    }

    public static void showSub(Term term, HashMap<String, Term> unSubst) {
        if (Term.isConstructor(term)) {
            System.out.print(term.getTermName());
            System.out.print("(");

            for(int i = 0; i < term.getArguments().size(); ++i) {
                Term arg = (Term)term.getArguments().get(i);
                if (i != 0) {
                    System.out.print(",");
                }

                showSub(arg, unSubst);
            }

            System.out.print(")");
        }

        if (Term.isConstant(term)) {
            System.out.print(term.getTermName());
        }

        if (Term.isVariable(term)) {
            System.out.print(term.getTermName());
        }

    }
}