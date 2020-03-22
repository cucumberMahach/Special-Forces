package view.gui.general;

import com.badlogic.gdx.utils.Align;

import engine.GameLogic;
import engine.Loader;
import engine.SpecialForces;
import view.Font;
import view.Label;
import view.gui.ProgressBar;
import view.gui.ProgressBarStyle;

public class ExperienceBar extends ProgressBar{
	
	private Label experienceLab;
	private BlueStar blueStar;
	private int exp = -1, maxExp = -1, level, needNow;
	
	public ExperienceBar(Loader loader, float x, float y) {
		super(loader, ProgressBarStyle.EXPERIENCE);
		experienceLab = new Label(loader, "", Font.EXPERIENCE, Align.right, getWidth() - 10, 1, 0, 43);
		blueStar = new BlueStar(loader, -39, -12);
		
		setPosition(x, y);
		addActor(experienceLab);
		addActor(blueStar);
		setExperience(0);
	}
	
	public void setExperience(int value){
		if (value == exp)
			return;
		exp = value;
		if (value > maxExp)
			calcMaxExperience(value);
		experienceLab.setCaption(value + " / " + maxExp);
		setProgress(1f / needNow * (needNow-(maxExp-value)));
	}
	
	private void calcMaxExperience(int exp){
		final int lastExp;
		level = GameLogic.expToLevel(exp);
		lastExp = maxExp;
		maxExp = GameLogic.needToNextLevel(level);
		needNow = maxExp - lastExp;
		blueStar.setValue(level);
	}

}
