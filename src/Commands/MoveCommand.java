package Commands;

import Turtle.Turtle;

public class MoveCommand implements Command {

	private int distance;
	private Turtle turtle;
	private boolean isExecuted = false;

	public MoveCommand(Turtle t, int distance) {
		this.distance = distance;
		this.turtle = t;
	}

	@Override
	public Turtle execute() {
		turtle.move(distance);
		isExecuted = true;
		return turtle;
	}

	@Override
	public Turtle undo() {
		if (isExecuted) {
			turtle.move(-(distance));
			return turtle;
		} else
			throw new RuntimeException("Can't undo this Command");
	}

}
