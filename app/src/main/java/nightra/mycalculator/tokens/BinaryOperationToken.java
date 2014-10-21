package nightra.mycalculator.tokens;

public enum BinaryOperationToken implements OperationToken {
    Plus("+", 2),Minus("-",2),Multiply("*", 3),Devide("/", 3);

    private final String rep;
    private final int precedence;

    BinaryOperationToken(String rep, int precedence) {
        this.rep = rep;
        this.precedence = precedence;
    }

    public int precedence(){
        return precedence;
    }

    @Override
    public String toString() {
        return rep;
    }
}
