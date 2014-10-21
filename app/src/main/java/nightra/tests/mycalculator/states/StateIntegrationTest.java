package nightra.tests.mycalculator.states;

import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.states.*;
import nightra.mycalculator.tokens.*;
import nightra.tests.test.Test;
import nightra.tests.test.Tests;

import static fj.data.Option.none;
import static fj.data.Option.some;
import static nightra.mycalculator.tokens.BinaryOperationToken.*;
import static nightra.mycalculator.tokens.NumberToken.numberToken;
import static nightra.mycalculator.tokens.UnaryFunctionToken.Sqrt;

public class StateIntegrationTest {
  public static void main(String[] args) {
    stateTests().run();
  }

  public static Tests stateTests() {
    return new Tests(
            emptyExpressionTest(),
            numberTest(),
            simpleExpressionTest(),
            nonWholeNumberTest(),
            complexExpressionTest(),
            braceMultiplicationTest(),
            syntaxErrorEvalTest(),
            dotAfterDotZeroPartialNumberTest(),
            dotAfterDotNotZeroPartialNumberTest()
    );
  }

  public static Test emptyExpressionTest() {
    Option<List<Token>> expected = none();
    Option<List<Token>> actual = new EmptyState().getFinalTokens();
    return Test.equals(expected, actual, "The empty state should represent an invalid token list.");
  }

  public static Test numberTest() {
    Option<List<Token>> expected = some(List.single(new NumberToken(32)));
    Option<List<Token>> actual = new EmptyState().handleDigit(3).handleDigit(2).getFinalTokens();
    return Test.equals(expected, actual, "Consequent digits should become a number.");
  }

  public static Test simpleExpressionTest() {
    Option<List<Token>> expected = some(List.list(numberToken(12), Plus, numberToken(2), Multiply, numberToken(3)));

    Option<List<Token>> actual = new EmptyState().handleDigit(1).handleDigit(2).handleBinaryOp(Plus)
            .handleDigit(2).handleBinaryOp(Multiply).handleDigit(3)
            .getFinalTokens();

    return Test.equals(expected, actual, "Simple expression with binary operation tokenization.");
  }

  public static Test nonWholeNumberTest() {
    Option<List<Token>> expected = some(List.single(new NumberToken(12.5)));
    Option<List<Token>> actual = new EmptyState().handleDigit(1).handleDigit(2).handleDot().handleDigit(5)
            .getFinalTokens();
    return Test.equals(expected, actual, "Tokenizing non whole numbers.");
  }

  public static Test complexExpressionTest() {
    // sqrt(3+5*2.556)
    Option<List<Token>> expected = some(List.list(Sqrt, OpenParenToken.token, numberToken(3), Plus, numberToken(5), Multiply, numberToken(2.556), CloseParenToken.token));
    Option<List<Token>> actual = new EmptyState().handleFunc(Sqrt).handleDigit(3).handleBinaryOp(Plus).handleDigit(5).handleBinaryOp(Multiply)
            .handleDigit(2).handleDot().handleDigit(5).handleDigit(5).handleDigit(6).handleRightBrace()
            .getFinalTokens();
    return Test.equals(expected, actual, "Tokenizing an expression with a function.");
  }

  public static Test braceMultiplicationTest() {
    // (1+2)(3+4) -> (1+2)*(3+4)
    Option<List<Token>> expected = some(List.list(OpenParenToken.token, numberToken(1), Plus, numberToken(2), CloseParenToken.token, Multiply,
            OpenParenToken.token, numberToken(3), Plus, numberToken(4), CloseParenToken.token));
    Option<List<Token>> actual = new EmptyState().handleLeftBrace().handleDigit(1).handleBinaryOp(Plus).handleDigit(2).handleRightBrace()
            .handleLeftBrace().handleDigit(3).handleBinaryOp(Plus).handleDigit(4).handleRightBrace()
            .getFinalTokens();
    return Test.equals(expected, actual, "Two braced expressions should tokenize to multiplication.");
  }

  public static Test syntaxErrorEvalTest() {
    // Sqrt(
    State expected = new ErrorState(Errors.SyntaxError);
    State actual = new EmptyState().handleFunc(Sqrt).handleEval();
    return Test.equals(actual, expected, "Evaluating \"Sqrt(\" should result in a syntax error.");
  }

  public static Test dotAfterDotZeroPartialNumberTest() {
    State expected = new EmptyState().handleDigit(1).handleDigit(2).handleDot().handleDigit(1);
    State actual = new EmptyState().handleDigit(1).handleDigit(2).handleDot().handleDigit(0).handleDot().handleDigit(1);
    return Test.equals(actual, expected, "Pressing a dot after a .0 should remove the previous dot and continue from there.");
  }

  public static Test dotAfterDotNotZeroPartialNumberTest(){
    State expected = new EmptyState().handleDigit(1).handleDigit(2).handleDot().handleDigit(1);
    State actual = new EmptyState().handleDigit(1).handleDigit(2).handleDot().handleDigit(1).handleDot();
    return Test.equals(actual, expected, "Pressing a dot after a .1 (not zero) should not place a dot.");
  }

}
