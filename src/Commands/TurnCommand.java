package Commands;

import Turtle.Turtle;

public class TurnCommand implements Command {

	private int angle;
	private Turtle turtle;
	private boolean isExecuted = false;

	public TurnCommand(Turtle t, int angle) {
		this.angle = angle;
		this.turtle = t;
	}

	@Override
	public Turtle execute() {
		turtle.turn(angle);
		isExecuted = true;
		return turtle;
	}

	@Override
	public Turtle undo() {
		if (isExecuted) {
			turtle.turn(-(angle));
			return turtle;
		} else
			throw new RuntimeException("Can't undo this Command");

	}

}
