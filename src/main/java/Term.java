import java.util.ArrayList;

public class Term {
    private String termName;
    private TermType termType;
    private ArrayList<Term> arguments;

    public Term(String termName, TermType termType) {
        this.termName = termName;
        this.termType = termType;
    }

    public void setArguments(ArrayList<Term> terms) {
        this.arguments = terms;
    }

    enum TermType {
        VARIABLE,
        CONSTANT,
        FUNCTION
    }
}
