package net.r4513.norsemen.level.building;

import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.level.Level;

public abstract class Building {
	public static final int WIDTH = 48;
	public static final int HEIGHT = 48;
	protected double _x;
	protected double _y;
	private boolean _removed;
	private Level _level;

	public abstract void update();

	public void init(Level level) {
		_level = level;
	}

	public double getX() {
		return _x;
	}

	public double getY() {
		return _y;
	}

	public void remove() {
		_removed = true;
	}

	public boolean isRemoved() {
		return _removed;
	}

	public abstract void render(Screen screen);
}
