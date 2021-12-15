import java.util.ArrayList;

public class Parser {

    private static final String VARIABLE_REGEX = "\\[[[a-zA-Z],]*[a-zA-Z]\\]";
    private static final String RULE_SEPARATOR = "->";
    private static final String TERM_NAME_REGEX = "[a-zA-Z]+";
    private static final int RULE_SEPARATOR_LENGTH = 2;
    private static final int STRING_START = 0;

    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String COMMA = ",";

    private ArrayList<Term> variables;
    private ArrayList<String> inputRules;

    public Parser(ArrayList<String> rules) {
        this.inputRules = rules;
    }

    public TRS parse() throws SyntaxError {
        ArrayList<Term> variables = parseVariables(inputRules.get(0));
        this.variables = variables;
        inputRules.remove(0);
        ArrayList<Rule> resultRules = new ArrayList<>();
        for (String rule : inputRules) {
            resultRules.add(parseRule(rule));
        }
        return new TRS(this.variables, resultRules);
    }

    public Rule parseRule(String rule) throws SyntaxError {
        int indexOfRuleSeparator = rule.indexOf(RULE_SEPARATOR);
        if (indexOfRuleSeparator == -1) {
            throw new SyntaxError("NO RULE SEPARATOR IN THE RULE");
        }
        String ruleLeftSide = rule.substring(STRING_START, indexOfRuleSeparator);
        String ruleRightSide = rule.substring(indexOfRuleSeparator + RULE_SEPARATOR_LENGTH);

        return new Rule(parseTerm(ruleLeftSide), parseTerm(ruleRightSide));
    }

    public Term parseTerm(String term) throws SyntaxError{
        int openBracketStartIndex = term.indexOf(OPEN_BRACKET);
        if (openBracketStartIndex == -1) {
            if (!term.matches(TERM_NAME_REGEX)) { //возможно лишняя проверка
                throw new SyntaxError("ERROR TERM NAME");
            }
            if (isVariable(term)) {
                return new Term(term, Term.TermType.VARIABLE);
            } else {
                return new Term(term, Term.TermType.CONSTANT);
            }
        }

        String termName = term.substring(0, openBracketStartIndex);
        String constructor = term.substring(openBracketStartIndex);

        if (!termName.matches(TERM_NAME_REGEX)) {
            throw new SyntaxError("ERROR TERM NAME");
        }

        Term resultTerm = new Term(termName, Term.TermType.FUNCTION);
        ArrayList<Term> terms = new ArrayList<>();
        if (!isCloseBracket(constructor.charAt(constructor.length() - 1))) {
            System.out.println(constructor.charAt(constructor.length()-1));
            throw new SyntaxError("NO CLOSE BRACKET IN TERM CONSTRUCTOR");
        }
        //delete open bracket
        constructor = constructor.substring(1);
        //delete close bracket
        constructor = constructor.substring(0, constructor.length() - 1);
        int commaStartIndex = constructor.indexOf(COMMA);
        if (commaStartIndex == -1) {
            terms.add(parseTerm(constructor));
        } else {
            while (commaStartIndex != -1) {
                terms.add(parseTerm(constructor.substring(0, commaStartIndex)));
                constructor = constructor.substring(commaStartIndex + 1);
                commaStartIndex = constructor.indexOf(COMMA);
            }
            terms.add(parseTerm(constructor));
        }
        resultTerm.setArguments(terms);
        return resultTerm;
    }

    protected boolean isCloseBracket(Character c) {
        return Character.toString(c).equals(CLOSE_BRACKET);
    }

    private boolean isVariable(String term) {
        for(Term t : variables) {
            if (t.getTermName().equals(term)) {
                return true;
            }
        }
        return false;
    }


    public static ArrayList<Term> parseVariables(String variables) throws SyntaxError{
        if (variables == null) {
            throw new SyntaxError("NULL VARIABLES");
        }
        variables = variables.replaceAll("\\s", "");
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
