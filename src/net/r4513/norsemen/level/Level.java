package net.r4513.norsemen.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.r4513.norsemen.entity.Entity;
import net.r4513.norsemen.entity.mob.Player;
import net.r4513.norsemen.entity.particle.Particle;
import net.r4513.norsemen.entity.projectile.Projectile;
import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.level.building.Building;
import net.r4513.norsemen.level.tile.Tile;
import net.r4513.norsemen.util.Vector2I;

public abstract class Level {

	protected int _width, _height;
	protected int[] _tiles;

	public static Level _spawn = new SpawnLevel("/levels/spawnlevel.png");

	private List<Player> _players = new ArrayList<Player>();
	private List<Entity> _entities = new ArrayList<Entity>();
	private List<Projectile> _projectiles = new ArrayList<Projectile>();
	private List<Particle> _particles = new ArrayList<Particle>();
	private List<Building> _buildings = new ArrayList<Building>();

	private Comparator<Node> _nodeSorter = new Comparator<Node>() {
		@Override
		public int compare(Node n1, Node n2) {
			if (n2.getFCost() < n1.getFCost()) {
				return 1;
			}
			if (n2.getFCost() > n1.getFCost()) {
				return -1;
			}
			return 0;
		}
	};

	public Level(int width, int height) {
		_width = width;
		_height = height;
		_tiles = new int[_width * _height];
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected abstract void loadLevel(String path);

	protected abstract void generateLevel();

	public void update() {
		for (int i = 0; i < _entities.size(); i++) {
			_entities.get(i).update();
		}
		for (int i = 0; i < _projectiles.size(); i++) {
			_projectiles.get(i).update();
		}
		for (int i = 0; i < _particles.size(); i++) {
			_particles.get(i).update();
		}

		for (int i = 0; i < _players.size(); i++) {
			_players.get(i).update();
		}

		for (int i = 0; i < _buildings.size(); i++) {
			_buildings.get(i).update();
		}

		remove();
	}

	private void remove() {
		for (int i = 0; i < _entities.size(); i++) {
			if (_entities.get(i).isRemoved()) {
				_entities.remove(i);
			}
		}
		for (int i = 0; i < _projectiles.size(); i++) {
			if (_projectiles.get(i).isRemoved()) {
				_projectiles.remove(i);
			}
		}
		for (int i = 0; i < _particles.size(); i++) {
			if (_particles.get(i).isRemoved()) {
				_particles.remove(i);
			}
		}
		for (int i = 0; i < _players.size(); i++) {
			if (_players.get(i).isRemoved()) {
				_players.remove(i);
			}
		}
		for (int i = 0; i < _buildings.size(); i++) {
			if (_buildings.get(i).isRemoved()) {
				_buildings.remove(i);
			}
		}
	}

	protected void time() {

	}

	public List<Node> findPath(Vector2I start, Vector2I goal) {
		List<Node> openList = new ArrayList<Node>(); // Consider these tiles
		List<Node> closedList = new ArrayList<Node>(); // Don't consider
		Node current = new Node(start, null, 0, getDistance(start, goal));

		openList.add(current);

		while (openList.size() > 0) {
			Collections.sort(openList, _nodeSorter);
			current = openList.get(0);
			if (current.getTile().equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.getParent() != null) {
					path.add(current);
					current = current.getParent();
				}
				openList.clear();
				closedList.clear();

				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) {
					continue;
				}
				int x = current.getTile().getX();
				int y = current.getTile().getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile tile = getTile(x + xi, y + yi);
				if (tile == null) {
					continue;
				}
				if (tile.solid()) {
					continue;
				}

				Vector2I vec = new Vector2I(x + xi, y + yi);
				double gCost = current.getGCost()
						+ (getDistance(current.getTile(), vec) == 1 ? 1 : 0.95);
				double hCost = getDistance(vec, goal);
				Node node = new Node(vec, current, gCost, hCost);

				if (isVecInList(closedList, vec) && gCost >= node.getGCost()) {
					continue;
				}
				if (!isVecInList(openList, vec) || gCost < node.getGCost()) {
					openList.add(node);
				}

			}
		}
		closedList.clear();
		return null;
	}

	private boolean isVecInList(List<Node> list, Vector2I vec) {
		for (Node n : list) {
			if (n.getTile().equals(vec)) {
				return true;
			}
		}
		return false;
	}

	private double getDistance(Vector2I start, Vector2I goal) {
		double x = start.getX() - goal.getX();
		double y = start.getY() - goal.getY();
		double distance = Math.sqrt(x * x + y * y);
		if (distance == 1) {
			return 1;
		}
		return 0.95;
	}

	public boolean tileCollision(int x, int y, int size, int xOffset,
			int yOffset) {
		boolean collition = false;
		int xt, yt;
		for (int c = 0; c < 4; c++) {
			xt = (x - c % 2 * size + xOffset) / Tile.WIDTH;
			yt = (y - c / 2 * size + yOffset) / Tile.HEIGHT;
			if (getTile(xt, yt).solid()) {
				collition = true;
			}
		}
		return collition;
	}

	public boolean buildingCollision(int x, int y, int size, int xOffset,
			int yOffset) {
		boolean collition = false;
		int xt, yt;
		for (int c = 0; c < 4; c++) {
			xt = (x - c % 2 * size + xOffset) / Tile.WIDTH;
			yt = (y - c / 2 * size + yOffset) / Tile.HEIGHT;
			if (getBuilding(xt, yt) != null) {
				collition = true;
			}
		}
		return collition;
	}

	public Building getBuilding(int xt, int yt) {
		for (Building b : _buildings) {
			if (b.getX() == xt && b.getY() == yt) {
				return b;
			}
		}
		return null;
	}

	public void addBuilding(Building b) {
		_buildings.add(b);
	}

	public void removeBuilding(Building b) {
		_buildings.remove(b);
	}

	public void addEntity(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			_particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			_projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			_players.add((Player) e);
		} else {
			_entities.add(e);
		}
	}

	public void removeEntity(Entity e) {
		if (e instanceof Particle) {
			_particles.remove(e);
		} else if (e instanceof Projectile) {
			_projectiles.remove(e);
		} else if (e instanceof Player) {
			_players.remove(e);
		} else {
			_entities.remove(e);
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= _width || y >= _height) {
			return Tile.voidTile;
		} else if (_tiles[x + y * _width] == 0) {
			return Tile.grass;
		} else if (_tiles[x + y * _width] == 1) {
			return Tile.road_sand;
		} else if (_tiles[x + y * _width] == 2) {
			return Tile.road_dark;
		} else if (_tiles[x + y * _width] == 3) {
			return Tile.sand;
		} else if (_tiles[x + y * _width] == 3) {
			return Tile.road_dark;
		} else if (_tiles[x + y * _width] == 3) {
			return Tile.wall_red;
		} else if (_tiles[x + y * _width] == 3) {
			return Tile.wall_dark;
		}

		return Tile.voidTile;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffsets(xScroll, yScroll);
		int x0 = xScroll / Tile.WIDTH;
		int x1 = (xScroll + screen.getWidth() + Tile.WIDTH) / Tile.WIDTH;
		int y0 = yScroll / Tile.HEIGHT;
		int y1 = (yScroll + screen.getHeight() + Tile.HEIGHT) / Tile.HEIGHT;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}

		for (int i = 0; i < _entities.size(); i++) {
			_entities.get(i).render(screen);
		}

		for (int i = 0; i < _players.size(); i++) {
			_players.get(i).render(screen);
		}

		for (int i = 0; i < _projectiles.size(); i++) {
			_projectiles.get(i).render(screen);
		}
		for (int i = 0; i < _particles.size(); i++) {
			_particles.get(i).render(screen);
		}

		for (int i = 0; i < _buildings.size(); i++) {
			_buildings.get(i).render(screen);
		}
	}

	public List<Projectile> getProjectiles() {
		return _projectiles;
	}

	public List<Player> getPlayers() {
		return _players;
	}

	public Player getPlayerAt(int i) {
		return _players.get(i);
	}

	public Player getClientPlayer() {
		return _players.get(0);
	}

	public List<Building> getBuildings() {
		return _buildings;
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		double ex = e.getX();
		double ey = e.getY();
		double x, y, dx, dy;
		double dist;
		Entity entity;
		for (int i = 0; i < _entities.size(); i++) {
			entity = _entities.get(i);
			if (e.equals(entity)) {
				continue;
			}
			x = entity.getX();
			y = entity.getY();
			dx = Math.abs(x - ex);
			dy = Math.abs(y - ey);
			dist = Math.sqrt((dx * dx) + (dy * dy));
			if (dist <= radius) {
				result.add(entity);
			}
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		double ex = e.getX();
		double ey = e.getY();
		double x, y, dx, dy;
		double dist;
		Player en;
		for (int i = 0; i < _players.size(); i++) {
			en = _players.get(i);
			x = en.getX();
			y = en.getY();
			dx = Math.abs(x - ex);
			dy = Math.abs(y - ey);
			dist = Math.sqrt((dx * dx) + (dy * dy));
			if (dist <= radius) {
				result.add(en);
			}
		}
		return result;
	}
}
