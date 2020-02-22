package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Image;
import view.Label;

public class BlueStar extends Group{
	private Image star;
	private Label valueLab;
	
	public BlueStar(Loader loader, float x, float y){
		star = new Image(loader.getIcon("star"));
		valueLab = new Label(loader, "", Font.LEVEL, Align.center, -10, 23, 100, 0);
		setPosition(x, y);
		setSize(star.getWidth(), star.getHeight());
		addActor(star);
		addActor(valueLab);
		setValue(0);
	}
	
	public void setValue(int value){
		valueLab.setCaption(String.valueOf(value));
	}
}
