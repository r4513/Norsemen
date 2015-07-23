package net.r4513.norsemen.entity.mob;

import java.util.List;

import net.r4513.norsemen.graphics.AnimatedSprite;
import net.r4513.norsemen.graphics.Sprite;
import net.r4513.norsemen.graphics.SpriteSheet;
import net.r4513.norsemen.level.Node;
import net.r4513.norsemen.level.tile.Tile;
import net.r4513.norsemen.util.Vector2I;

public class Star extends NPC {

	private AnimatedSprite _down = new AnimatedSprite(SpriteSheet.dummy_down,
			16, 16, 3);
	private AnimatedSprite _up = new AnimatedSprite(SpriteSheet.dummy_up, 16,
			16, 3);
	private AnimatedSprite _right = new AnimatedSprite(SpriteSheet.dummy_right,
			16, 16, 3);
	private AnimatedSprite _left = new AnimatedSprite(SpriteSheet.dummy_left,
			16, 16, 3);

	private AnimatedSprite _currentAnimatedSprite = _right;

	private int xa = 1;
	private int ya = 0;
	private int _time = 0;
	private List<Node> _path = null;

	public Star(int x, int y) {
		_x = x * Tile.WIDTH;
		_y = y * Tile.HEIGHT;
		_sprite = Sprite.dummy;
	}

	private void move() {
		xa = 0;
		ya = 0;

		int px = (int) Math.round(_level.getPlayerAt(0).getX());
		int py = (int) Math.round(_level.getPlayerAt(0).getY());
		Vector2I start = new Vector2I((int) _x / 16, (int) _y / 16);
		Vector2I destination = new Vector2I(px / 16, py / 16);

		if (_time % 5 == 0) {
			_path = _level.findPath(start, destination);
		}

		if (_path != null) {
			if (_path.size() > 0) {
				Vector2I vec = _path.get(_path.size() - 1).getTile();
				if (_x < vec.getX() * 16) {
					xa++;
				}
				if (_x > vec.getX() * 16) {
					xa--;
				}
				if (_y < vec.getY() * 16) {
					ya++;
				}
				if (_y > vec.getY() * 16) {
					ya--;
				}
			}
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			_moving = true;
		} else {
			_moving = false;
		}
	}

	@Override
	public void update() {
		_time++;
		move();

		if (_moving) {
			_currentAnimatedSprite.update();
		} else {
			_currentAnimatedSprite.setFrame(0);
		}

		updateAnimatedSprite();

		_sprite = _currentAnimatedSprite.getSprite();
	}

	private void updateAnimatedSprite() {
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
	}
}
