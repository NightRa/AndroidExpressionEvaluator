package nightra.mycalculator.tokens;

public class CloseParenToken implements Token { // Shouldn't ever be pushed onto the operation stack
    public static final CloseParenToken token = new CloseParenToken();

    private CloseParenToken(){}

    @Override
    public String toString() {
        return ")";
    }
}
