package view.gui.tuner;

import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Label;

public class NumericTuner extends Tuner implements Setable{
	private Label valueLab;
	
	public NumericTuner(Loader loader, float x, float y){
		super(loader, x, y);
		valueLab = new Label(loader, "0", Font.SMALL, Align.left, getRight() + 10, 15, 0, 30);
		addActor(valueLab);
		setSetable(this);
	}
	
	public NumericTuner(Loader loader) {
		this(loader, 0, 0);
	}

	@Override
	public void setValue(int value) {
		valueLab.setCaption(String.valueOf(value));
		this.value = value;
	}
}

