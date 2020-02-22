package editor.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import editor.objects.EditorObject;
import engine.Style;

public class ResizeController extends InputListener{
	private EditorObject object;
	
	public ResizeController(EditorObject object){
		this.object = object;
	}
	
	private void resize(float w, float h){
		if (w < Style.TILE_SIZE)
			w = Style.TILE_SIZE;
		if (h < Style.TILE_SIZE)
			h = Style.TILE_SIZE;
		object.setWidth(w);
		object.setHeight(h);
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		return object.getWidth() - x < 10 && object.getHeight() - y < 10;
	}
	
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		resize(x, y);
	}
}
