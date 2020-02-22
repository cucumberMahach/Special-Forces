package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.PlayerData;
import engine.SpecialForces;
import view.Font;
import view.Label;
import view.gui.Button;
import view.gui.ButtonType;
import world.gameplay.Weapon;

public class Inventory extends Group{
	
	private Button[] weaponBtns;
	private WeaponStat weaponStat;
	private Label weaponName;
	private Weapon[] inventory;
	
	public Inventory(Loader loader, Weapon[] inventory){
		this.inventory = inventory;
		setOrigin(SpecialForces.WIDTH/2, SpecialForces.HEIGHT/2);
		weaponBtns = new Button[inventory.length];
		weaponStat = new WeaponStat(loader, this, 717, 177);
		
		Button btn;
		final int px = 20, py = 434;
		for (int i = 0; i < weaponBtns.length; i++){
			btn = new Button(loader, ButtonType.WEAPON, loader.getWeaponIcon(i));
			btn.setPosition(px + (i % 3) * (btn.getWidth() + 10), py - (i / 3) * (btn.getHeight() + 13));
			if (!inventory[i].isBought())
				btn.setColor(0.1f, 0.1f, 0.1f, 1);
			btn.addListener(new WeaponStatEvent(this, inventory[i]));
			weaponBtns[i] = btn;
			addActor(btn);
		}
		
		weaponName = new Label(loader, "", Font.DEFAULT, Align.center, getOriginX(), 570, 0, 0);
		
		addActor(weaponStat);
		addActor(weaponName);
		selectWeapon(inventory[0]);
	}
	
	public void buy(Weapon weapon){
		final PlayerData pd = SpecialForces.getInstance().playerData();
		final int dollars = pd.getDollars();
		final int cost;
		if (weapon.isBought()){
			cost = weapon.getUpgradePrice();
			if (cost > dollars)
				return;
			pd.changeDollars(-cost);
			weapon.addLevel();
		}else{
			cost = weapon.getWeaponPrice();
			if (cost > dollars)
				return;
			pd.changeDollars(-cost);
			weapon.buy();
		}
		pd.save();
		update();
		SpecialForces.getInstance().sounds().buy();
	}
	
	public void buyAmmo(Weapon weapon){
		final PlayerData pd = SpecialForces.getInstance().playerData();
		final int cost = weapon.getAmmoPrice();
		if (cost > pd.getDollars())
			return;
		weapon.addAmmoAbsolute(weapon.getMaxCaseAmmo());
		pd.changeDollars(-cost);
		pd.save();
		update();
		SpecialForces.getInstance().sounds().buy();
	}
	
	public void selectWeapon(Weapon weapon){
		weaponStat.setWeapon(weapon);
		weaponName.setCaption(weapon.getType().toString());
	}
	
	public void update(){
		for (int i = 0; i < weaponBtns.length; i++){
			float color = inventory[i].isBought() ? 1 : 0.1f;
			weaponBtns[i].setColor(color, color, color, 1);
		}
		weaponStat.update();
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible)
			update();
	}
	
	class WeaponStatEvent extends ClickListener{
		private Inventory inventory;
		private Weapon weapon;
		
		public WeaponStatEvent(Inventory inventory, Weapon weapon){
			this.inventory = inventory;
			this.weapon = weapon;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			inventory.selectWeapon(weapon);
		}
	}
}
