package editor.controller;

import com.badlogic.gdx.InputProcessor;

import engine.utils.Maths;
import engine.utils.Point;
import stages.Editor;

public class AddObjectController implements InputProcessor{
	private Editor editor;
	private Point point;
	
	public AddObjectController(Editor editor){
		this.editor = editor;
		point = new Point();
	}
	
	public void addObject(float screenX, float screenY){
		Maths.mouseToWorldYTop(screenX, screenY, point);
		editor.addObject(point.x, point.y);
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
		addObject(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
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
