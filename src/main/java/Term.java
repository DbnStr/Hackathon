import java.util.ArrayList;

public class Term {
    private String termName;
    private TermType termType;
    private ArrayList<Term> arguments;

    public Term(String termName, TermType termType) {
        this.termName = termName;
        this.termType = termType;
        this.arguments = new ArrayList<>();
    }

    public ArrayList<Term> getAllChildTerms() {
        ArrayList<Term> result = new ArrayList<>();
        for(Term arg : arguments) {
            result.add(arg);
            result.addAll(arg.getAllChildTerms());
        }
        return result;
    }

    public boolean equals(Term term) {
        if (term.getTermType() != TermType.FUNCTION && getTermType() != TermType.FUNCTION) {
            if (termType == TermType.VARIABLE && term.getTermType() == TermType.VARIABLE)
                return termName.equals(term.getTermName());
            return true;
        } else {
            if (arguments.size() != term.getArguments().size())
                return false;
            for (int i = 0; i < arguments.size(); i++) {
                Term arg = arguments.get(i);
                Term a = term.getArguments().get(i);
                if (!arg.equals(arguments.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public String getTermName() {
        return termName;
    }

    public TermType getTermType() {
        return termType;
    }

    public ArrayList<Term> getArguments() {
        return arguments;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setTermType(TermType termType) {
        this.termType = termType;
    }

    public void setArguments(ArrayList<Term> arguments) {
        this.arguments = arguments;
    }

    public static boolean isVariable(Term t) {
        return t.getTermType() == TermType.VARIABLE;
    }

    public static boolean isConstructor(Term t) {
        return t.getTermType() == TermType.FUNCTION;
    }

    public static boolean isConstant(Term t) {
        return t.getTermType() == TermType.CONSTANT;
    }

    enum TermType {
        VARIABLE,
        CONSTANT,
        FUNCTION
    }

    public void showTerm() {
        System.out.print(termName + " " + termType + " ");
        if (termType == TermType.FUNCTION) {
            for (Term argument : arguments) {
                argument.showTerm();
            }
        }
    }
}
