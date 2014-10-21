package nightra.tests.mycalculator.algorithm;

import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.expression.*;
import nightra.mycalculator.tokens.CloseParenToken;
import nightra.mycalculator.tokens.OpenParenToken;
import nightra.mycalculator.tokens.Token;
import nightra.tests.test.Test;
import nightra.tests.test.Tests;

import static fj.data.List.cons;
import static fj.data.Option.some;
import static nightra.mycalculator.algorithm.ShantingYard.parse;
import static nightra.mycalculator.expression.Cos.cosExpr;
import static nightra.mycalculator.expression.Division.divExpr;
import static nightra.mycalculator.expression.Minus.minusExpr;
import static nightra.mycalculator.expression.Multiplication.multiplyExpr;
import static nightra.mycalculator.expression.NumberLiteral.numExpr;
import static nightra.mycalculator.expression.Plus.plusExpr;
import static nightra.mycalculator.expression.Sin.sinExpr;
import static nightra.mycalculator.expression.Sqrt.sqrtExpr;
import static nightra.mycalculator.tokens.BinaryOperationToken.*;
import static nightra.mycalculator.tokens.NumberToken.numberToken;
import static nightra.mycalculator.tokens.UnaryFunctionToken.Cos;
import static nightra.mycalculator.tokens.UnaryFunctionToken.Sin;
import static nightra.mycalculator.tokens.UnaryFunctionToken.Sqrt;

public class ParseTest {
    public static void main(String[] args) {
        parseTests().run();
    }

    public static Tests parseTests() {
        return new Tests(
                literalTest(),
                simpleExpressionTest(),
                precedenceTest1(),
                precedenceTest2(),
                parensTest(),
                funcTest1(),
                funcTest2(),
                funcTest3(),
                funcTest4(),
                funcTest5(),
                complexParensTest()
        ).and(enclosingParensChecks());
    }

    private static final List<Token> exampleInput1 = List.single(numberToken(5));

    public static Test literalTest() {
        List<Token> input = exampleInput1;
        Option<Expr> expected = some(new NumberLiteral(5));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Parsing a number literal token: 5");
    }

    public static Test enclosingParensProperty(List<Token> input1) {
        List<Token> input2 = cons(OpenParenToken.token, input1).append(List.single(CloseParenToken.token));
        Option<Expr> res1 = parse(input1);
        Option<Expr> res2 = parse(input2);
        return Test.equals(res1, res2, "Enclosing a whole expression in parens should result in the same parse tree: " + res1.map(Expr::prettyPrint));
    }

    @SuppressWarnings("unchecked")
    public static Tests enclosingParensChecks() {
        return new Tests(List.list(exampleInput1, exampleInput2, exampleInput3, exampleInput4, exampleInput5, exampleInput6, exampleInput7, exampleInput8, exampleInput9, exampleInput10, exampleInput11)
                .map(ParseTest::enclosingParensProperty));
    }

    private static final List<Token> exampleInput2 = List.list(numberToken(1), Plus, numberToken(2), Minus, numberToken(3));

    public static Test simpleExpressionTest() {
        List<Token> input = exampleInput2;
        Option<Expr> expected = some(minusExpr(plusExpr(numExpr(1), numExpr(2)), numExpr(3)));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Parsing a simple expression with the same precedence: 1 + 2 - 3 == ((1+2)-3)");
    }

    private static final List<Token> exampleInput3 = List.list(numberToken(1), Plus, numberToken(2), Multiply, numberToken(3));

    public static Test precedenceTest1() {
        List<Token> input = exampleInput3;
        Option<Expr> expected = some(plusExpr(numExpr(1), multiplyExpr(numExpr(2), numExpr(3))));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Precedence Test: * before +: 1 + 2 * 3 == (1+(2*3))");
    }

    private static final List<Token> exampleInput4 = List.list(numberToken(1), Plus, numberToken(2), Minus, numberToken(3), Multiply, numberToken(4), Devide, numberToken(5));

