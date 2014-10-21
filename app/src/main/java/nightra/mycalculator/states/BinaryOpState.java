package nightra.mycalculator.states;

import fj.F;
import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.tokens.BinaryOperationToken;
import nightra.mycalculator.tokens.Token;
import nightra.mycalculator.tokens.UnaryFunctionToken;

public class BinaryOpState extends State {
    public final BinaryOperationToken op;

    public BinaryOpState(List<Token> tokens, BinaryOperationToken op) {
        super(tokens);
        this.op = op;
    }



    @Override
    public Option<List<Token>> getFinalTokens() {
        return Option.none();
    }

    private List<Token> nextTokens() {
        return List.cons(op, tokens);
    }

    @Override
    public State handleDigit(int d) {
        return new PartialNumberState(nextTokens(),d);
    }

    @Override
    public State handleDot() {
        return this;
    }

    @Override
    public State handleBinaryOp(BinaryOperationToken op) {
        return new BinaryOpState(tokens,op); // Change the op.
    }

    @Override
    public State handleFunc(UnaryFunctionToken func) {
        return new FuncState(nextTokens(),func);
    }

    @Override
    public State handleLeftBrace() {
        return new LeftBraceState(nextTokens());
    }

    @Override
    public State handleRightBrace() {
        return this;
    }

    @Override
    protected String showCurrent(String onEmpty, F<Errors, String> onError) {
        return op.toString();
    }
}
