package nightra.mycalculator.states;

import fj.F;
import fj.data.Either;
import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.algorithm.ShantingYard;
import nightra.mycalculator.expression.Expr;
import nightra.mycalculator.tokens.BinaryOperationToken;
import nightra.mycalculator.tokens.Token;
import nightra.mycalculator.tokens.UnaryFunctionToken;

public abstract class State {
    public final List<Token> tokens;

    protected State(List<Token> tokens) {
        this.tokens = tokens;
    }

    public abstract Option<List<Token>> getFinalTokens();

    public abstract State handleDigit(int d);

    public abstract State handleDot();

    public abstract State handleBinaryOp(BinaryOperationToken op);

    public abstract State handleFunc(UnaryFunctionToken func);

    public abstract State handleLeftBrace();

    public abstract State handleRightBrace();

    public State handleClear() {
        return new EmptyState();
    }

    public State handleEval() {
        Option<Expr> ast = getFinalTokens().bind(ShantingYard::parse);
        Either<Errors,Expr> handledParse = ast.toEither(Errors.SyntaxError);
        Either<Errors, Double> result = handledParse.right().bind(expr -> expr.eval().<Errors>toEither(Errors.MathError));
        return result.either(ErrorState::new, PartialNumberState::new);
    }

    public String toString(String onEmpty, F<Errors, String> onError) {
        return tokens.map(Object::toString).foldLeft((acc, s) -> s + acc, "") + showCurrent(onEmpty,onError);
    }

    @Override
    public String toString() {
        return toString("Empty", err -> err.cata("Syntax Error", "Math error"));
    }

    protected abstract String showCurrent(String onEmpty, F<Errors, String> onError);
}
