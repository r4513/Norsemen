package net.r4513.norsemen.entity.mob;

import java.util.ArrayList;

import net.r4513.norsemen.entity.Entity;
import net.r4513.norsemen.entity.projectile.Projectile;
import net.r4513.norsemen.entity.projectile.WizardProjectile;
import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.graphics.Sprite;
import net.r4513.norsemen.level.building.Building;
import net.r4513.norsemen.level.tile.Tile;

public abstract class NPC extends Entity {

	public static final int SIZE = 16;

	protected Sprite _sprite;
	protected boolean _moving = false;
	protected int _animate = 0;
	protected int _animateSpeed = 50;
	protected Direction _direction;
	protected double _speed = 1;
	protected ArrayList<Projectile> _projectiles = new ArrayList<Projectile>();

	@Override
	public abstract void update();

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	@Override
	public void render(Screen screen) {
		screen.renderNPC((int) (_x - (SIZE / 2)), (int) (_y - (SIZE / 2)), this);
	}

	public static int getSize() {
		return SIZE;
	}

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;

		}

		if (xa > 0) {
			_direction = Direction.RIGHT;
		} else if (xa < 0) {
			_direction = Direction.LEFT;
		} else if (ya > 0) {
			_direction = Direction.DOWN;
		} else if (ya < 0) {
			_direction = Direction.UP;
		}

		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!collision(absMove(xa), ya)) {
					_x += absMove(xa);
				}
				xa -= absMove(xa);
				;
			} else {
				if (!collision(absMove(xa), ya)) {
					_x += xa;
				}
				xa = 0;
			}
		}

		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, absMove(ya))
						&& !collisionBuilding(xa, absMove(ya))) {
					_y += absMove(ya);
				}
				ya -= absMove(ya);
				;
			} else {
				if (!collision(xa, absMove(ya))
						&& !collisionBuilding(xa, absMove(ya))) {
					_y += ya;
				}
				ya = 0;
			}
		}
	}

	private int absMove(double ya) {
		if (ya < 0) {
			return -1;
		}
		if (ya == 0) {
			return 0;
		}
		return 1;
	}

	public boolean collision(double xa, double ya) {
		boolean collition = false;
		double xt, yt;
		for (int c = 0; c < 4; c++) {
			xt = ((_x + xa) - c % 2 * (Tile.WIDTH - 1)) / Tile.WIDTH;
			yt = ((_y + ya) - c / 2 * (Tile.HEIGHT - 1) + (SIZE / 2))
					/ Tile.HEIGHT;
			int xi = (int) Math.ceil(xt);
			int yi = (int) Math.ceil(yt);

			if (c % 2 == 0) {
				xi = (int) Math.floor(xt);
			}

			if (c / 2 == 0) {
				yi = (int) Math.floor(yt);
			}

			if (_level.getTile(xi, yi).solid()) {
				collition = true;
			}
		}

		return collition;
	}

	public boolean collisionBuilding(double xa, double ya) {
		boolean collition = false;
		double xt, yt;
		for (int c = 0; c < 4; c++) {
			xt = ((_x + xa) - c % 2 * (Building.WIDTH - 1)) / Building.WIDTH;
			yt = ((_y + ya) - c / 2 * (Building.HEIGHT - 1) + (SIZE / 2))
					/ Building.HEIGHT;
			int xi = (int) Math.ceil(xt);
			int yi = (int) Math.ceil(yt);

			if (c % 2 == 0) {
				xi = (int) Math.floor(xt);
			}

			if (c / 2 == 0) {
				yi = (int) Math.floor(yt);
			}

			if (_level.getBuilding(xi / Tile.WIDTH, yi / Tile.HEIGHT) != null) {
				collition = true;
			}
		}

		return collition;
	}

	protected void shoot(int x, int y, double direction) {
		Projectile p = new WizardProjectile(x, y, direction);
		_projectiles.add(p);
		_level.addEntity(p);

	}

	public Sprite getSprite() {
		return _sprite;
	}
}