    public static Test precedenceTest2() {
        // 1 + 2 - 3 * 4 / 5
        // (1 + 2) - ((3*4)/5)
        List<Token> input = exampleInput4;
        Option<Expr> expected = some(minusExpr(plusExpr(numExpr(1), numExpr(2)), divExpr(multiplyExpr(numExpr(3), numExpr(4)), numExpr(5))));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Precedence Test + left associativity: 1 + 2 - 3 * 4 / 5 == (1 + 2) - ((3*4)/5)");
    }

    private static final List<Token> exampleInput5 = List.list(numberToken(2), Multiply, OpenParenToken.token, numberToken(3), Plus, numberToken(4), CloseParenToken.token);

    public static Test parensTest() {
        // 2 * (3 + 4)
        List<Token> input = exampleInput5;
        Option<Expr> expected = some(multiplyExpr(numExpr(2), plusExpr(numExpr(3), numExpr(4))));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Parens test: 2 * (3 + 4)");
    }

    private static final List<Token> exampleInput6 = List.list(Sqrt, OpenParenToken.token, numberToken(3), Plus, numberToken(5), Multiply, numberToken(2.556), CloseParenToken.token);

    public static Test funcTest1() {
        // sqrt(3+5*2.556)
        List<Token> input = exampleInput6;
        Option<Expr> expected = some(sqrtExpr(plusExpr(numExpr(3), multiplyExpr(numExpr(5), numExpr(2.556)))));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Function parsing test: sqrt(3+5*2.556)");
    }

    private static final List<Token> exampleInput7 = List.list(Sqrt, OpenParenToken.token, Sqrt, OpenParenToken.token, numberToken(3), Plus, numberToken(5), Multiply, numberToken(2.556), CloseParenToken.token, CloseParenToken.token);

    public static Test funcTest2() {
        // sqrt(sqrt(3+5*2.556))
        List<Token> input = exampleInput7;
        Option<Expr> expected = some(sqrtExpr(sqrtExpr(plusExpr(numExpr(3), multiplyExpr(numExpr(5), numExpr(2.556))))));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Function parsing test: sqrt(sqrt(3+5*2.556))");
    }

    private static final List<Token> exampleInput8 = List.list(Sqrt, OpenParenToken.token, numberToken(4), CloseParenToken.token);

    public static Test funcTest3() {
        // sqrt(4)
        List<Token> input = exampleInput8;
        Option<Expr> expected = some(sqrtExpr(numExpr(4)));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Simple function parsing test: sqrt(4)");
    }

    private static final List<Token> exampleInput9 = List.list(Sin, OpenParenToken.token, numberToken(4), CloseParenToken.token);

    public static Test funcTest4() {
        // sin(4)
        List<Token> input = exampleInput9;
        Option<Expr> expected = some(sinExpr(numExpr(4)));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Simple function parsing test: sin(4)");
    }

    private static final List<Token> exampleInput10 = List.list(Cos, OpenParenToken.token, numberToken(4), CloseParenToken.token);

    public static Test funcTest5() {
        // sin(4)
        List<Token> input = exampleInput10;
        Option<Expr> expected = some(cosExpr(numExpr(4)));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Simple function parsing test: cos(4)");
    }

    private static final List<Token> exampleInput11 =
            List.list(OpenParenToken.token, numberToken(1), Multiply, OpenParenToken.token, OpenParenToken.token, numberToken(2), Plus,
                    numberToken(3), Multiply, numberToken(4), CloseParenToken.token, Multiply, numberToken(5), CloseParenToken.token, CloseParenToken.token);

    public static Test complexParensTest() {
        // (1*((2+3*4)*5)) == (1*((2+(3*4))*5))
        List<Token> input = exampleInput11;
        Option<Expr> expected = some(multiplyExpr(numExpr(1), multiplyExpr(plusExpr(numExpr(2), multiplyExpr(numExpr(3), numExpr(4))), numExpr(5))));
        Option<Expr> actual = parse(input);
        return Test.equals(actual, expected, "Complex parens test: (1*((2+3*4)*5))");
    }
}
