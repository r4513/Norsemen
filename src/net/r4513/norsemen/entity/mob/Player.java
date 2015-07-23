package net.r4513.norsemen.entity.mob;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import net.r4513.norsemen.entity.projectile.Projectile;
import net.r4513.norsemen.graphics.AnimatedSprite;
import net.r4513.norsemen.graphics.Game;
import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.graphics.Sprite;
import net.r4513.norsemen.graphics.SpriteSheet;
import net.r4513.norsemen.input.Keyboard;
import net.r4513.norsemen.input.Mouse;

public class Player extends NPC {

	private int _keyUp = KeyEvent.VK_W;
	private int _keyDown = KeyEvent.VK_S;
	private int _keyLeft = KeyEvent.VK_A;
	private int _keyRight = KeyEvent.VK_D;
	private int _mouseBtn1 = MouseEvent.BUTTON1;

	// Sprites
	private Sprite _sprite = Sprite.player_down1;

	// Animated sprites
	private AnimatedSprite _down = new AnimatedSprite(SpriteSheet.player_down,
			16, 16, 3);
	private AnimatedSprite _up = new AnimatedSprite(SpriteSheet.player_up, 16,
			16, 3);
	private AnimatedSprite _right = new AnimatedSprite(
			SpriteSheet.player_right, 16, 16, 3);
	private AnimatedSprite _left = new AnimatedSprite(SpriteSheet.player_left,
			16, 16, 3);

	private AnimatedSprite _currentAnimatedSprite = _right;

	private Keyboard _keyInput;
	private Mouse _mouseInput;

	private double _fireRate = 10;
	private double _fireRateCounter = _fireRate;

	public Player(Keyboard keyInput, Mouse mouseInput) {
		_x = 0;
		_y = 0;

		_keyInput = keyInput;
		_mouseInput = mouseInput;

	}

	public Player(int x, int y, Keyboard keyInput, Mouse mouseInput) {
		_keyInput = keyInput;
		_mouseInput = mouseInput;

		_x = x;
		_y = y;
	}

	@Override
	public void update() {
		if (_moving) {
			_currentAnimatedSprite.update();
		} else {
			_currentAnimatedSprite.setFrame(0);
		}
		updateFirerate();

		updateMoving();
		clear();
		updateShooting();
	}

	private void updateFirerate() {
		if (_fireRateCounter > 0) {
			_fireRateCounter--;
		} else {
			_fireRateCounter = _fireRate;
		}
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

	private void updateMoving() {
		if (_animate < _animateSpeed) {
			_animate++;
		} else {
			_animate = 0;
		}

		double x = 0, y = 0;

		if (_keyInput.isKeyDown(_keyUp)) {
			y -= _speed;
			_currentAnimatedSprite = _up;
		} else if (_keyInput.isKeyDown(_keyDown)) {
			y += _speed;
			_currentAnimatedSprite = _down;
		}

		if (_keyInput.isKeyDown(_keyLeft)) {
			x -= _speed;
			_currentAnimatedSprite = _left;
		} else if (_keyInput.isKeyDown(_keyRight)) {
			x += _speed;
			_currentAnimatedSprite = _right;
		}

		if (x != 0 || y != 0) {
			move(x, y);
			_moving = true;
		} else {
			_moving = false;
		}
		_sprite = _currentAnimatedSprite.getSprite();
	}

	private void updateShooting() {
		if (_mouseInput.isKeyDown(_mouseBtn1)) {
			double dx = _mouseInput.getX() - Game.getWindowWidth() / 2;
			double dy = _mouseInput.getY() - Game.getWindowHeight() / 2;
			double direction = Math.atan2(dy, dx);
			shoot((int) _x, (int) _y, direction);

		}
	}

	@Override
	public void render(Screen screen) {
		screen.renderNPC((int) (_x - SIZE / 2), (int) (_y - SIZE / 2), _sprite,
				0);
	}
}