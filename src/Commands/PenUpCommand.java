package Commands;

import Turtle.Turtle;

public class PenUpCommand implements Command {

	private Turtle turtle;
	private boolean isExecuted = false;

	public PenUpCommand(Turtle turtle) {
		this.turtle = turtle;
	}

	@Override
	public Turtle execute() {
		turtle.penUp();
		isExecuted = true;
		return turtle;

	}

	@Override
	public Turtle undo() {
		if (isExecuted) {
			turtle.penDown();
			return turtle;
		} else
			throw new RuntimeException("Can't undo this Command");
	}

}
