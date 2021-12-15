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
