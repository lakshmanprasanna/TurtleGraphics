package Expressions;

import Helpers.Context;
import Turtle.Turtle;
import Visitor.AbstractVisitor;

public class PenUpExpression implements AbstractExpression<Void> {

	private AbstractExpression<Void> nextExpression;

	@Override
	public Void interpret(Context value) {
		Turtle t = value.getTurtle();
		t.penUp();

		if (nextExpression != null) {
			nextExpression.interpret(value);
		}

		return null;
	}

	public PenUpExpression() {
		nextExpression = null;
	}

	@Override
	public void setNext(AbstractExpression<Void> exp) {
		this.nextExpression = exp;

	}

	@Override
	public AbstractExpression<Void> getNext() {
		return nextExpression;
	}

	@Override
	public void accept(AbstractVisitor v) {
		v.visitPenUpExpression(this);

	}

}
