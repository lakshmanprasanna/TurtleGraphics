package Visitor;

import Expressions.*;
import Helpers.Context;
import Turtle.Turtle;

public class DistanceCalculationVisitor implements AbstractVisitor<Integer> {

	private Context context;
	private int totalDistance;
	private int nestedRepeatCount = 0;
	
	public DistanceCalculationVisitor(Context context) {
		this.context = context;
		totalDistance = 0;
	}
	
	@Override
	public void visitMoveExpression(MoveExpression moveExp) {
		if(nestedRepeatCount ==0 ) {
			int distance = moveExp.getArgument().interpret(context);
			context.getTurtle().move(distance);
			totalDistance = totalDistance + distance;
		}
	}

	@Override
	public void visitTurnExpression(TurnExpression turnExp) {
		if(nestedRepeatCount == 0) {
			context.getTurtle().turn(turnExp.getArgument().interpret(context));
		}
	}

	@Override
	public void visitPenUpExpression(PenUpExpression penupExp) {
		if (nestedRepeatCount == 0) {
			context.getTurtle().penUp();
		}
	}

	@Override
	public void visitPenDownExpression(PenDownExpression pendownExp) {
		if (nestedRepeatCount == 0) {
			context.getTurtle().penDown();
		}
	}

	@Override
	public void visitAssignmentExpression(AssignmentExpression assignExpression) {
		if (nestedRepeatCount == 0) {
			context.setValue(assignExpression.getKey(),
					assignExpression.getValue());
		}
	}

	@Override
	public void visitRepeatExpression(RepeatExpression repeatExpression) {
		nestedRepeatCount++;
	}

	@Override
	public void visitEndExpression(EndExpression endExpression) {
		
		nestedRepeatCount--;
		AbstractExpression<Void> exp = endExpression
				.getcorrespondingRepeatExp().getNext();
		AbstractExpression<Void> nextExpression = endExpression.getNext();
		AbstractExpression<Integer> argumentExp = endExpression
				.getArgumentExp();
		int repeatCount = argumentExp.interpret(context);
		for (int i = 0; i < repeatCount; i++) {
			while (exp != endExpression) {
				exp.accept((AbstractVisitor) this);
				exp = exp.getNext();
			}
			exp = endExpression.getcorrespondingRepeatExp().getNext();

		}
	}

	@Override
	public Integer getResult() {
		return totalDistance;
	}

}
