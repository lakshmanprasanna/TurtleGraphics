package Visitor;

import java.util.ArrayList;
import java.util.List;

import Commands.*;
import Expressions.*;
import Helpers.Context;
import Turtle.Turtle;

public class StepByStepExecutionVisitor implements
		AbstractVisitor<List<Command>> {

	private List<Command> commandList = new ArrayList<Command>();
	private Context context;
	private int nestedRepeatCount = 0;

	public StepByStepExecutionVisitor(Context context) {
		this.context = context;
	}

	@Override
	public void visitMoveExpression(MoveExpression exp) {

		if (nestedRepeatCount == 0) {
			Command move = new MoveCommand(context.getTurtle(), exp
					.getArgument().interpret(context));
			commandList.add(move);
		}
	}

	@Override
	public void visitTurnExpression(TurnExpression exp) {
		if (nestedRepeatCount == 0) {
			Command turn = new TurnCommand(context.getTurtle(), exp
					.getArgument().interpret(context));
			commandList.add(turn);
		}
	}

	@Override
	public void visitPenUpExpression(PenUpExpression penupExp) {
		if (nestedRepeatCount == 0) {
			Command penUp = new PenUpCommand(context.getTurtle());
			commandList.add(penUp);
		}
	}

	@Override
	public void visitPenDownExpression(PenDownExpression pendownExp) {
		if (nestedRepeatCount == 0) {
			Command penDown = new PenDownCommand(context.getTurtle());
			commandList.add(penDown);
		}

	}

	@Override
	public void visitAssignmentExpression(AssignmentExpression assignExpression) {
		if (nestedRepeatCount == 0) {
			if (nestedRepeatCount == 0) {
				context.setValue(assignExpression.getKey(),
						assignExpression.getValue());
			}		}
	}

	@Override
	public List<Command> getResult() {
		return this.commandList;
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

}
