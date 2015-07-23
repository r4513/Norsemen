package net.r4513.norsemen.entity.spawner;

import net.r4513.norsemen.entity.particle.Particle;
import net.r4513.norsemen.entity.spawner.Spawner.Type;
import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.level.Level;

public class ParticleSpawner extends Spawner {

	private int _life;

	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		_life = life;
		for(int i = 0; i < amount; i++){
			_level.addEntity(new Particle((int) Math.round(_x),(int) Math.round(_y), life));
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Screen screen) {
		// TODO Auto-generated method stub
		
	}

}
