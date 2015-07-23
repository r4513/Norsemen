package net.r4513.norsemen.entity.spawner;

import java.util.ArrayList;
import java.util.List;

import net.r4513.norsemen.entity.Entity;
import net.r4513.norsemen.entity.particle.Particle;
import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.level.Level;

public abstract class Spawner extends Entity {

	public enum Type{
		MOB, PARTICLE;
	}
	
	private Type _type;
	protected Level _level;
	
	public Spawner(int x, int y, Type t, int amount, Level level){
		_x = x;
		_y = y;
		_type = t;
		_level = level;
	}
}