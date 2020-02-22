package view.joystick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import engine.SpecialForces;

public class StickListener extends InputListener{
	private Stick stick;
	
	public StickListener(Stick stick){
		this.stick = stick;
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //внутри объекта
		this.stick.dragDown(x, y);
		return true;
	}
	
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		float cx = Gdx.input.getX(pointer) / SpecialForces.getInstance().getPpuX();
		float cy = Gdx.input.getY(pointer) / SpecialForces.getInstance().getPpuY();
		cy = SpecialForces.HEIGHT - cy;
		stick.dragged(cx, cy);
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		this.stick.dragUp();
	}
}
