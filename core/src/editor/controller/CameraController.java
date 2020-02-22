package editor.controller;

import java.util.HashSet;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import stages.Editor;

public class CameraController implements InputProcessor{
	private Editor editor;
	private HashSet<Integer> keys;
	private int scrollAmount;

	public static final float EDITOR_CAMERA_SPEED = 1;
	public static final float EDITOR_CAMERA_SPEED_SHIFT = 3.5f;
	
	public CameraController(Editor editor) {
		this.editor = editor;
		keys = new HashSet<Integer>();
	}
	
	public void update(){
		updatePosition();
		editor.zoomBy(getZoom());
	}
	
	private void updatePosition(){
		float camSpeed = EDITOR_CAMERA_SPEED;
		if (keys.contains(Keys.SHIFT_LEFT))
			camSpeed = EDITOR_CAMERA_SPEED_SHIFT;
		if (keys.contains(Keys.W))
			editor.moveBy(0, camSpeed);
		if (keys.contains(Keys.A))
			editor.moveBy(-camSpeed, 0);
		if (keys.contains(Keys.S))
			editor.moveBy(0, -camSpeed);
		if (keys.contains(Keys.D))
			editor.moveBy(camSpeed, 0);
	}
	
	private float getZoom(){
		int zoom = scrollAmount;
		scrollAmount = 0;
		return zoom * 0.05f;
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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		scrollAmount += amount;
		return false;
	}

}
