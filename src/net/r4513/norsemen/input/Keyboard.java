package net.r4513.norsemen.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{

	private boolean[] _keys = new boolean[256];
	
	public boolean isKeyDown(int keyCode){
		return _keys[keyCode];
	}
	
	public void keyPressed(KeyEvent e) {
		_keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		_keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}
}