package net.r4513.norsemen.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import net.r4513.norsemen.entity.mob.Player;
import net.r4513.norsemen.input.Keyboard;
import net.r4513.norsemen.input.Mouse;
import net.r4513.norsemen.level.Level;
import net.r4513.norsemen.level.TileCoordinate;

/**
 * Game class extends Canvas and implements Runnable
 * 
 * @author Rasmus Soee Christensen aka r4513
 *
 */
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int _width = 300;
	private static int _height = _width / 16 * 9; // The height of the screen
													// with 16:9 ratio
	public static int _scale = 3; // The scale for scaling the screen by 3 to
									// increase performance

	private Thread _thread;
	private boolean _running = false;
	private JFrame _frame;
	private Screen _screen;
	private String _title;
	private Keyboard _keyboard;
	private Level _level;
	private Player _player;

	private BufferedImage _image = new BufferedImage(_width, _height,
			BufferedImage.TYPE_INT_RGB);
	private int[] _pixels = ((DataBufferInt) _image.getRaster().getDataBuffer())
			.getData();
	private Mouse _mouse;

	/**
	 * Game constructor
	 * 
	 * @param title
	 */
	public Game(String title) {
		_title = title;

		Dimension size = new Dimension(_width * _scale, _height * _scale);
		setPreferredSize(size);

		_screen = new Screen(_width, _height);
		_frame = new JFrame();
		_frame.setTitle(_title);

		_keyboard = new Keyboard();
		addKeyListener(_keyboard);
		_mouse = new Mouse();
		addMouseListener(_mouse);
		addMouseMotionListener(_mouse);

		TileCoordinate playerSpawn = new TileCoordinate(1, 1);

		_level = Level._spawn;
		_player = new Player(playerSpawn.getX(), playerSpawn.getY(), _keyboard,
				_mouse);
		_level.addEntity(_player);

		setFocusable(true);
		requestFocus();
	}

	/**
	 * Starts the thread
	 */
	public synchronized void start() {
		_running = true;
		_thread = new Thread(this, "Display");
		_thread.start();
	}

	/**
	 * Stops the thread
	 */
	public synchronized void stop() {
		_running = false;
		try {
			_thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Runs the game loop
	 */
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (_running) {
			long now = System.nanoTime();

			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				_frame.setTitle(_title + " | " + updates + " ups, " + frames
						+ " fps.");
				frames = 0;
				updates = 0;
			}
		}
	}

	/**
	 * Updates the game
	 */
	public void update() {
		_level.update();
	}

	/**
	 * Renders to the buffers
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		_screen.clear();

		int xScroll = (int) Math.round(_player.getX()
				- (_screen.getWidth() / 2));
		int yScroll = (int) Math.round(_player.getY()
				- (_screen.getHeight() / 2));
		_level.render(xScroll, yScroll, _screen);
		_player.render(_screen);

		// Renders the pixels
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = _screen.getPixelAt(i);
		}

		Graphics g = bs.getDrawGraphics();

		// Draw game graphics
		g.drawImage(_image, 0, 0, getWidth(), getHeight(), null);

		// Draw mouse
		g.setColor(Color.RED);
		g.drawOval(_mouse.getX() - 8, _mouse.getY() - 8, 16, 16);

		g.dispose();
		bs.show();
	}

	/**
	 * Returns the width of the window
	 */
	public static int getWindowWidth() {
		return _width * _scale;
	}

	/**
	 * Returns the height of the window
	 */
	public static int getWindowHeight() {
		return _height * _scale;
	}

	/**
	 * Returns the scale of the window
	 */
	public int getScale() {
		return _scale;
	}

	/**
	 * Returns the JFrame
	 */
	public JFrame getFrame() {
		return _frame;
	}

	public static void main(String args[]) {
		Game game = new Game("Norsemen");
		game.getFrame().setResizable(false);
		game.getFrame().add(game);
		game.getFrame().pack();
		game.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.getFrame().setLocationRelativeTo(null);
		game.getFrame().setVisible(true);

		game.start();
	}
}