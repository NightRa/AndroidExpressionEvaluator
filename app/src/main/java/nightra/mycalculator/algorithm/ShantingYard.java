package nightra.mycalculator.algorithm;

import fj.P2;
import fj.data.List;
import fj.data.Option;
import nightra.mycalculator.expression.*;
import nightra.mycalculator.tokens.*;

import static fj.data.List.cons;
import static fj.data.Option.none;
import static fj.data.Option.some;

public class ShantingYard {
    public static Option<Expr> parse(List<Token> tokens) {
        return parseGo(tokens, List.nil(), List.nil());
    }

    public static Option<Expr> parseGo(List<Token> tokens, List<OperationToken> operations, List<Expr> out) {
        if (!tokens.isEmpty()) {
            Token tokensHead = tokens.head();
            List<Token> tokensTail = tokens.tail();
            if (tokensHead instanceof NumberToken) {
                NumberLiteral lit = new NumberLiteral(((NumberToken) tokensHead).number);
                return parseGo(tokensTail, operations, cons(lit, out));
            } else if (tokensHead instanceof OpenParenToken) {
                return parseGo(tokensTail, cons((OperationToken) tokensHead, operations), out);
            } else if (tokensHead instanceof CloseParenToken) {
                // split when open brace is encountered.
                P2<List<OperationToken>, List<OperationToken>> broken = operations.breakk(op -> op instanceof OpenParenToken);
                List<OperationToken> operationsToBeApplied = broken._1();
                List<OperationToken> afterOps = broken._2();
                if (afterOps.isEmpty() || afterOps.head() != OpenParenToken.token) {
                    // No opening brace.
                    return none();
                } else if (afterOps.tail().isNotEmpty() && afterOps.tail().head() instanceof UnaryFunctionToken) {
                    // apply all ops in the parens, and then apply the unary func.
                    return applyOpsStack(operationsToBeApplied, out)
                            .bind(out2 -> {
                                if (out2.isEmpty()) {
                                    return none();
                                } else {
                                    Expr expr = out2.head();
                                    List<Expr> rest = out2.tail();
                                    return parseGo(tokensTail, afterOps.tail().tail() /*drop brace and func*/, cons(((UnaryFunctionToken)afterOps.tail().head()).toExpr(expr), rest));
                                }
                            });
                } else {
                    // just a normal brace pair
                    // apply all operations between the braces.
                    return applyOpsStack(operationsToBeApplied, out)
                            .bind(out2 -> parseGo(tokensTail, afterOps.tail()/*drop brace*/, out2));
                }
            } else if(tokens.head() instanceof UnaryFunctionToken){
                return parseGo(tokensTail, cons((UnaryFunctionToken) tokens.head(), operations),out);
            } else if (tokens.head() instanceof OperationToken) {
                OperationToken currentOp = (OperationToken) tokens.head();
                // a binary operation.
                P2<List<OperationToken>, List<OperationToken>> broken = operations.breakk(op -> currentOp.precedence() > op.precedence());
                List<OperationToken> operationsToBeApplied = broken._1();
                List<OperationToken> afterOps = broken._2();
                return applyOpsStack(operationsToBeApplied, out).bind(out2 -> parseGo(tokensTail, cons(currentOp, afterOps), out2));
            } else {
                throw new IllegalStateException("New operation type which isn't checked for.");
            }
        } else {
            return applyOpsStack(operations, out).bind(l -> {
                if (l.length() == 1) {
                    return some(l.head());
                } else {
                    return none();
                }
            });
        }
    }

    public static Option<List<Expr>> applyOpsStack(List<OperationToken> ops, List<Expr> out) {
        if (ops.isEmpty()) {
            return some(out);
        } else if (ops.isNotEmpty() && out.isNotEmpty() && out.tail().isNotEmpty()) {
            Expr expr2 = out.head();
            Expr expr1 = out.tail().head();
            List<Expr> rest = out.drop(2);
            if (ops.head() == BinaryOperationToken.Plus) {
                return applyOpsStack(ops.tail(), cons(new Plus(expr1, expr2), rest));
            } else if (ops.head() == BinaryOperationToken.Minus) {
                return applyOpsStack(ops.tail(), cons(new Minus(expr1, expr2), rest));
            } else if (ops.head() == BinaryOperationToken.Multiply) {
                return applyOpsStack(ops.tail(), cons(new Multiplication(expr1, expr2), rest));
            } else if (ops.head() == BinaryOperationToken.Devide) {
                return applyOpsStack(ops.tail(), cons(new Division(expr1, expr2), rest));
            } else {
                throw new IllegalStateException("New operation which isn't checked for.");
            }
        } else {
            return none();
        }
    }
}
