package nightra.mycalculator.expression;

import fj.data.Option;

import static fj.data.Option.some;

public class NumberLiteral extends Expr {
    public final double num;

    public NumberLiteral(double num) {
        this.num = num;
    }

    public static NumberLiteral numExpr(double num){
        return new NumberLiteral(num);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberLiteral that = (NumberLiteral) o;
        if (Double.compare(that.num, num) != 0) return false;
        return true;
    }

    @Override
    public Option<Double> eval() {
        return some(num);
    }

    @Override
    public String toString() {
        return "NumberLiteral(" + num + ")";
    }

    @Override
    public String prettyPrint() {
        return roundingShow(num);
    }

    public static boolean isWhole(double num){
        return (int) num == num;
    }

    public static String roundingShow(double num){
        if(isWhole(num))
            return String.valueOf((int) num);
        else
            return String.valueOf(num);
    }
}
