import java.util.ArrayList;

public class LPO {
    public static boolean checkTerminating(TRS trs) {
        ArrayList<ArrayList<String>> assumptions = trs.getFunctionsNamesPermutations();
        Rule firstRule = trs.getRules().get(0);
        if (firstRule.getLefTerm().getTermType() != Term.TermType.FUNCTION) {
            return false;
        }
        for (Rule rule : trs.getRules()) {
            Term rightTerm = rule.getRightTerm();
            Term leftTerm = rule.getLefTerm();
            assumptions = leftMoreThanRight(leftTerm, rightTerm, assumptions);
            if (assumptions == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkFirst(Term leftTerm, Term rightTerm) {
        for (Term arg : leftTerm.getArguments()) {
            if (arg.equals(rightTerm))
                return true;
        }
        return false;
    }

    public static ArrayList<ArrayList<String>> checkSecond(Term leftTerm, Term rightTerm, ArrayList<ArrayList<String>> assumptions) {
        ArrayList<ArrayList<String>> newAssumptions = new ArrayList<>();
        newAssumptions.addAll(assumptions);
        for (Term arg : leftTerm.getArguments()) {
            newAssumptions = leftMoreThanRight(arg, rightTerm, newAssumptions);
            if (newAssumptions != null)
                return newAssumptions;
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> checkThird(Term leftTerm, Term rightTerm, ArrayList<ArrayList<String>> assumptions) {
        ArrayList<ArrayList<String>> deletedAssumptions = new ArrayList<>();
        for (ArrayList<String> assumption : assumptions) {
            if (assumption.indexOf(leftTerm.getTermName()) > assumption.indexOf(rightTerm.getTermName())) {
                for (Term arg: rightTerm.getArguments()) {
                    assumptions = leftMoreThanRight(leftTerm, arg, assumptions);
                    if (assumptions == null) {
                        return null;
                    }
                }
                return assumptions;
            } else
                deletedAssumptions.add(assumption);

        }

        for (ArrayList<String> deleted : deletedAssumptions) {
            assumptions.remove(deleted);
        }
        return assumptions;
    }

    public static ArrayList<ArrayList<String>> checkForth(Term leftTerm, Term rightTerm, ArrayList<ArrayList<String>> assumptions) {
        /*int j = 0;

        assumptions = leftMoreThanRight(leftTerm.getArguments().get(j), rightTerm.getArguments().get(j), assumptions);
        while (assumptions != null && j < leftTerm.getArguments().size() &&) {
            j++;
            assumptions = leftMoreThanRight(leftTerm.getArguments().get(j), rightTerm.getArguments().get(j), assumptions);
        }*/

        for (Term arg : rightTerm.getArguments()) {
            assumptions = leftMoreThanRight(leftTerm, arg, assumptions);
            if (assumptions == null)
                return null;
        }
        return assumptions;
    }

    public static ArrayList<ArrayList<String>> leftMoreThanRight(Term leftTerm, Term rightTerm, ArrayList<ArrayList<String>> assumptions) {
        if (leftTerm.getTermType() != Term.TermType.FUNCTION)
            return null;

        if (checkFirst(leftTerm, rightTerm))
            return assumptions;

        ArrayList<ArrayList<String>> newAssumptions = checkSecond(leftTerm, rightTerm, assumptions);
        if (newAssumptions != null)
            return newAssumptions;

        if (rightTerm.getTermType() != Term.TermType.FUNCTION) {
            return null;
        }

        if (leftTerm.getTermName() == rightTerm.getTermName() && leftTerm.getArguments().size() == rightTerm.getArguments().size()) {
            assumptions = checkForth(leftTerm, rightTerm, assumptions);
        } else
            assumptions = checkThird(leftTerm, rightTerm, assumptions);

        return assumptions;
    }
}
