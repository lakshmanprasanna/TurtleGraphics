package Turtle;
import javafx.geometry.Point2D;

public class Turtle {
	private Point2D point;
	private boolean isPenUp;
	private int angle;

	public Turtle() {
		point = new Point2D(0.0, 0.0);
		isPenUp = false;
		angle = 0;
	}

	public void penDown() {
		isPenUp = false;
	}

	public void penUp() {
		isPenUp = true;
	}

	public boolean isPenUp() {
		return isPenUp;
	}

	public void turn(int angle) {
		this.angle = this.angle+angle;
	}

	public int direction() {
		return angle;
	}

	public Point2D location() {
		return point;
	}

	public void move(int distance) {
		double radian = Math.PI * ((double) angle / 180);
		double deltaX = Math.cos(radian) * distance;
		double deltaY = Math.sin(radian) * distance;
		point = point.add(deltaX, deltaY);
	}

}
