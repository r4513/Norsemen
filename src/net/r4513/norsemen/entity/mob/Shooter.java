package net.r4513.norsemen.entity.mob;

import java.util.List;

import net.r4513.norsemen.entity.Entity;
import net.r4513.norsemen.entity.projectile.Projectile;
import net.r4513.norsemen.graphics.AnimatedSprite;
import net.r4513.norsemen.graphics.Sprite;
import net.r4513.norsemen.graphics.SpriteSheet;
import net.r4513.norsemen.util.Vector2I;

public class Shooter extends NPC {

	private AnimatedSprite _down = new AnimatedSprite(SpriteSheet.dummy_down,
			16, 16, 3);
	private AnimatedSprite _up = new AnimatedSprite(SpriteSheet.dummy_up, 16,
			16, 3);
	private AnimatedSprite _right = new AnimatedSprite(SpriteSheet.dummy_right,
			16, 16, 3);
	private AnimatedSprite _left = new AnimatedSprite(SpriteSheet.dummy_left,
			16, 16, 3);

	private AnimatedSprite _currentAnimatedSprite = _right;

	private int _time = 0;
	private int ya = 0;
	private int xa = 0;
	private double _fireRate = 10;
	private double _fireRateCounter = _fireRate;

	public Shooter(int x, int y) {
		_x = x * 16;
		_y = y * 16;
		_sprite = Sprite.dummy;

	}

	@Override
	public void update() {
		if (_fireRateCounter > 0) {
			_fireRateCounter--;
		}
		_time++;

		move();

		clear();

		if (_level.getEntities(this, 48).size() > 0) {
			shootClosest();
		} else if (_time % (600 + _random.nextInt(600)) == 0) {
			shootRandom();
		}
	}

	public void move() {
		if (_time % (_random.nextInt(50) + 30) == 0) {
			xa = _random.nextInt(3) - 1;
			ya = _random.nextInt(3) - 1;
			if (_random.nextInt(5) <= 1) {
				ya = 0;
				xa = 0;
			}
		}

		if (_moving) {
			_currentAnimatedSprite.update();
		} else {
			_currentAnimatedSprite.setFrame(0);
		}

		if (ya < 0) {
			_currentAnimatedSprite = _up;
		} else if (ya > 0) {
			_currentAnimatedSprite = _down;
		}
		if (xa < 0) {
			_currentAnimatedSprite = _left;
		} else if (xa > 0) {
			_currentAnimatedSprite = _right;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			_moving = true;
		} else {
			_moving = false;
		}

		_sprite = _currentAnimatedSprite.getSprite();
	}

	public void clear() {
		Projectile p;
		for (int i = 0; i < _level.getProjectiles().size(); i++) {
			p = _level.getProjectiles().get(i);
			if (p.isRemoved()) {
				_level.getProjectiles().remove(i);
				_level.removeEntity(p);
			}
		}
	}

	private void shootRandom() {
		List<Entity> entities = _level.getEntities(this, 50);
		entities.add(_level.getClientPlayer());
		Entity random = null;

		if (entities.size() > 0) {
			int index = _random.nextInt((entities.size()));
			random = entities.get(index);
		}

		if (random != null) {
			double dx = random.getX() - _x;
			double dy = random.getY() - _y;
			double dir = Math.atan2(dy, dx);
			if (_fireRate <= 0) {
				shoot((int) Math.round(_x), (int) Math.round(_y), dir);
				_fireRateCounter = _fireRate;
			}
		}
	}

	private void shootClosest() {
		List<Entity> entities = _level.getEntities(this, 50);
		entities.add(_level.getClientPlayer());
		double min = 0;
		double distance = 0;
		Vector2I thisVec = new Vector2I((int) Math.round(_x),
				(int) Math.round(_y));
		Vector2I otherVec = null;
		Entity other = null;
		Entity closest = null;

		for (int i = 0; i < entities.size(); i++) {
			other = entities.get(i);
			otherVec = new Vector2I((int) Math.round(other.getX()),
					(int) Math.round(other.getY()));
			distance = thisVec.getDistance(otherVec);
			if (i == 0 || distance < min) {
				min = distance;
				closest = other;
			}
		}

		if (closest != null) {
			double dx = closest.getX() - _x;
			double dy = closest.getY() - _y;
			double dir = Math.atan2(dy, dx);
			if (_fireRate <= 0) {
				shoot((int) Math.round(_x), (int) Math.round(_y), dir);
				_fireRateCounter = _fireRate;
			}
		}
	}
}
