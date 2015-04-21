package Expressions;
import Visitor.AbstractVisitor;
import Helpers.Context;

public class EndExpression implements AbstractExpression<Void> {

	private AbstractExpression<Void> repeatExpression, nextExpression;
	private AbstractExpression<Integer> repeatCountExpression;
	private int counter = 0, repeatCount = 0;

	public EndExpression(AbstractExpression<Void> exp,
			AbstractExpression<Integer> repeatCountExp) {
		repeatExpression = exp;
		nextExpression = null;
		this.repeatCountExpression = repeatCountExp;
	}
	/*
	 * This method will call the corresponding repeat expression for it's count
	 * value times and after that it will call next expression.
	 */
	@Override
	public Void interpret(Context value) {
		counter++;
		if (counter < repeatCountExpression.interpret(value)) {
			if (repeatExpression.getNext() != null)
				repeatExpression.getNext().interpret(value);
		} else if (nextExpression != null) {
			counter = 0;
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

	public AbstractExpression<Void> getcorrespondingRepeatExp() {
		return repeatExpression;
	}

	public AbstractExpression<Integer> getArgumentExp() {
		return repeatCountExpression;
	}

	@Override
	public void accept(AbstractVisitor v) {
		v.visitEndExpression(this);
	}
}