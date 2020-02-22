package view.gui.tuner;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TunerAddEvent extends ClickListener{
	private Tuner tuner;
	private int amount;
	private boolean fast;
	
	public TunerAddEvent(Tuner tuner, int amount) {
		this.tuner = tuner;
		this.amount = amount;
		this.fast = true;
	}
	
	public void setFast(boolean value){
		this.fast = value;
	}
	
	private void add(){
		tuner.addValue(amount);
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		tuner.addValue(amount);
	}
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		super.touchDown(event, x, y, pointer, button);
		return true;
	}
	
	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		if (fast)
			add();
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		super.touchUp(event, x, y, pointer, button);
	}
}
