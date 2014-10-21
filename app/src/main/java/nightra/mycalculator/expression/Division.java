package nightra.mycalculator.expression;

import fj.data.Option;

import static fj.data.Option.none;
import static fj.data.Option.some;

public class Division extends BinaryExpr {
    public Division(Expr left, Expr right) {
        super(left, right);
    }

    @Override
    protected Option<Double> evalBinary(double left, double right) {
        if(right == 0){
            return none();
        } else {
            return some(left / right);
        }
    }

    public static Division divExpr(Expr left, Expr right){
        return new Division(left, right);
    }

    @Override
    public String toString() {
        return "Division(" + left + "," + right + ")";
    }

    @Override
    public String prettyPrint() {
        return "(" + left.prettyPrint() + ")/(" + right.prettyPrint() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Division that = (Division) o;
        if (!left.equals(that.left)) return false;
        if (!right.equals(that.right)) return false;
        return true;
    }
}
