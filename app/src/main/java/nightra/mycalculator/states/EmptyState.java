package nightra.mycalculator.states;

import fj.F;
import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.tokens.BinaryOperationToken;
import nightra.mycalculator.tokens.Token;
import nightra.mycalculator.tokens.UnaryFunctionToken;

public class EmptyState extends State {
    public EmptyState() {
        super(List.nil());
    }

    public static EmptyState emptyState(){
        return new EmptyState();
    }

    @Override
    public Option<List<Token>> getFinalTokens() {
        return Option.none();
    }

    @Override
    public State handleDigit(int d) {
        return new PartialNumberState(List.nil(),d);
    }

    @Override
    public State handleDot() {
        return this;
    }

    @Override
    public State handleBinaryOp(BinaryOperationToken op) {
        return this;
    }

    @Override
    public State handleFunc(UnaryFunctionToken func) {
        return new FuncState(tokens,func);
    }

    @Override
    public State handleLeftBrace() {
        return new LeftBraceState(tokens);
    }

    @Override
    public State handleRightBrace() {
        return this;
    }

    @Override
    protected String showCurrent(String onEmpty, F<Errors, String> onError) {
        return onEmpty;
    }
}
