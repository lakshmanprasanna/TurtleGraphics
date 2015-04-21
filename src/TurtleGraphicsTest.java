import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

import Commands.Command;
import Helpers.Context;
import Helpers.Evaluator;
import Turtle.Turtle;
import Visitor.AbstractVisitor;
import Visitor.DistanceCalculationVisitor;
import Visitor.StepByStepExecutionVisitor;

public class TurtleGraphicsTest {

	private double epsilon = 0.01;

	@Test
	public void turtleTest() throws FileNotFoundException {

		Evaluator evaluator = new Evaluator("turtle.txt");
		evaluator.makeSyntaxTree();
		Context context = evaluator.getContext();

		Turtle turtleInterpreter = getResultUsingInterpreter(context, evaluator);
		List<Command> commandList = getCommandListUsingVisitor(context,
				evaluator);

		Turtle turtleVisitor = new Turtle();
		for (int i = 0; i < commandList.size(); i++) {
			turtleVisitor = commandList.get(i).execute();
		}

		int totalDistance = getTotalDistanceUsingVisitor(context, evaluator);

		assertTrue(Math.abs(460 - turtleInterpreter.location().getX()) < epsilon);
		assertTrue(Math.abs(-40 - turtleInterpreter.location().getY()) < epsilon);
		assertTrue(Math.abs(turtleInterpreter.location().getX()
				- turtleVisitor.location().getX()) < epsilon);
		assertTrue(Math.abs(turtleInterpreter.location().getY()
				- turtleVisitor.location().getY()) < epsilon);
		assertEquals(totalDistance, 500);
		assertTrue(turtleInterpreter.isPenUp());
		assertTrue(turtleVisitor.isPenUp());

		Turtle turtle1 = commandList.get(commandList.size() - 1).undo();
		assertTrue(Math.abs(460 - turtle1.location().getX()) < epsilon);
		assertEquals(turtle1.direction(), -90);

		Turtle turtle2 = commandList.get(commandList.size() - 2).undo();
		assertTrue(Math.abs(460 - turtle2.location().getX()) < epsilon);
		assertEquals(turtle2.direction(), 0);

		Turtle turtle3 = commandList.get(commandList.size() - 3).undo();
		assertTrue(Math.abs(450 - turtle3.location().getX()) < epsilon);
		assertTrue(turtle3.isPenUp());

		Turtle turtle4 = commandList.get(commandList.size() - 4).undo();
		assertFalse(turtle4.isPenUp());
	}

	@Test(expected = RuntimeException.class)
	public void TurtleTest2() throws FileNotFoundException {
		Evaluator evaluator = new Evaluator("turtle.txt");
		evaluator.makeSyntaxTree();
		Context context = evaluator.getContext();
		List<Command> commandList = getCommandListUsingVisitor(context,
				evaluator);
		commandList.get(0).undo();
	}

	@Test(expected = RuntimeException.class)
	public void TurtleTest3() throws FileNotFoundException {
		Evaluator evaluator = new Evaluator("turtle1.txt");
		evaluator.makeSyntaxTree();
		Context context = evaluator.getContext();
		Turtle turtleInterpreter = getResultUsingInterpreter(context, evaluator);
	}

	public Turtle getResultUsingInterpreter(Context context, Evaluator evaluator) {
		context.setTurtle(new Turtle());
		Turtle turtle = evaluator.interpret(context);
		return turtle;
	}

	public List<Command> getCommandListUsingVisitor(Context context,
			Evaluator evaluator) {
		context.setTurtle(new Turtle());
		AbstractVisitor<List<Command>> commandVisitor = new StepByStepExecutionVisitor(
				context);
		evaluator.accept(commandVisitor);
		List<Command> commandList = commandVisitor.getResult();
		return commandList;
	}

	public int getTotalDistanceUsingVisitor(Context context, Evaluator evaluator) {
		context.setTurtle(new Turtle());
		AbstractVisitor<Integer> distanceVisitor = new DistanceCalculationVisitor(
				context);
		evaluator.accept(distanceVisitor);
		int totalDistance = distanceVisitor.getResult();
		return totalDistance;
	}

}
