package nightra.mycalculator.expression;

import fj.data.Option;

import static fj.data.Option.some;

public class Sin extends UnaryFunction {
    public Sin(Expr of) {
        super(of);
    }

    @Override
    protected Option<Double> evalUnary(double of) {
        return some(Math.sin(of));
    }

    public static Sin sinExpr(Expr of){
        return new Sin(of);
    }

    @Override
    public String toString() {
        return "Sin(" + of + ")";
    }

    @Override
    public String prettyPrint() {
        return "Sin(" + of.prettyPrint() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sin sin = (Sin) o;
        if (!of.equals(sin.of)) return false;
        return true;
    }
}
