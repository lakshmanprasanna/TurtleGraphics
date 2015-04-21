package Expressions;

import Visitor.AbstractVisitor;
import Helpers.Context;

public interface AbstractExpression<E> {

	public E interpret(Context value);

	public void setNext(AbstractExpression<E> exp);

	public AbstractExpression<E> getNext();

	public void accept(AbstractVisitor<E> v);

}
