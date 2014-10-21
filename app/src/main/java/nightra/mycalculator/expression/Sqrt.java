package nightra.mycalculator.expression;

import fj.data.Option;

import static fj.data.Option.none;
import static fj.data.Option.some;

public class Sqrt extends UnaryFunction {
    public Sqrt(Expr of) {
        super(of);
    }

    @Override
    protected Option<Double> evalUnary(double of) {
        if(of < 0){
            return none();
        } else {
            return some(Math.sqrt(of));
        }
    }

    public static Sqrt sqrtExpr(Expr of){
        return new Sqrt(of);
    }

    @Override
    public String toString() {
        return "Sqrt(" + of + ")";
    }

    @Override
    public String prettyPrint() {
        return "Sqrt(" + of.prettyPrint() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sqrt sqrt = (Sqrt) o;
        if (!of.equals(sqrt.of)) return false;
        return true;
    }
}
