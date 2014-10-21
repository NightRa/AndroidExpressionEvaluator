package nightra.mycalculator.expression;

import fj.data.Option;

public abstract class BinaryExpr extends Expr {
    public final Expr left;
    public final Expr right;

    protected BinaryExpr(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Option<Double> eval() {
        return left.eval().bind(l -> right.eval().bind(r -> evalBinary(l,r)));
    }

    protected abstract Option<Double> evalBinary(double left, double right);
}
