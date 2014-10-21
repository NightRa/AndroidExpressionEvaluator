package nightra.mycalculator.tokens;

import nightra.mycalculator.expression.Expr;

import static nightra.mycalculator.expression.Cos.cosExpr;
import static nightra.mycalculator.expression.Sin.sinExpr;
import static nightra.mycalculator.expression.Sqrt.sqrtExpr;

public enum UnaryFunctionToken implements OperationToken {
    Sqrt {
        @Override
        public Expr toExpr(Expr of) {
            return sqrtExpr(of);
        }
    },Sin {
        @Override
        public Expr toExpr(Expr of) {
            return sinExpr(of);
        }
    },Cos {
        @Override
        public Expr toExpr(Expr of) {
            return cosExpr(of);
        }
    };

    public abstract Expr toExpr(Expr of);

    @Override
    public int precedence() {
        return 1;
    }
}
