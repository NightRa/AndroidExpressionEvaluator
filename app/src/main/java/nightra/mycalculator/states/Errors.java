package nightra.mycalculator.states;

public enum Errors {
    SyntaxError, MathError;

    public <A> A cata(A onSyntaxError, A onMathError){
        if(this == SyntaxError)
            return onSyntaxError;
        else
            return onMathError;
    }
}
