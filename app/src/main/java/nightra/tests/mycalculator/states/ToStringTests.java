package nightra.tests.mycalculator.states;

import nightra.mycalculator.states.EmptyState;
import nightra.mycalculator.states.PartialNumberState;
import nightra.mycalculator.states.State;
import nightra.mycalculator.tokens.BinaryOperationToken;
import nightra.mycalculator.tokens.UnaryFunctionToken;
import nightra.tests.test.Test;
import nightra.tests.test.Tests;

import static nightra.mycalculator.states.EmptyState.emptyState;

public class ToStringTests {
    public static void main(String[] args) {
        toStringTests().run();
    }

    public static Tests toStringTests() {
        return new Tests(
                testNumberToString(),
                testFuncToString(),
                testPartialNumberCreation()
        );
    }

    public static Test testNumberToString() {
        State input = emptyState().handleDigit(1).handleDigit(2).handleBinaryOp(BinaryOperationToken.Plus).handleDigit(3);
        String expected = "12+3";
        String actual = input.toString();
        return Test.equals(actual, expected, "The state 12+3 should be rendered correctly");
    }

    public static Test testFuncToString() {
        State input = emptyState().handleFunc(UnaryFunctionToken.Sqrt);
        String expected = "Sqrt(";
        String actual = input.toString();
        return Test.equals(actual, expected, "The state Sqrt should be rendered as \"Sqrt(\"");
    }

    public static Test testPartialNumberCreation(){
        State input = new PartialNumberState(12.0);
        String expected = "12";
        String actual = input.toString();
        return Test.equals(actual,expected, "Creating a partial number from a double should round it correctly");

    }
}
