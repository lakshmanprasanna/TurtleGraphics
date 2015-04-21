package Expressions;

import Visitor.AbstractVisitor;
import Helpers.Context;

public class AssignmentExpression implements AbstractExpression<Void> {

	private AbstractExpression<Void> nextExpression;
	private String key;
	private int value;

	public AssignmentExpression(String key, int value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public Void interpret(Context values) {
		values.setValue(key, value);
		if (nextExpression != null)
			nextExpression.interpret(values);
		return null;
	}

	@Override
	public void setNext(AbstractExpression<Void> exp) {
		this.nextExpression = exp;
	}

	@Override
	public AbstractExpression<Void> getNext() {
		return nextExpression;
	}

	public String getKey() {
		return this.key;
	}

	public int getValue() {
		return this.value;
	}

	@Override
	public void accept(AbstractVisitor v) {
		v.visitAssignmentExpression(this);

	}

}
