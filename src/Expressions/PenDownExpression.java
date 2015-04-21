package Expressions;

import Helpers.Context;
import Turtle.Turtle;
import Visitor.AbstractVisitor;

public class PenDownExpression implements AbstractExpression<Void> {

	private AbstractExpression<Void> nextExpression;

	public PenDownExpression() {
		nextExpression = null;
	}

	@Override
	public Void interpret(Context value) {
		Turtle t = value.getTurtle();
		t.penDown();

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

	@Override
	public void accept(AbstractVisitor v) {
		v.visitPenDownExpression(this);

	}

}
