package editor.controller;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import engine.SpecialForces;
import engine.utils.Maths;
import engine.utils.Point;
import stages.Editor;

public class MapPaintController implements InputProcessor{
	private Editor editor;
	private Point point;
	private boolean isAndroid = false;
	
	public MapPaintController(Editor editor){
		this.editor = editor;
		point = new Point();
		isAndroid = Gdx.app.getType() == Application.ApplicationType.Android;
	}
	
	public void paint(float screenX, float screenY){
		int mapX, mapY;
		screenX = Gdx.input.getX();
		screenY = Gdx.input.getY();
		Maths.mouseToWorldYTop(screenX, screenY, point);
		mapX = Maths.toMapX(point.x);
		mapY = Maths.toMapY(point.y);
		editor.paint(mapX, mapY);
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
		paint(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		paint(screenX, screenY);
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
