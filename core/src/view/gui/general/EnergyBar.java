package view.gui.general;

import engine.Loader;
import view.Image;
import view.gui.ProgressBar;
import view.gui.ProgressBarStyle;

public class EnergyBar extends ProgressBar{
	
	private Image lightningImg;

	public EnergyBar(Loader loader, float x, float y) {
		super(loader, ProgressBarStyle.ENERGY);
		lightningImg = new Image(loader.getIcon("lightning"));
		lightningImg.setPosition(-10, -12);
		setPosition(x, y);
		addActor(lightningImg);
		
	}
	
	public void setEnergy(int value){
		setProgress(1f / 100 * value);
	}

}
