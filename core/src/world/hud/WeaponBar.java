package world.hud;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;

import engine.Loader;
import engine.utils.CoordsMap;
import view.Group;
import view.Image;
import world.gameplay.Weapon;

public class WeaponBar extends Group{
	
	private Image background, selector;
	private Array<Image> weapons;
	
	private boolean active;
	private float activeTime;
	
	private int[][] selectorCoords;
	
	public WeaponBar(Loader loader, float x, float y){
		background = new Image(loader.getHud("weapons"));
		
		int[][] pos = CoordsMap.readCoordsMap("coords/weaponBar.txt");
		selectorCoords = CoordsMap.readCoordsMap("coords/weaponSelector.txt");
		
		weapons = new Array<Image>();
		for (int i = 0; i < pos.length; i++)
			weapons.add(new Image(loader.getWeaponIcon(i), pos[i][0], pos[i][1]));
		
		selector = new Image(loader.getHud("selector"));
		setWeaponIndex(0);
		
		setPosition(x, y);
		setSize(background.getWidth(), background.getHeight());
		
		addActor(background);
		addActor(selector);
		Iterator<Image> iter = weapons.iterator();
		while (iter.hasNext()){
			addActor(iter.next());
		}
		active = false;
	}
	
	public void updateWeapons(Weapon[] weapons){
		Image img;
		int i;
		for (i = 0; i < this.weapons.size; i++){
			img = this.weapons.get(i);
			if (weapons[i].isEmpty())
				img.setColor(0, 0, 0, 1);
			else
				img.setColor(1, 1, 1, 1);
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		activeTime += delta;
		if (activeTime > 2)
			active = false;
	}
	
	public void makeActive(){
		active = true;
		activeTime = 0;
	}
	
	public void setWeaponIndex(int index){
		selector.setPosition(selectorCoords[index][0], selectorCoords[index][1]);
		selector.setWidth(selectorCoords[index][2]);
		makeActive();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (active)
			super.draw(batch, parentAlpha);
	}
	
	public boolean isActive(){
		return active;
	}
}
