package ir.ast;

import ir.ASTVisitor;

public class EqBinOp extends BinOpExpr {

	public EqBinOp(Expression l, BinOpType op, Expression r){
		super(l,op,r);
	}

	@Override
	public void accept(ASTVisitor v) {
		v.visit(this);
	}
}