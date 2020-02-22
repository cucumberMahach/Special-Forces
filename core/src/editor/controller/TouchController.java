package editor.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import engine.SpecialForces;
import stages.Editor;

public class TouchController implements InputProcessor{
	private Editor editor;
	private float downX, downY, x, y, oldX, oldY;

	public TouchController(Editor editor) {
		this.editor = editor;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pointer > 0)
			return false;
		editor.getTouchZoomController().zoomHappened = false;
		downX = screenX;
		downY = screenY;
		oldX = 0;
		oldY = 0;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer > 0)
			return false;
		if (editor.getTouchZoomController().zoomHappened) {
			editor.getTouchZoomController().zoomHappened = false;
			return false;
		}
		x = screenX - downX;
		y = screenY - downY;
		editor.moveCameraAndroid(-(x - oldX) / SpecialForces.getInstance().getPpuX(), (y - oldY) / SpecialForces.getInstance().getPpuY());
		oldX = x;
		oldY = y;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
