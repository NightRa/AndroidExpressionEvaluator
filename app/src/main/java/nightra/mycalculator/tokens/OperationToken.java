package nightra.mycalculator.tokens;

public interface OperationToken extends Token {
    public int precedence();
}
