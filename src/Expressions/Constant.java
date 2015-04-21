package Expressions;

import Visitor.AbstractVisitor;
import Helpers.Context;

public class Constant implements AbstractExpression<Integer> {

	private int value;

	public Constant(int value) {
		this.value = value;
	}

	@Override
	public Integer interpret(Context values) {
		return value;
	}

	@Override
	public void setNext(AbstractExpression<Integer> exp) {
		throw new UnsupportedOperationException(
				"Method not supported for terminal expressions");

	}

	@Override
	public AbstractExpression<Integer> getNext() {
		throw new UnsupportedOperationException(
				"Method not supported for terminal expressions");
	}

	@Override
	public void accept(AbstractVisitor v) {
		throw new UnsupportedOperationException(
				"Method not supported for terminal expressions");
	}

}
