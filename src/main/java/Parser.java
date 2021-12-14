import java.util.ArrayList;

public class Parser {

    private static final String VARIABLE_REGEX = "\\[[[a-z],]*[a-z]\\]";
    public static TRS parse(ArrayList<String> rules) throws SyntaxError {

        for(String rule : rules) {

        }
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
