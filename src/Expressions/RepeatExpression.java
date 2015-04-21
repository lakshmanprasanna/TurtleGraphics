package Expressions;

import Visitor.AbstractVisitor;
import Helpers.Context;

public class RepeatExpression implements AbstractExpression<Void> {

	private AbstractExpression<Void> nextExpression;
	private AbstractExpression<Integer> argument;

	public RepeatExpression(AbstractExpression<Integer> argument) {
		this.nextExpression = null;
		this.argument = argument;
	}

	@Override
	public Void interpret(Context value) {
		if (nextExpression != null) {
			nextExpression.interpret(value);
		}
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

	public AbstractExpression<Integer> getRepeatCount() {
		return argument;
	}

	@Override
	public void accept(AbstractVisitor v) {
		v.visitRepeatExpression(this);

	}

}
