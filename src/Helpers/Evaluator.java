package Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import Turtle.Turtle;
import Visitor.AbstractVisitor;
import Expressions.*;

public class Evaluator {

	private String fileName;
	private String expression;
	private int fileLineCounter = 0, nestedRepeatCount = 0;
	private String[] expressionSplitArray;
	private Context context = new Context();
	private AbstractExpression<Void> root = null, leaf = null;
	private Stack<AbstractExpression<Void>> repeatStack = new Stack<AbstractExpression<Void>>();
	private Stack<AbstractExpression<Integer>> repeatCountStack = new Stack<AbstractExpression<Integer>>();

	public Evaluator(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
	}

	/*
	 * This method will read the program from text file and will create a
	 * respective expression object by parsing it.Then it will represent the
	 * objects as a abstract syntax tree
	 */

	public void makeSyntaxTree() throws FileNotFoundException {
		context.removeValues();
		Scanner fileReader = new Scanner(new File(fileName));
		while (fileReader.hasNextLine()) {
			fileLineCounter++;
			expression = fileReader.nextLine().trim();
			expressionSplitArray = expression.split(" ");

			if (expressionSplitArray.length == 2) {
				String argument = expressionSplitArray[1].trim();
				String operation = expressionSplitArray[0].trim();
				checkForSyntaxError(operation, argument);
				if (argument.startsWith("$")) {
					handleExpressionWithVariable(operation);
				} else {
					handleExpressionWithConstant(operation);
				}

			} else if (expressionSplitArray.length == 1
					|| expressionSplitArray.length == 3) {

				checkForSyntaxError(expression, expressionSplitArray.length);
				if (root == null) {
					handleExpRoot(expression, null);
				} else {

					handleExpLeaf(expression, null);
				}
			} else {
				fileReader.close();
				throw new RuntimeException("Unidentified expression at Line "
						+ fileLineCounter);
			}
		}
		fileReader.close();

		if (nestedRepeatCount != 0) {
			throw new RuntimeException("End expression is missing at Line "
					+ fileLineCounter);
		}
	}

	public void handleExpressionWithVariable(String operation) {
		String name = expressionSplitArray[1].trim();
		AbstractExpression<Integer> variable = new Variable(name,
				nestedRepeatCount);
		if (operation.equalsIgnoreCase("repeat"))
			repeatCountStack.push(variable);
		if (root == null) {
			handleExpRoot(operation, variable);
		} else {

			handleExpLeaf(operation, variable);
		}
	}

	public void handleExpressionWithConstant(String operation) {
		String value = expressionSplitArray[1].trim();
		int IntegerValue = 0;
		try {
			IntegerValue = Integer.parseInt(value);
		} catch (Exception e) {
		}

		AbstractExpression<Integer> constant = new Constant(IntegerValue);
		if (operation.equalsIgnoreCase("repeat"))
			repeatCountStack.push(constant);
		if (root == null) {
			handleExpRoot(operation, constant);
		} else {
			handleExpLeaf(operation, constant);
		}
	}

	/*
	 * This method will make expressions and pass either constant or variable if
	 * the expression need it. It save the repeat expression in stack and if end
	 * expression comes it pops out the stack and initialize end expression with
	 * corresponding repeat expression.After it will be used to implement repeat
	 * logic.
	 */

	public void handleExpRoot(String operation,
			AbstractExpression<Integer> argument) {

		if (operation.equalsIgnoreCase("move")) {
			root = new MoveExpression(argument);
			leaf = root;
		} else if (operation.equalsIgnoreCase("turn")) {
			root = new TurnExpression(argument);
			leaf = root;
		} else if (operation.equalsIgnoreCase("repeat")) {
			nestedRepeatCount++;
			root = new RepeatExpression(argument);
			leaf = root;
			repeatStack.push(leaf);
		} else if (operation.equalsIgnoreCase("penup")) {
			root = new PenUpExpression();
			leaf = root;
		} else if (operation.equalsIgnoreCase("pendown")) {
			root = new PenDownExpression();
			leaf = root;
		} else if (operation.startsWith("$")) {
			root = parseAssignmentExpression(operation);
			leaf = root;
		} else if (!expression.isEmpty())
			throw new RuntimeException("Syntax error at line "
					+ fileLineCounter);
	}

