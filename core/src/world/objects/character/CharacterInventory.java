package world.objects.character;

import engine.Loader;
import engine.SpecialForces;
import world.gameplay.Weapon;
import world.gameplay.WeaponType;
import world.objects.Item;

public abstract class CharacterInventory {
	protected Weapon weapons[];
	private int weaponIndex;

	private Character character;
	
	public CharacterInventory(Character character, Loader loader, Weapon weapons[]){
		this.character = character;
		this.weapons = weapons;
		weaponIndex = 0;
	}

	public boolean secondWeapon(){
		int index = -1;
		for (int a = 0; a < weapons.length; a++) {
			if (!weapons[a].isEmpty()){
				index = a;
				break;
			}
		}
		if (index == -1)
			return false;
		else
			character.setWeaponIndex(index);
		return true;
	}
	
	public void setInfinityAmmo(WeaponType type, boolean value){
		weapons[type.ordinal()].setInfinityAmmo(value);
	}
	
	public void giveAmmo(WeaponType type, int ammo){
		Weapon weapon = weapons[type.ordinal()];
		weapon.addAmmo(ammo);
		character.updatePose();
	}
	
	public void pickUp(Item item){
		Weapon weapon = weapons[item.getWeaponType().ordinal()];
		int ost = weapon.addAmmo(item.getAmmo());
		if (ost == item.getAmmo())
			return;
		character.updatePose();
		item.pickUp(ost);
	}
	
	public void setWeaponIndex(int index){
		weaponIndex = index;
	}
	
	public void changeWeaponIndex(int amount){
		weaponIndex += amount;
		weaponIndex %= weapons.length;
		weaponIndex = weaponIndex < 0 ? weapons.length-weaponIndex - 1 + amount : weaponIndex;
		SpecialForces.getInstance().sounds().flip();
	}
	
	public Weapon getWeapon(){
		return weapons[weaponIndex];
	}
	
	public Weapon[] getWeapons(){
		return weapons;
	}
	
	public int getWeaponIndex(){
		return weaponIndex;
	}
}
