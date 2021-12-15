import java.util.*;
import java.util.stream.Collectors;

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
        Set<Term> result = new HashSet<>();
        for (Rule rule: rules) {
            result.addAll(rule.getAllTerms());
        }

        return result.stream().map(Term::getTermName).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<String> getAllVariablesNames() {
        Set<String> result = new HashSet<>();
        for (Term variable : variables) {
            result.add(variable.getTermName());
        }
        return new ArrayList<>(result);
    }

    private void swap(ArrayList<String> arr, int i, int j) {
        Collections.swap(arr, i, j);
    }

    //возвращаем следующую переставку(алгос из инета)
    private ArrayList<String> nextPermutation(ArrayList<String> functions) {
        int n = functions.size();
        ArrayList<String> permutation = new ArrayList<>(functions);
        int j = n - 2;
        while (j != -1 && permutation.get(j).compareTo(permutation.get(j + 1)) != -1) j--;
        if (j == -1)
            return null;
        int k = n - 1;
        while (permutation.get(j).compareTo(permutation.get(k)) != -1) k--;
        swap(functions, k, j);
        int l = j + 1, r = n - 1;
        while (l < r) swap(functions, l++, r--);
        return permutation;
    }

    //расставляем знаки для [f1, f2, f3] как f1 < f2 < f3
    public ArrayList<ArrayList<String>> getFunctionsNamesPermutations() {
        ArrayList<ArrayList<String>> permutations = new ArrayList<>();
        ArrayList<String> functions = getAllFunctionsNames();
        ArrayList<String> permutation = nextPermutation(functions);
        while (permutation != null) {
            permutations.add(permutation);
            permutation = nextPermutation(functions);
        }
        return permutations;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public ArrayList<Term> getVariables() {
        return variables;
    }

    public Map<String, TreeSet<Integer>> getAllTerms() {
        ArrayList<Term> result = new ArrayList<>();
        for (Rule rule: rules) {
            result.addAll(rule.getAllTerms());
        }
        Map<String, TreeSet<Integer>> res = new HashMap<>();
        for (Term term : result) {
            if (!res.containsKey(term.getTermName())) {
                res.put(term.getTermName(), new TreeSet<>());
            }
            Set<Integer> curSet = res.get(term.getTermName());
            curSet.add(term.getArguments().size());
        }
        return res;
    }
}