	public void handleExpLeaf(String operation,
			AbstractExpression<Integer> argument) {
		AbstractExpression<Void> temporaryExpression = null;
		if (operation.equalsIgnoreCase("move")) {
			temporaryExpression = new MoveExpression(argument);
			leaf.setNext(temporaryExpression);
			leaf = temporaryExpression;
		} else if (operation.equalsIgnoreCase("turn")) {
			temporaryExpression = new TurnExpression(argument);
			leaf.setNext(temporaryExpression);
			leaf = temporaryExpression;
		} else if (operation.equalsIgnoreCase("repeat")) {
			temporaryExpression = new RepeatExpression(argument);
			leaf.setNext(temporaryExpression);
			leaf = temporaryExpression;
			nestedRepeatCount++;
			repeatStack.push(leaf);
		} else if (operation.equalsIgnoreCase("penup")) {
			temporaryExpression = new PenUpExpression();
			leaf.setNext(temporaryExpression);
			leaf = temporaryExpression;
		} else if (operation.equalsIgnoreCase("pendown")) {
			temporaryExpression = new PenDownExpression();
			leaf.setNext(temporaryExpression);
			leaf = temporaryExpression;
		} else if (operation.equalsIgnoreCase("end")) {
			temporaryExpression = new EndExpression(repeatStack.pop(),
					repeatCountStack.pop());
			leaf.setNext(temporaryExpression);
			leaf = temporaryExpression;
			nestedRepeatCount--;
		} else if (operation.startsWith("$")) {
			if (nestedRepeatCount > 0)
				operation = nestedRepeatCount + operation;
			temporaryExpression = parseAssignmentExpression(operation);
			leaf.setNext(temporaryExpression);
			leaf = temporaryExpression;
		} else if (!expression.isEmpty()) {
			throw new RuntimeException("Syntax error at line "
					+ fileLineCounter);
		}
	}

	public AbstractExpression<Void> parseAssignmentExpression(String expression) {

		String[] keyValues = expression.split("=");
		String key = keyValues[0].trim();
		int value = 0;
		try {
			value = Integer.parseInt(keyValues[1].trim());
		} catch (Exception e) {
		}
		AbstractExpression<Void> exp = new AssignmentExpression(key, value);
		return exp;
	}

	public void checkForSyntaxError(String operation, String argument) {
		if (operation.equalsIgnoreCase("move")
				|| operation.equalsIgnoreCase("turn")
				|| operation.equalsIgnoreCase("repeat")) {
			if (!argument.startsWith("$")) {
				try {
					Integer.parseInt(argument);
				} catch (Exception e) {
					throw new RuntimeException(
							"Given value is not an Integer at line "
									+ fileLineCounter);
				}
			}
		} else
			throw new RuntimeException("Unidentified expression at Line "
					+ fileLineCounter);
	}

	public void checkForSyntaxError(String expression, int length) {
		if (length == 1) {
			if (expression.startsWith("$")) {
				String[] expArray = expression.split("=");
				try {
					Integer.parseInt(expArray[1]);
				} catch (Exception e) {
					throw new RuntimeException(
							"Given value is not an Integer at Line "
									+ fileLineCounter);
				}
			} else if (expression.equalsIgnoreCase("penup")
					|| expression.equalsIgnoreCase("pendown")
					|| expression.equalsIgnoreCase("end")
					|| expression.isEmpty()) {
			} else
				throw new RuntimeException("Unidentified expression at Line "
						+ fileLineCounter);
		} else {
			if (expression.startsWith("$")) {
				String[] expArray = expression.split(" ");
				try {
					Integer.parseInt(expArray[2]);
				} catch (Exception e) {
					throw new RuntimeException(
							"Given value is not an Integer at Line "
									+ fileLineCounter);
				}
			} else {
				throw new RuntimeException("Unidentified expression at Line"
						+ fileLineCounter);
			}
		}
	}

	/*
	 * This method will call the interpret function of the root and it
	 * recursively call interpret to all the remaining expressions
	 */

	public Turtle interpret(Context context) {
		root.interpret(context);
		return context.getTurtle();
	}

	/*
	 * This method will call accept method for all the expression with concrete
	 * visitor which client passes here.
	 */

	public void accept(AbstractVisitor v) {
		AbstractExpression<Void> exp = root;

		while (exp != null) {
			exp.accept(v);
			exp = exp.getNext();
		}
	}

	public Context getContext() {
		return context;
	}

}
