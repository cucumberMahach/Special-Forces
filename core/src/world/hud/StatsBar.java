package world.hud;

import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Group;
import view.Image;
import view.Label;

public class StatsBar extends Group{
	private Image statsImg;
	private Label healthLab, armorLab;
	private int health, armor;
	
	public StatsBar(Loader loader, float x, float y){
		statsImg = new Image(loader.getHud("stats"), -5, -13);
		
		healthLab = new Label(loader, "100", Font.NUMERIC46, Align.center, 42, 3, 40, 40);
		healthLab.setColor(0.6f, 0.05f, 0.05f, 1);
		
		armorLab = new Label(loader, "100", Font.NUMERIC46, Align.center, 196, 3, 40, 40);
		armorLab.setColor(0.24f, 0.6f, 0.5f, 1);
		
		setPosition(x, y);
		setSize(statsImg.getWidth(), statsImg.getHeight());
		
		addActor(statsImg);
		addActor(healthLab);
		addActor(armorLab);
	}
	
	public void updateStats(int health, int armor){
		if (this.health != health){
			this.health = health;
			healthLab.setCaption(String.valueOf(health));
		}
		if (this.armor != armor){
			this.armor = armor;
			armorLab.setCaption(String.valueOf(armor));
		}
	}
}
