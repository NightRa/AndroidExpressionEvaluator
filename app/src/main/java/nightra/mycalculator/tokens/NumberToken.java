package nightra.mycalculator.tokens;

import static nightra.mycalculator.expression.NumberLiteral.roundingShow;

public class NumberToken implements Token {
    public final double number;

    public NumberToken(double number) {
        this.number = number;
    }

    public static NumberToken numberToken(double number){
        return new NumberToken(number);
    }

    @Override
    public String toString() {
        return roundingShow(number);
    }

    /*GENERATED*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberToken that = (NumberToken) o;
        if (Double.compare(that.number, number) != 0) return false;
        return true;
    }
}
