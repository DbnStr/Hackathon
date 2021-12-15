import java.util.ArrayList;

public class TRS {
    private ArrayList<Term> variables;
    private ArrayList<Rule> rules;

    public TRS(ArrayList<Term> variables, ArrayList<Rule> rules) {
        this.variables = variables;
        this.rules = rules;
    }

    public void showTRS() {
        System.out.println("Variables : ");
        for (Term term : variables) {
            System.out.println(term.getTermName() + "  " + term.getTermType() + ";");
        }

        System.out.println("Rules :");
        for (Rule rule : rules) {
            rule.showRule();
            System.out.println();
        }
    }
}
