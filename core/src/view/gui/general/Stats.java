package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Image;
import view.Label;

public class Stats extends Group{
	private Image dollars;
	private Label dollarsLab;
	
	public Stats(Loader loader, float x, float y){
		dollars = new Image(loader.getIcon("dollars"));
		dollars.setPosition(0, 60);
		
		dollarsLab = new Label(loader, "0", Font.NUMBERS, Align.left, dollars.getRight(), dollars.getCenterY(), 0, 0);
		dollarsLab.moveBy(0, -dollarsLab.getHeight()/3);
		
		addActor(dollars);
		addActor(dollarsLab);
		setPosition(x, y);
	}
	
	public void setDollars(int value){
		dollarsLab.setCaption(String.valueOf(value));
	}
}
