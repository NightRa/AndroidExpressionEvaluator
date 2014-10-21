package nightra.mycalculator.expression;

import fj.data.Option;

import static fj.data.Option.some;

public class Minus extends BinaryExpr {
    public Minus(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    protected Option<Double> evalBinary(double left, double right) {
        return some(left - right);
    }

    public static Minus minusExpr(Expr left, Expr right){
        return new Minus(left,right);
    }

    @Override
    public String toString() {
        return "Minus(" + left + "," + right + ")";
    }

    @Override
    public String prettyPrint() {
        return "(" + left.prettyPrint() + ")-(" + right.prettyPrint() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Minus that = (Minus) o;
        if (!left.equals(that.left)) return false;
        if (!right.equals(that.right)) return false;
        return true;
    }
}
