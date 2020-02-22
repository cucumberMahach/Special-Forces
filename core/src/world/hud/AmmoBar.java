package world.hud;

import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Group;
import view.Image;
import view.Label;
import world.gameplay.Weapon;

public class AmmoBar extends Group{
	
	private Image ammoImg, selWeapon;
	private Label ammoLab, ammoCaseLab;
	
	private Loader loader;
	
	private int ammo, caseAmmo;
	
	public AmmoBar(Loader loader, float x, float y){
		this.loader = loader;
		ammoImg = new Image(loader.getHud("ammo"), 0, 0);
		
		ammoCaseLab = new Label(loader, "0", Font.NUMERIC32, Align.right, 44, 65, 30, 20);
		ammoCaseLab.setColor(0.7f, 0.5f, 0f, 1);

		ammoLab = new Label(loader, "0", Font.NUMERIC46, Align.right, 34, 18, 40, 40);
		ammoLab.setColor(0.7f, 0.5f, 0f, 1);
		
		selWeapon = new Image(loader.getWeaponIcon(0), 147, 55, true);
		
		setPosition(x, y);
		setSize(ammoImg.getWidth(), ammoImg.getHeight());
		
		addActor(ammoImg);
		addActor(ammoCaseLab);
		addActor(ammoLab);
		addActor(selWeapon);
	}
	
	public void updateAmmo(Weapon weapon){
		if (weapon != null){
			if (this.ammo != weapon.getAmmo()){
				ammoLab.setCaption(String.valueOf(weapon.getAmmo()));
				ammo = weapon.getAmmo();
			}
			if (this.caseAmmo != weapon.getCaseAmmo()){
				ammoCaseLab.setCaption(String.valueOf(weapon.getCaseAmmo()));
				caseAmmo = weapon.getCaseAmmo();
			}
		}else{
			ammoLab.setCaption(String.valueOf(0));
			ammoCaseLab.setCaption(String.valueOf(0));
		}
	}
	
	public void setWeapon(int index){
		selWeapon.setTexture(loader.getWeaponIcon(index));
	}
}
