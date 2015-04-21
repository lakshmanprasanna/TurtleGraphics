package Expressions;

import Visitor.AbstractVisitor;
import Helpers.Context;

public class Variable implements AbstractExpression<Integer> {

	private String key;
	private int nestedRepeatCount;

	public Variable(String name, int nestedRepeatCount) {
		this.key = name;
		this.nestedRepeatCount = nestedRepeatCount;
	}

	@Override
	public Integer interpret(Context values) {
		for (int i = nestedRepeatCount; i > 0; i--) {
			if (values.getValue(i + key) != Integer.MIN_VALUE) {
				return values.getValue(i + key);
			}
		}
		if (values.getValue(key) != Integer.MIN_VALUE)
			return values.getValue(key);
		else
			throw new RuntimeException("Variable not initialized");
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
