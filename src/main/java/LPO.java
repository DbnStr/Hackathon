public class LPO {
    public static boolean checkTerminating(TRS trs) {
        Rule firstRule = trs.getRules().get(0);
        if (firstRule.getLefTerm().getTermType() != Term.TermType.FUNCTION) {
            return false;
        }
        for (Rule rule : trs.getRules()) {
            Term rightTerm = rule.getRightTerm();
        }
    }
}
