package view.gui.general;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Font;
import view.Image;
import view.Label;
import view.gui.Button;
import view.gui.ButtonIcon;
import view.gui.ButtonType;
import world.gameplay.Weapon;
import world.gameplay.WeaponType;

public class WeaponStat extends Group{
	
	private Loader loader;
	private Image background, weaponImg, caseImg, bulletImg;
	private Group weapons;
	private Button ammoBtn, actionBtn;
	private Label infoLab, ammoLab, priceLab;
	private WeaponLevelBar levelBar;
	
	private Weapon weapon;
	private Inventory parent;
	
	public WeaponStat(Loader loader, Inventory parent, float x, float y){
		this.loader = loader;
		this.parent = parent;
		background = new Image(loader.getBackground("weaponStat"));
		setSize(background.getWidth(), background.getHeight());
		setOrigin(Align.center);
		
		weapons = new Group();
		weaponImg = new Image(null, 2);
		caseImg = new Image(null);
		bulletImg = new Image(null);
		ammoBtn = new Button(loader, ButtonType.SMALL_GREEN, "", ButtonIcon.PLUS);
		ammoBtn.setPosition(169, 178);
		ammoLab = new Label(loader, "", Font.DEFAULT, Align.left, ammoBtn.getRight() + 12, ammoBtn.getY() + 10, 0, 0);
		levelBar = new WeaponLevelBar(loader, 162, 115);
		actionBtn = new Button(loader, ButtonType.BUY, "upgrade", null);
		actionBtn.setPosition(22, 15);
		priceLab = new Label(loader, "", Font.CONTRACT_COST, Align.center, 382, actionBtn.getY() + 15, 0, 0);
		infoLab = new Label(loader, "ammo:\nlevel:", Font.DEFAULT, Align.left, 31, 190, 0, 0);
		
		actionBtn.addListener(new ActionEvent(this));
		ammoBtn.addListener(new AmmoEvent(this));
		
		addActor(background);
		weapons.addActor(weaponImg);
		weapons.addActor(caseImg);
		weapons.addActor(bulletImg);
		addActor(infoLab);
		addActor(ammoBtn);
		addActor(ammoLab);
		addActor(levelBar);
		addActor(weapons);
		addActor(actionBtn);
		addActor(priceLab);
		setPosition(x, y);
	}
	
	public void update(){
		ammoLab.setCaption(weapon.getCaseAmmo() + weapon.getAmmo() + " / " + (weapon.getMaxAmmo() + weapon.getMaxCaseAmmo()));
		levelBar.setLevel(weapon.getLevel());
		setBuyMode(!weapon.isBought());
	}
	
	public void buy(){
		parent.buy(weapon);
	}
	
	public void buyAmmo(){
		parent.buyAmmo(weapon);
	}
	
	public void setBuyMode(boolean value){
		actionBtn.setCaption(value ? "buy" : "upgrade");
		if (value){
			actionBtn.setVisible(true);
			priceLab.setVisible(true);
			actionBtn.setPosition(getOriginX(), 15, Align.bottom);
			priceLab.setPosition(getOriginX(), 300, Align.bottom);
			priceLab.setCaption(weapon.getWeaponPrice() + " $");
		}else{
			actionBtn.setVisible(weapon.isUpgradeAvailable());
			priceLab.setVisible(weapon.isUpgradeAvailable());
			actionBtn.setPosition(22, 15);
			priceLab.setPosition(382, actionBtn.getY() + 15);
			priceLab.setCaption(weapon.getUpgradePrice() + " $");
			ammoLab.setX(weapon.isFull() ? ammoBtn.getX() : ammoBtn.getRight() + 12);
		}
		weapons.setPosition(getOriginX(), value ? getOriginY() : 260, Align.center);
		value = !value;
		ammoBtn.setVisible(value && !weapon.isFull());
		ammoLab.setVisible(value);
		levelBar.setVisible(value);
		infoLab.setVisible(value);
	}
	
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
		setWeaponImages(weapon.getType());
		update();
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	
	private void setWeaponImages(WeaponType type){
		weaponImg.setTexture(loader.getWeaponIcon(type.ordinal()));
		caseImg.setTexture(loader.getParticle(type.getWeaponCase()));
		bulletImg.setTexture(loader.getParticle(type.getBullet()));
		weapons.setWidth(bulletImg.getWidth() + 20 + caseImg.getWidth() + 15 + weaponImg.getWidth());
		caseImg.setX(bulletImg.getWidth() + 20);
		weaponImg.setX(caseImg.getRight() + 15);
	}
	
	class AmmoEvent extends ClickListener{
		private WeaponStat weaponStat;
		
		public AmmoEvent(WeaponStat weaponStat){
			this.weaponStat = weaponStat;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			weaponStat.buyAmmo();
		}
	}
	
	class ActionEvent extends ClickListener{
		private WeaponStat weaponStat;
		
		public ActionEvent(WeaponStat weaponStat){
			this.weaponStat = weaponStat;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			weaponStat.buy();
		}
	}
}