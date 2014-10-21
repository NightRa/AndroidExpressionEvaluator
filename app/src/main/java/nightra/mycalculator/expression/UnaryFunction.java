package nightra.mycalculator.expression;

import fj.data.Option;

public abstract class UnaryFunction extends Expr {
    public final Expr of;

    protected UnaryFunction(Expr of) {
        this.of = of;
    }

    @Override
    public Option<Double> eval() {
        return of.eval().bind(this::evalUnary);
    }

    protected abstract Option<Double> evalUnary(double of);
}
