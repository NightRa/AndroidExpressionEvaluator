package nightra.mycalculator.expression;

import fj.data.Option;

import static fj.data.Option.some;

public class Cos extends UnaryFunction {
    public Cos(Expr of) {
        super(of);
    }

    @Override
    protected Option<Double> evalUnary(double of) {
        return some(Math.cos(of));
    }

    public static Cos cosExpr(Expr of) {
        return new Cos(of);
    }

    @Override
    public String toString() {
        return "Cos(" + of + ")";
    }

    @Override
    public String prettyPrint() {
        return "Cos(" + of.prettyPrint() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cos cos = (Cos) o;
        if (!of.equals(cos.of)) return false;
        return true;
    }
}
