package net.r4513.norsemen.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.r4513.norsemen.entity.mob.Dummy;
import net.r4513.norsemen.entity.mob.Shooter;
import net.r4513.norsemen.entity.mob.Star;
import net.r4513.norsemen.graphics.Sprite;
import net.r4513.norsemen.level.building.TippiBuilding;
import net.r4513.norsemen.level.tile.Tile;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
		generateLevel();
		addEntity(new Dummy(17, 20));
		addEntity(new Dummy(27, 18));
		addEntity(new Dummy(34, 23));
		addEntity(new Dummy(33, 34));

		// addEntity(new Chaser(17, 22));
		addEntity(new Star(16, 23));
		addEntity(new Shooter(16, 27));

		// Add building
		addBuilding(new TippiBuilding(5, 5, Sprite.tippi));
	}

	@Override
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= _width || y >= _height) {
			return Tile.voidTile;
		}

		if (_tiles[x + y * _width] == Tile.GRASS_COLOR) {
			return Tile.grass;
		}
		if (_tiles[x + y * _width] == Tile.GRASS_ROAD_COLOR) {
			return Tile.road_sand;
		}
		if (_tiles[x + y * _width] == Tile.GRASS_SAND_COLOR) {
			return Tile.sand;
		}
		if (_tiles[x + y * _width] == Tile.GRASS_WATER_COLOR) {
			return Tile.water1;
		}
		if (_tiles[x + y * _width] == Tile.GRASS_FLOOR_COLOR) {
			return Tile.road_dark;
		}
		if (_tiles[x + y * _width] == Tile.GRASS_WALL1_COLOR) {
			return Tile.wall_dark;
		}
		if (_tiles[x + y * _width] == Tile.GRASS_WALL2_COLOR) {
			return Tile.wall_red;
		}

		return Tile.voidTile;
	}

	@Override
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class
					.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			_width = w;
			_height = h;
			_tiles = new int[w * h];
			image.getRGB(0, 0, w, h, _tiles, 0, w);
		} catch (IOException e) {
			System.out.println("Couldn't load SpecLevel file");
			e.printStackTrace();
		}
	}

	@Override
	protected void generateLevel() {
		// TODO Auto-generated method stub

	}
}