package nightra.mycalculator.states;

import fj.F;
import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.tokens.BinaryOperationToken;
import nightra.mycalculator.tokens.CloseParenToken;
import nightra.mycalculator.tokens.Token;
import nightra.mycalculator.tokens.UnaryFunctionToken;

public class RightBraceState extends State {
    public RightBraceState(List<Token> tokens) {
        super(tokens);
    }

    @Override
    public Option<List<Token>> getFinalTokens() {
        return Option.some(nextTokens().reverse());
    }

    private List<Token> nextTokens(){
        return List.cons(CloseParenToken.token,tokens);
    }

    @Override
    public State handleDigit(int d) {
        return handleBinaryOp(BinaryOperationToken.Multiply).handleDigit(d);
    }

    @Override
    public State handleDot() {
        return this;
    }

    @Override
    public State handleBinaryOp(BinaryOperationToken op) {
        return new BinaryOpState(nextTokens(),op);
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
        return ")";
    }
}
