package nightra.mycalculator.states;

import fj.F;
import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.tokens.BinaryOperationToken;
import nightra.mycalculator.tokens.NumberToken;
import nightra.mycalculator.tokens.Token;
import nightra.mycalculator.tokens.UnaryFunctionToken;

import static nightra.mycalculator.expression.NumberLiteral.isWhole;
import static nightra.mycalculator.expression.NumberLiteral.roundingShow;

public class PartialNumberState extends State {
    public final String partial;

    public PartialNumberState(List<Token> tokens, String partial) {
        super(tokens);
        this.partial = partial;
    }

    public PartialNumberState(List<Token> tokens, int digit) {
        this(tokens, String.valueOf(digit));
    }

    public PartialNumberState(double num) {
        this(List.nil(), roundingShow(num));
    }

    // Assuming PartialNumberState was constructed correctly
    private NumberToken toToken() {
        return new NumberToken(parse());
    }

    private double parse() {
        return Double.parseDouble(partial);
    }

    private List<Token> nextTokens() {
        return List.cons(toToken(), tokens);
    }

    @Override
    public Option<List<Token>> getFinalTokens() {
        return Option.some(nextTokens().reverse());
    }

    @Override
    public State handleDigit(int d) {
        return new PartialNumberState(tokens, partial + d);
    }

    @Override
    public State handleDot() {
        if (partial.endsWith(".")) {
            return this;
        } else {
            double num = parse();
            if (isWhole(num)) {
                return new PartialNumberState(tokens, roundingShow(num) + ".").handleDot();
            } else {
                return this;
            }
        }
    }

    @Override
    public State handleBinaryOp(BinaryOperationToken op) {
        return new BinaryOpState(nextTokens(), op);
    }

    @Override
    public State handleFunc(UnaryFunctionToken func) {
        return handleBinaryOp(BinaryOperationToken.Multiply).handleFunc(func);
    }

    @Override
    public State handleLeftBrace() {
        return handleBinaryOp(BinaryOperationToken.Multiply).handleLeftBrace();
    }

    @Override
    public State handleRightBrace() {
        return new RightBraceState(nextTokens());
    }

    @Override
    protected String showCurrent(String onEmpty, F<Errors, String> onError) {
        return partial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartialNumberState that = (PartialNumberState) o;
        if (!partial.equals(that.partial)) return false;
        return true;
    }

}
