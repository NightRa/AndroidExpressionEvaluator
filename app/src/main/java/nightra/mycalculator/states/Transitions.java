package nightra.mycalculator.states;

import fj.F;
import nightra.mycalculator.tokens.BinaryOperationToken;
import nightra.mycalculator.tokens.UnaryFunctionToken;

public class Transitions {
    public static F<State, State> handleDigit(int d) {
        return state -> state.handleDigit(d);
    }
    public static final F<State, State> handleDot = State::handleDot;
    public static final F<State, State> handleClear = State::handleClear;
    public static final F<State, State> handleEval = State::handleEval;
    public static final F<State, State> handleLeftBrace = State::handleLeftBrace;
    public static final F<State, State> handleRightBrace = State::handleRightBrace;
    public static final F<State, State> handlePlus = state -> state.handleBinaryOp(BinaryOperationToken.Plus);
    public static final F<State, State> handleMinus = state -> state.handleBinaryOp(BinaryOperationToken.Minus);
    public static final F<State, State> handleMultiply = state -> state.handleBinaryOp(BinaryOperationToken.Multiply);
    public static final F<State, State> handleDivision = state -> state.handleBinaryOp(BinaryOperationToken.Devide);
    public static final F<State, State> handleSqrt = state -> state.handleFunc(UnaryFunctionToken.Sqrt);
    public static final F<State, State> handleSin = state -> state.handleFunc(UnaryFunctionToken.Sin);
    public static final F<State, State> handleCos = state -> state.handleFunc(UnaryFunctionToken.Cos);
}
