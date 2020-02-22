package view.gui.general;

import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.Style;
import view.Font;
import view.Label;
import view.gui.ProgressBar;
import view.gui.ProgressBarStyle;

public class WeaponLevelBar extends ProgressBar{
	
	private Label levelLab;

	public WeaponLevelBar(Loader loader, float x, float y) {
		super(loader, ProgressBarStyle.WEAPON_LEVEL);
		levelLab = new Label(loader, "", Font.NUMBERS_WEAPON_STAT, Align.right, getWidth() - 10, 1, 0, 43);
		
		setPosition(x, y);
		addActor(levelLab);
		
		setLevel(0);
	}
	
	public void setLevel(int value){
		levelLab.setCaption(String.valueOf(value));
		setProgress(1f / Style.MAX_WEAPON_LEVEL * value);
	}

}
