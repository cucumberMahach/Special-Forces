package view.gui.general;

import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Label;
import view.gui.ProgressBar;
import view.gui.ProgressBarStyle;

public class ContractBar extends ProgressBar{
	
	private Label valueLab;

	public ContractBar(Loader loader, float x, float y) {
		super(loader, ProgressBarStyle.CONTRACT_STAT);
		valueLab = new Label(loader, "", Font.CONTRACT_STAT, Align.right, getWidth() - 10, 13, 0, 0);
		addActor(valueLab);
		setPosition(x, y);
		setValue(0);
	}
	
	public void setValue(float value){
		valueLab.setCaption(String.valueOf((int) (value * 100)));
		setProgress(value);
	}

}
