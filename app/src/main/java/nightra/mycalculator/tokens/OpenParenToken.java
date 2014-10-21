package nightra.mycalculator.tokens;

public class OpenParenToken implements OperationToken {
    public final static OpenParenToken token = new OpenParenToken();

    private OpenParenToken(){}

    @Override
    public String toString() {
        return "(";
    }

    @Override
    public int precedence() {
        return 0;
    }
}
