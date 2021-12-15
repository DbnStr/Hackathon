import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public ArrayList<String> getAllFunctionsNames() {
        Set<String> result = new HashSet<>();
        for (Term variable : variables) {
            result.add(variable.getTermName());
        }
        return new ArrayList<>(result);
    }

    private void swap(int i, int j) {
        String tmp = variables.get(i).getTermName();
        variables.get(i).setTermName(variables.get(j).getTermName());
        variables.get(j).setTermName(tmp);
    }

    private ArrayList<String> nextPermutation() {
        int n = variables.size();
        ArrayList<String> permutation = new ArrayList<>();
        for (Term variable : variables) {
            permutation.add(variable.getTermName());
        }
        int j = n - 2;
        while (j != -1 && permutation.get(j).compareTo(permutation.get(j + 1)) != -1) j--;
        if (j == -1)
            return null;
        int k = n - 1;
        while (permutation.get(j).compareTo(permutation.get(k)) != -1) k--;
        swap(k, j);
        int l = j + 1, r = n - 1;
        while (l < r) swap(l++, r++);
        return permutation;
    }

    //расставляем знаки для [f1, f2, f3] как f1 < f2 < f3
    public ArrayList<ArrayList<String>> getFunctionsNamesPermutations() {
        ArrayList<ArrayList<String>> permutations = new ArrayList<>();
        ArrayList<String> permutation = nextPermutation();
        while (permutation != null) {
            permutations.add(permutation);
            permutation = nextPermutation();
        }
        return permutations;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public ArrayList<Term> getVariables() {
        return variables;
    }
}
