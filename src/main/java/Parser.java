import java.util.ArrayList;

public class Parser {

    private static final String VARIABLE_REGEX = "\\[[[a-z],]*[a-z]\\]";
    private static final String RULE_SEPARATOR = "->";
    private static final int RULE_SEPARATOR_LENGTH = 2;
    private static final int STRING_START = 0;

    private ArrayList<Term> variables;
    private ArrayList<String> inputRules;

    public Parser(ArrayList<String> rules) {
        this.inputRules = rules;
    }

    public TRS parse() throws SyntaxError {
        ArrayList<Term> variables = parseVariables(inputRules.get(0));
        inputRules.remove(0);
        ArrayList<Rule> resultRules = new ArrayList<>();
        for (String rule : inputRules) {
            resultRules.add(parseRule(rule));
        }
        return new TRS(variables, resultRules);
    }

    public Rule parseRule(String rule) throws SyntaxError {
        int indexOfRuleSeparator = rule.indexOf(RULE_SEPARATOR);
        if (indexOfRuleSeparator == -1) {
            throw new SyntaxError("NO RULE SEPARATOR IN THE RULE");
        }
        String ruleRightSide = rule.substring(STRING_START, indexOfRuleSeparator);
        String ruleLeftSide = rule.substring(indexOfRuleSeparator + RULE_SEPARATOR_LENGTH);

        return new Rule(parseTerm(ruleLeftSide), parseTerm(ruleRightSide));
    }

    public Term parseTerm(String term) {
        StringBuilder t = new StringBuilder(term);

        return new Term();
    }


    public static ArrayList<Term> parseVariables(String variables) throws SyntaxError{
        if (variables == null) {
            throw new SyntaxError("NULL VARIABLES");
        }
        variables.replaceAll("\\s", "");
        if (!variables.matches(VARIABLE_REGEX)) {
            throw new SyntaxError("UNCORRECTED VARIABLE STRING");
        }
        ArrayList<Term> res = new ArrayList<>();
        StringBuilder result = new StringBuilder(variables);
        result.deleteCharAt(0);
        result.deleteCharAt(result.length() - 1);
        while (result.length() !=0) {
            res.add(new Term(Character.toString(result.charAt(0)), Term.TermType.VARIABLE));
            result.deleteCharAt(0);
            if (result.length() != 0) {
                result.deleteCharAt(0);
            }
        }
        return res;
    }

}
