package net.r4513.norsemen.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{

	private boolean _mouseKeys[] = new boolean[32];
	private int _mouseX = -1;
	private int _mouseY = -1;
	
	public int getX(){
		return _mouseX;
	}
	
	public int getY(){
		return _mouseY;
	}
	
	public boolean isKeyDown(int keyCode){
		return _mouseKeys[keyCode];
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		_mouseKeys[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		_mouseKeys[e.getButton()] = false;
	}

	public void mouseDragged(MouseEvent e) {
		_mouseX = e.getX();
		_mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		_mouseX = e.getX();
		_mouseY = e.getY();
	}
}