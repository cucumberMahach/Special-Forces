package view.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class PressController extends InputListener{
	private Pressable object;
	
	public PressController(Pressable object){
		this.object = object;
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		if (button == 0)
		this.object.setDown(true);
		return true;
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		if (button == 0)
			this.object.setDown(false);
	}
	
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		object.setFocus(true);
	}
	
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		object.setFocus(false);
	}
}