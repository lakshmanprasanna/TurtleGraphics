package Commands;

import Turtle.Turtle;

public interface Command {
	public Turtle execute();

	public Turtle undo();
}
