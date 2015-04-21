package Commands;

import Turtle.Turtle;

public class PenDownCommand implements Command {

	private Turtle turtle;
	private boolean isExecuted = false;

	public PenDownCommand(Turtle turtle) {
		this.turtle = turtle;
	}

	@Override
	public Turtle execute() {
		turtle.penDown();
		isExecuted = true;
		return turtle;
	}

	@Override
	public Turtle undo() {
		if (isExecuted) {
			turtle.penUp();
			return turtle;
		} else
			throw new RuntimeException("Can't undo this Command");
	}

}
