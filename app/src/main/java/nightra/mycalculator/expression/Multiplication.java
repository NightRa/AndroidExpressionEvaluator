package nightra.mycalculator.expression;

import fj.data.Option;

import static fj.data.Option.some;

public class Multiplication extends BinaryExpr {
    public Multiplication(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    protected Option<Double> evalBinary(double left, double right) {
        return some(left * right);
    }

    public static Multiplication multiplyExpr(Expr left, Expr right){
        return new Multiplication(left,right);
    }

    @Override
    public String toString() {
        return "Multiplication(" + left + "," + right + ")";
    }

    @Override
    public String prettyPrint() {
        return "(" + left.prettyPrint() + ")*(" + right.prettyPrint() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Multiplication that = (Multiplication) o;
        if (!left.equals(that.left)) return false;
        if (!right.equals(that.right)) return false;
        return true;
    }
}
