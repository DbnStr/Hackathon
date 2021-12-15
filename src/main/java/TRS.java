import java.util.*;

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
        ArrayList<Term> result = new ArrayList<>();
        for (Rule rule: rules) {
            result.addAll(rule.getAllTerms());
        }

        Set<String> names = new HashSet<>();
        for (Term term: result) {
            if (term.getTermType() == Term.TermType.FUNCTION)
                names.add(term.getTermName());
        }

        return new ArrayList<>(names);
    }

    public ArrayList<String> getAllVariablesNames() {
        Set<String> result = new HashSet<>();
        for (Term variable : variables) {
            result.add(variable.getTermName());
        }
        return new ArrayList<>(result);
    }

    private static void swap(ArrayList<String> arr, int i, int j) {
        Collections.swap(arr, i, j);
    }

    private static void permutationsInternal(ArrayList<String> arr,ArrayList<ArrayList<String>> permutations, int index){
        if(index >= arr.size() - 1){
            permutations.add(new ArrayList<>(arr));
            return;
        }

        for(int i = index; i < arr.size(); i++){
            swap(arr, i, index);
            permutationsInternal(arr, permutations, index + 1);
            swap(arr, i, index);
        }
    }

    public ArrayList<ArrayList<String>> generatePermutations(ArrayList<String> sequence) {
        ArrayList<ArrayList<String>> permutations = new ArrayList<>();
        permutationsInternal(sequence, permutations, 0);
        return permutations;
    }

    //расставляем знаки для [f1, f2, f3] как f1 < f2 < f3
    public ArrayList<ArrayList<String>> getFunctionsNamesPermutations() {
        ArrayList<String> functions = getAllFunctionsNames();

        return generatePermutations(functions);
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
