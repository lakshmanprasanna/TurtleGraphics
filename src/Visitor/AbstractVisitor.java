package Visitor;

import Expressions.*;

public interface AbstractVisitor<E> {
	
	public void visitMoveExpression(MoveExpression moveExp);
	public void visitTurnExpression(TurnExpression turnExp);
	public void visitPenUpExpression(PenUpExpression penupExp);
	public void visitPenDownExpression(PenDownExpression pendownExp);
	public void visitAssignmentExpression(AssignmentExpression assignExpression);
	public void visitRepeatExpression(RepeatExpression repeatExpression);
	public void visitEndExpression(EndExpression endExpression);
	public E getResult();
	
}
