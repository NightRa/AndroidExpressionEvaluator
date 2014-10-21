package nightra.mycalculator.states;

import fj.F;
import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.tokens.BinaryOperationToken;
import nightra.mycalculator.tokens.Token;
import nightra.mycalculator.tokens.UnaryFunctionToken;

public class ErrorState extends State {
    private Errors error;

    public ErrorState(Errors error) {
        super(List.nil());
        this.error = error;
    }

    @Override
    public Option<List<Token>> getFinalTokens() {
        return Option.none();
    }

    private State nextState(){
        return new EmptyState();
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
        return nextState().handleLeftBrace();
    }

    @Override
    public State handleRightBrace() {
        return nextState().handleRightBrace();
    }

    @Override
    protected String showCurrent(String onEmpty, F<Errors, String> onError) {
        return onError.f(error);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorState that = (ErrorState) o;
        if (error != that.error) return false;
        return true;
    }
}
