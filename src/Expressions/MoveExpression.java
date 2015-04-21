package Expressions;

import Helpers.Context;
import Turtle.Turtle;
import Visitor.AbstractVisitor;

public class MoveExpression implements AbstractExpression<Void> {

	private AbstractExpression<Integer> argument;
	private AbstractExpression<Void> nextExpression;

	public MoveExpression(AbstractExpression<Integer> argument) {
		this.argument = argument;
		this.nextExpression = null;
	}

	@Override
	public Void interpret(Context value) {
		value.getTurtle().move(argument.interpret(value));

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

	public AbstractExpression<Integer> getArgument() {
		return this.argument;
	}

	@Override
	public void accept(AbstractVisitor v) {
		v.visitMoveExpression(this);

	}

}
