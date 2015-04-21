package Helpers;

import java.util.Hashtable;

import Turtle.Turtle;

public class Context {

	private Hashtable<String, Integer> values;
	private Turtle t;

	public Context() {
		values = new Hashtable<String, Integer>();
		t = new Turtle();
	}

	public void setTurtle(Turtle t) {
		this.t = t;
	}

	public Turtle getTurtle() {
		return t;
	}

	public int getValue(String key) {
		if (values.get(key) != null)
			return values.get(key);
		else {
			return Integer.MIN_VALUE;
		}
	}

	public void setValue(String key, int value) {
		values.put(key, value);
	}

	public void removeValues() {
		values.clear();
	}

}
