package editor.controller;

import java.util.HashSet;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import editor.objects.EditorObject;
import engine.Style;
import engine.utils.Maths;
import engine.utils.Point;
import stages.Editor;

public class EditObjectController implements InputProcessor{
	private Editor editor;
	private Point tmpPoint, downPoint, mousePoint, downPointYTop, objectPosWhenHit, cameraPosWhenHit;
	private int button;
	private HashSet<Integer> pressedKeys;
	private boolean dragged;
	
	public EditObjectController(Editor editor){
		this.editor = editor;
		pressedKeys = new HashSet<Integer>();
		tmpPoint = new Point();
		downPoint = new Point();
		mousePoint = new Point();
		downPointYTop = new Point();
		objectPosWhenHit = new Point();
		cameraPosWhenHit = new Point();
	}
	
	public void update(){
		EditorObject obj = editor.commands().getSelectObject();
		if (obj == null)
			return;
		switch (button) {
		case 0:
			if (dragged)
				setPosition(obj, mousePoint.x, mousePoint.y, editor.commands().isRoundMode() || pressedKeys.contains(Keys.ALT_LEFT));
			break;
		case 1:
			Maths.mouseToWorldYTop(mousePoint.x, mousePoint.y, tmpPoint);
			editor.commands().firstProperty(tmpPoint.x, tmpPoint.y);
			break;
		default:
			break;
		}
		if (pressedKeys.contains(Keys.FORWARD_DEL) || editor.commands().isRemoveMode())
			editor.commands().removeSelectObject();
	}
	
	private void setPosition(EditorObject obj, float x, float y, boolean round){
		Maths.mouseToWorldYTop(x, y, tmpPoint);
		Maths.mouseToWorldYTop(downPoint.x, downPoint.y, downPointYTop);
		if (round){
			tmpPoint.x = Maths.toMapCoords(tmpPoint.x) * Style.TILE_SIZE;
			tmpPoint.y = Maths.toMapCoords(tmpPoint.y) * Style.TILE_SIZE;
		}else{
			float origX = downPointYTop.x - objectPosWhenHit.x;
			float origY = downPointYTop.y - objectPosWhenHit.y;
			float camDifX = editor.getOrtCam().position.x - cameraPosWhenHit.x;
			float camDifY = editor.getOrtCam().position.y - cameraPosWhenHit.y;
			tmpPoint.sub(origX, origY);
			tmpPoint.add(camDifX, camDifY);
		}
		obj.setPosition(tmpPoint.x, tmpPoint.y);
	}
	
	private void selectObject(){
		Maths.mouseToWorldYTop(downPoint.x, downPoint.y, tmpPoint);
		try {
			EditorObject obj = (EditorObject) editor.hit(tmpPoint.x, tmpPoint.y, true);
			editor.commands().selectObject(obj);
			if (obj == null)
				return;
			objectPosWhenHit.x = obj.getX();
			objectPosWhenHit.y = obj.getY();
			cameraPosWhenHit.x = editor.getOrtCam().position.x;
			cameraPosWhenHit.y = editor.getOrtCam().position.y;
		}catch(ClassCastException ex){} //fix null
	}
	
	@Override
	public boolean keyDown(int keycode) {
		pressedKeys.add(keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		pressedKeys.remove(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		downPoint.set(screenX, screenY);
		mousePoint.set(screenX, screenY);
		if (pointer > 0)
			button = 1;
		this.button = button;
		if (button == 0 && pointer == 0)
			selectObject();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.button = -1;
		dragged = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mousePoint.set(screenX, screenY);
		dragged = true;
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
