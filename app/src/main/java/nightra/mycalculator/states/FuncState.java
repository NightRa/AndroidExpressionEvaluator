package nightra.mycalculator.states;

import fj.F;
import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.tokens.BinaryOperationToken;
import nightra.mycalculator.tokens.Token;
import nightra.mycalculator.tokens.UnaryFunctionToken;

public class FuncState extends State {
    public final UnaryFunctionToken func;

    public FuncState(List<Token> tokens, UnaryFunctionToken func) {
        super(tokens);
        this.func = func;
    }

    private List<Token> nextTokens() {
        return List.cons(func, tokens);
    }

    private State nextState() {
        return new LeftBraceState(nextTokens());
    }

    @Override
    public Option<List<Token>> getFinalTokens() {
        return Option.none();
    }

    @Override
    public State handleDigit(int d) {
        return nextState().handleDigit(d);
    }

    @Override
    public State handleDot() {
        return nextState().handleDot();
    }

    @Override
    public State handleBinaryOp(BinaryOperationToken op) {
        return nextState().handleBinaryOp(op);
    }

    @Override
    public State handleFunc(UnaryFunctionToken func) {
        return nextState().handleFunc(func);
    }

    @Override
    public State handleLeftBrace() {
        return nextState().handleRightBrace();
    }

    @Override
    public State handleRightBrace() {
        return nextState().handleRightBrace();
    }

    @Override
    protected String showCurrent(String onEmpty, F<Errors, String> onError) {
        return func.toString() + "(";
    }
}
