import java.util.ArrayList;

public class Term {
    private String termName;
    private TermType termType;
    private ArrayList<Term> arguments;

    public Term(String termName, TermType termType) {
        this.termName = termName;
        this.termType = termType;
    }

    public static boolean isVariable(Term x) {
        return x.termType == Term.TermType.VARIABLE;
    }

    public static boolean isConstructor(Term x) {
        return x.termType == TermType.FUNCTION;
    }

    public static boolean isConstant(Term x) {
        return x.termType == TermType.CONSTANT;
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

    public boolean equals(Term term) {
        if (termName != term.getTermName() || termType != term.getTermType() || arguments.size() != term.getArguments().size()) {
            return false;
        }
        for (int i = 0; i < arguments.size(); i++) {
            Term arg = arguments.get(i);
            if (!arg.equals(term.getArguments().get(i))) {
                return false;
            }
        }
        return true;
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
