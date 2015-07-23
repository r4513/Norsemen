package net.r4513.norsemen.entity.projectile;

import net.r4513.norsemen.entity.Entity;
import net.r4513.norsemen.entity.spawner.ParticleSpawner;
import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected double _rateOfFire = 15;

	protected static int _size;

	protected double _x, _y;
	protected final int _xOrigin, _yOrigin;
	protected double _angle;
	protected Sprite _sprite;
	protected double _updateX, _updateY;
	protected double _speed, _range, _damage;
	protected double _direction;
	protected double _distance;

	// Overwrite to offset the collision detection corner of the sprite
	protected int _xOffset = 0;
	protected int _yOffset = 0;

	public Projectile(int x, int y, double dir) {
		_xOrigin = x;
		_yOrigin = y;
		_angle = dir;
		_x = x;
		_y = y;
		_direction = dir;
		_size = 1;
	}

	public Projectile(int size, int x, int y, double dir) {
		_xOrigin = x;
		_yOrigin = y;
		_angle = dir;
		_x = x;
		_y = y;
		_direction = dir;
		_size = size;
	}

	@Override
	public void update() {
		if (_level.tileCollision((int) Math.round(_x + _updateX),
				(int) Math.round(_y + _updateY), 1, _xOffset, _yOffset)) {
			addSpawner();
			remove();
			return;
		}

		if (_level.buildingCollision((int) Math.round(_x + _updateX),
				(int) Math.round(_y + _updateY), 1, _xOffset, _yOffset)) {
			addSpawner();
			remove();
			return;
		}

		move();
	}

	/**
	 * Overwrite this to specify an effect on collision
	 */
	public void addSpawner() {
		_level.addEntity(new ParticleSpawner((int) _x, (int) _y, 16, 8, _level));
	}

	protected void move() {
		_x += _updateX;
		_y += _updateY;

		if (getDistance() >= _range) {
			remove();
		}
	}

	private double getDistance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((_xOrigin - _x) * (_xOrigin - _x)
				+ (_yOrigin - _y) * (_yOrigin - _y)));

		return dist;
	}

	@Override
	public void render(Screen screen) {
		screen.renderProjectile((int) Math.round(_x) - _sprite.getWidth() / 2,
				(int) Math.round(_y) - _sprite.getHeight() / 2, this);
	}

	public Sprite getSprite() {
		return _sprite;
	}

	public static int getSize() {
		return _size;
	}

	public double getRateOfFire() {
		return _rateOfFire;
	}
}