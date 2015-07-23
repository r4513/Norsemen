package net.r4513.norsemen.entity.projectile;

import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.graphics.Sprite;

public class WizardProjectile extends Projectile {

	private Sprite _sprite = Sprite.projectile_fireball1;

	public WizardProjectile(int x, int y, double dir) {
		super(x, y, dir);
		_range = 200;
		_speed = 4;
		_damage = 20;
		_rateOfFire = 15;
		_size = 16;

		_updateX = _speed * Math.cos(_angle);
		_updateY = _speed * Math.sin(_angle);
	}

	@Override
	public void update() {
		move();
	}

	@Override
	public void move() {
		_x = _updateX;
		_y = _updateY;
	}

	@Override
	public void render(Screen screen) {
		screen.renderProjectile((int) Math.round(_x), (int) Math.round(_y),
				this);
	}
}
