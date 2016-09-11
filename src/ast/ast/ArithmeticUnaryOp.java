package ir.ast;

import ir.ASTVisitor;

public class ArithmeticUnaryOp extends UnaryOpExpr {

	public ArithmeticUnaryOp(BinOpType operator, Expression operand ){
		super(operator,operand);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}