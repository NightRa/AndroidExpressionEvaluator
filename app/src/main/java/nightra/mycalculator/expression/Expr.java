package nightra.mycalculator.expression;

import fj.data.Option;

public abstract class Expr {
    public abstract String toString();
    public abstract String prettyPrint();
    public abstract boolean equals(Object o);
    public abstract Option<Double> eval();
}
