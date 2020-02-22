package controller;

import java.security.Key;
import java.util.HashSet;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import engine.utils.Maths;
import engine.utils.Point;
import world.objects.player.Player;

public class PlayerController implements InputProcessor{
	private Player player;
	private HashSet<Integer> keys;
	
	private int scrollAmount;
	
	private Point mousePoint, tmpPoint;
	private boolean shoot;
	
	public PlayerController(Player player){
		this.player = player;
		keys = new HashSet<Integer>();
		mousePoint = new Point();
		tmpPoint = new Point();
	}
	
	public void update(){
		int index = getWeaponIndex();
		player.setDirection(getDirection());
		player.setRotation(getRotation());
		if (index != 0)
			player.changeWeaponIndex(index);
		if (shoot){
			Maths.mouseToPlayerYTop(mousePoint.x, mousePoint.y, tmpPoint);
			player.actions().shoot(tmpPoint.x, tmpPoint.y);
		}
		if (keys.contains(Keys.R))
			player.actions().reload();
		if (keys.contains(Keys.SHIFT_LEFT))
			player.setSpeedRate(1.5f);
		else
			player.setSpeedRate(1f);
	}
	
	public int getWeaponIndex(){
		int index = scrollAmount;
		scrollAmount = 0;
		return index;
	}
	
	private float getRotation(){
		Maths.mouseToPlayerYTop(mousePoint.x, mousePoint.y, tmpPoint);
		float ang = (float) Math.atan2(tmpPoint.y, tmpPoint.x);
		return Maths.radiansToDegrees(ang);
	}
	
	private Direction getDirection(){
		boolean up = keys.contains(Keys.W);
		boolean down = keys.contains(Keys.S);
		boolean left = keys.contains(Keys.A);
		boolean right = keys.contains(Keys.D);
		if (up && right)
			return Direction.UP_RIGHT;
		if(right && down)
			return Direction.RIGHT_DOWN;
		if(down && left)
			return Direction.DOWN_LEFT;
		if(left && up)
			return Direction.LEFT_UP;
		if(up)
			return Direction.UP;
		if(down)
			return Direction.DOWN;
		if(left)
			return Direction.LEFT;
		if(right)
			return Direction.RIGHT;
		return Direction.NONE;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		keys.add(keycode);
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		keys.remove(keycode);
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == 0)
			shoot = true;
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == 0)
			shoot = false;
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mousePoint.set(screenX, screenY);
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mousePoint.set(screenX, screenY);
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		scrollAmount += amount;
		return false;
	}
}
