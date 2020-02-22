package world.gameplay;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import world.objects.character.Pose;

public class Weapon {
	private transient TextureRegion texture;
	private transient Pose pose;
	private transient int maxCaseAmmo;
	private transient int maxAmmo;
	private transient float shootDelay;
	private transient float reloadTime;
	private transient int damage;
	private transient float handleX, handleY, shotX, shotY;
	private transient int weaponPrice, ammoPrice;
	private transient boolean infinityAmmo;
	
	private WeaponType type;
	private int ammo;
	private int caseAmmo;
	private int level = 1;
	private boolean bought;
	
	public Weapon(WeaponType type, int maxCaseAmmo, int maxAmmo, float shootDelay, float reloadTime, int damage, int weaponPrice, int ammoPrice, float handleX, float handleY, float shotX, float shotY, TextureRegion texture, Pose pose){
		this.type = type;
		this.maxCaseAmmo = maxCaseAmmo;
		this.maxAmmo = maxAmmo;
		this.shootDelay = shootDelay;
		this.reloadTime = reloadTime;
		this.damage = damage;
		this.weaponPrice = weaponPrice;
		this.ammoPrice = ammoPrice;
		
		this.handleX = handleX;
		this.handleY = handleY;
		this.shotX = shotX;
		this.shotY = shotY;
		this.texture = texture;
		this.pose = pose;
	}
	
	public Weapon(Weapon weapon){
		this.type = weapon.type;
		setConfig(weapon);
	}
	
	public Weapon(){}
	
	public void setConfig(Weapon config){
		this.maxCaseAmmo = config.maxCaseAmmo;
		this.maxAmmo = config.maxAmmo;
		this.shootDelay = config.shootDelay;
		this.reloadTime = config.reloadTime;
		this.damage = config.damage;
		this.weaponPrice = config.weaponPrice;
		this.ammoPrice = config.ammoPrice;
		this.handleX = config.handleX;
		this.handleY = config.handleY;
		this.shotX = config.shotX;
		this.shotY = config.shotY;
		this.texture = config.texture;
		this.pose = config.pose;
	}
	
	public void setData(Weapon weapon){
		this.type = weapon.type;
		this.ammo = weapon.ammo;
		this.caseAmmo = weapon.caseAmmo;
		this.level = weapon.level;
	}
	
	public int getUpgradePrice(){
		return (int) (weaponPrice * level * 0.1f);
	}
	
	public boolean shoot(){
		if (infinityAmmo)
			return true;
		if (caseAmmo <= 0)
			return false;
		caseAmmo--;
		return true;
	}
	
	public void reload(){
		if (!canReload())
			return;
		final int need = maxCaseAmmo - caseAmmo;
		if (ammo > need){
			caseAmmo += need;
			ammo -= need;
		}else{
			caseAmmo += ammo;
			ammo = 0;
		}
		
	}
	
	public boolean canReload(){
		final int need = maxCaseAmmo - caseAmmo;
		if (need == 0 || ammo == 0)
			return false;
		return true;
	}
	
	public int addAmmoAbsolute(int count){
		reload();
		return addAmmo(count);
	}
	
	public int addAmmo(int count){
		int newAmmo = ammo + count;
		if (newAmmo <= maxAmmo){
			ammo = newAmmo;
			return 0;
		}
		ammo = maxAmmo;
		return newAmmo - maxAmmo;
	}
	
	public void setAmmo(int ammo){
		if (ammo < maxAmmo){
			this.ammo = ammo;
		}else{
			this.ammo = maxAmmo;
		}
		reload();
	}
	
	public WeaponType getType(){
		return type;
	}
	
	public int getMaxCaseAmmo(){
		return maxCaseAmmo;
	}
	
	public int getMaxAmmo(){
		return maxAmmo;
	}
	
	public int getAmmo(){
		return ammo;
	}
	
	public int getCaseAmmo(){
		return caseAmmo;
	}
	
	public float getReloadTime(){
		return reloadTime - (reloadTime / 10 * (level*0.70f));
	}
	
	public float getShootDelay(){
		return shootDelay - (shootDelay / 10 * (level*0.5f));
	}
	
	public int getDamage(){
		return damage;
	}
	
	public float getHandleX(){
		return handleX;
	}
	
	public float getHandleY(){
		return handleY;
	}
	
	public float getShotX(){
		return shotX;
	}
	
	public float getShotY(){
		return shotY;
	}
	
	public TextureRegion getTexture(){
		return texture;
	}
	
	public Pose getPose(){
		return isEmpty() ? Pose.STAND : pose;
	}
	
	public boolean isEmpty(){
		return ammo == 0 && caseAmmo == 0;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void addLevel(){
		if (isUpgradeAvailable())
			level++;
	}
	
	public boolean isUpgradeAvailable(){
		return level < 10;
	}
	
	public void buy(){
		bought = true;
	}
	
	public boolean isBought(){
		bought = bought || ammo > 0 || caseAmmo > 0;
		return bought;
	}
	
	public boolean isFull(){
		return ammo + caseAmmo == maxAmmo + maxCaseAmmo;
	}
	
	public int getWeaponPrice(){
		return weaponPrice;
	}
	
	public int getAmmoPrice(){
		return ammoPrice;
	}
	
	public void setInfinityAmmo(boolean value){
		infinityAmmo = value;
	}
	
	public void setHalfDamage(){
		damage = damage / 3;
	}
	
	public static void copyInventoryData(Weapon[] from, Weapon[] to){
		for (int i = 0; i < to.length; i++)
			to[i].setData(from[i]);
	}
}