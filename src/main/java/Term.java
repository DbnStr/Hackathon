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
                return true;
            /*if (termType == TermType.CONSTANT && term.termType == TermType.CONSTANT)
                return true;*/
            if (term.getTermType() == TermType.FUNCTION || termType == TermType.FUNCTION) {
                return false;
            }
            return true;
            //return false;
        } else {
            if (arguments.size() != term.getArguments().size() || !termName.equals(term.getTermName()))
                return false;
            for (int i = 0; i < arguments.size(); i++) {
                Term arg = arguments.get(i);
                Term a = term.getArguments().get(i);
                if (!arg.equals(a)) {
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
