import java.util.ArrayList;

public class Rule {
    private Term leftTerm;
    private Term rightTerm;

    public Rule(Term lefTerm, Term rightTerm) {
        this.leftTerm = lefTerm;
        this.rightTerm = rightTerm;
    }

    public void showRule() {
        leftTerm.showTerm();
        System.out.print(" --> ");
        rightTerm.showTerm();
    }

    public ArrayList<Term> getAllTerms() {
        ArrayList<Term> result = new ArrayList<>();
        result.add(leftTerm);
        result.add(rightTerm);
        result.addAll(leftTerm.getAllChildTerms());
        result.addAll(rightTerm.getAllChildTerms());
        return result;
    }

    public Term getLefTerm() {
        return leftTerm;
    }

    public Term getRightTerm() {
        return rightTerm;
    }

    public void setLefTerm(Term lefTerm) {
        this.leftTerm = lefTerm;
    }

    public void setRightTerm(Term rightTerm) {
        this.rightTerm = rightTerm;
    }
}
