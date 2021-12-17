import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LPO {
    public static boolean checkTerminating(TRS trs) {
        ArrayList<ArrayList<String>> assumptions = trs.getFunctionsNamesPermutations();
        ArrayList<ArrayList<String>> constantsAssumptions = trs.getConstantsNamesPermutations();
        //System.out.println(assumptions.toString());
        for (Rule rule : trs.getRules()) {
            Term rightTerm = rule.getRightTerm();
            Term leftTerm = rule.getLefTerm();
            assumptions = leftMoreThanRight(leftTerm, rightTerm, assumptions, constantsAssumptions);
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

    public static ArrayList<ArrayList<String>> checkSecond(Term leftTerm, Term rightTerm, ArrayList<ArrayList<String>> assumptions,
                                                           ArrayList<ArrayList<String>> constantsAssumptions) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (Term arg : leftTerm.getArguments()) {
            ArrayList<ArrayList<String>> newAssumptions = new ArrayList<>(assumptions);
            newAssumptions = leftMoreThanRight(arg, rightTerm, newAssumptions, constantsAssumptions);
            if (newAssumptions != null)
                result.addAll(newAssumptions);
        }
        if (result.isEmpty())
            return null;
        Set<ArrayList<String>> res = new HashSet<>(result);
        return new ArrayList<>(res);
    }

    public static ArrayList<ArrayList<String>> checkThird(Term leftTerm, Term rightTerm, ArrayList<ArrayList<String>> assumptions,
                                                          ArrayList<ArrayList<String>> constantsAssumptions) {
        ArrayList<ArrayList<String>> deletedAssumptions = new ArrayList<>();
        for (int i = 0; i < assumptions.size(); i++) {
            ArrayList<String> assumption = assumptions.get(i);
            if (assumption.indexOf(leftTerm.getTermName()) > assumption.indexOf(rightTerm.getTermName())) {
                for (Term arg: rightTerm.getArguments()) {
                    assumptions = leftMoreThanRight(leftTerm, arg, assumptions, constantsAssumptions);
                    if (assumptions == null) {
                        return null;
                    }
                }
            } else
                deletedAssumptions.add(assumption);

        }

        for (ArrayList<String> deleted : deletedAssumptions) {
            assumptions.remove(deleted);
        }
        return assumptions;
    }

    public static ArrayList<ArrayList<String>> checkForth(Term leftTerm, Term rightTerm, ArrayList<ArrayList<String>> assumptions,
                                                          ArrayList<ArrayList<String>> constantsAssumptions) {
        for (Term arg : rightTerm.getArguments()) {
            assumptions = leftMoreThanRight(leftTerm, arg, assumptions, constantsAssumptions);
            if (assumptions == null)
                return null;
        }

        /*n-ка (t1, . . . , tn) левых аргументов лексикографически больше, чем
        (u1, . . . , un) - правых агрументов (т.е. первый её не совпадающий с ui
        элемент ti удовлетворяет условию ti >lo ui).*/
        int j = 0;
        int len = leftTerm.getArguments().size();
        Term leftArgument = leftTerm.getArguments().get(j);
        Term rightArgument = rightTerm.getArguments().get(j);
        while (j < len && leftArgument.equals(rightArgument)) {
            j++;
            if (j < len) {
                leftArgument = leftTerm.getArguments().get(j);
                rightArgument = rightTerm.getArguments().get(j);
            }
        }
        if (j == len) {
            return null;
        }
        assumptions = leftMoreThanRight(leftTerm.getArguments().get(j), rightTerm.getArguments().get(j), assumptions, constantsAssumptions);

        return assumptions;
    }

    public static ArrayList<ArrayList<String>> leftMoreThanRight(Term leftTerm, Term rightTerm, ArrayList<ArrayList<String>> assumptions,
                                                                 ArrayList<ArrayList<String>> constantsAssumptions) {
        if (checkFirst(leftTerm, rightTerm))
            return assumptions;

        if (leftTerm.getTermType() != Term.TermType.FUNCTION) {
            if (rightTerm.getTermType() == Term.TermType.FUNCTION)
                return null;
            if (leftTerm.getTermType() == Term.TermType.VARIABLE && rightTerm.getTermType() == Term.TermType.VARIABLE)
                return assumptions;
            if (leftTerm.getTermType() == Term.TermType.CONSTANT && rightTerm.getTermType() == Term.TermType.CONSTANT) {
                ArrayList<ArrayList<String>> newConstantAssumptions = new ArrayList<>();
                for (ArrayList<String> assumption : constantsAssumptions) {
                    if (assumption.indexOf(leftTerm.getTermName()) > assumption.indexOf(rightTerm.getTermName()))
                        newConstantAssumptions.add(assumption);
                }
                constantsAssumptions = newConstantAssumptions;
                return assumptions;
            } else
                return null;
        }

        ArrayList<ArrayList<String>> newAssumptions = checkSecond(leftTerm, rightTerm, assumptions, constantsAssumptions);
        if (newAssumptions != null && newAssumptions.size() == assumptions.size())
            return newAssumptions;

        if (rightTerm.getTermType() != Term.TermType.FUNCTION) {
            return null;
        }

        int leftSize = leftTerm.getArguments().size();
        int rightSize = rightTerm.getArguments().size();
        if (Objects.equals(leftTerm.getTermName(), rightTerm.getTermName()) && leftSize == rightSize) {
            assumptions = checkForth(leftTerm, rightTerm, assumptions, constantsAssumptions);
        } else
            assumptions = checkThird(leftTerm, rightTerm, assumptions, constantsAssumptions);

        if (constantsAssumptions != null && constantsAssumptions.isEmpty())
            return null;
        if (assumptions != null && assumptions.isEmpty())
            return null;
        if (assumptions == null && newAssumptions == null)
            return null;

        if (newAssumptions != null) {
            if (assumptions != null) {
                assumptions.addAll(newAssumptions);
                Set<ArrayList<String>> res = new HashSet<>(assumptions);
                return new ArrayList<>(res);
            }
            return newAssumptions;
        }
        return assumptions;
    }
}
